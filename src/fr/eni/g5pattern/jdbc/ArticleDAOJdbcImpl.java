package fr.eni.g5pattern.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import fr.eni.g5pattern.bll.UtilisateurManager;
import fr.eni.g5pattern.bo.Article;
import fr.eni.g5pattern.bo.BusinessException;
import fr.eni.g5pattern.bo.Categorie;
import fr.eni.g5pattern.bo.Enchere;
import fr.eni.g5pattern.bo.Retrait;
import fr.eni.g5pattern.bo.Utilisateur;
import fr.eni.g5pattern.dal.ArticleDAO;
import fr.eni.g5pattern.dal.DAOFactory;

public class ArticleDAOJdbcImpl implements ArticleDAO {

	private static final String INSERT_ENCHERE = "INSERT INTO ARTICLES_VENDUS(nom_article, description, date_debut_encheres, "
			+ "date_fin_encheres, prix_initial, no_utilisateur, no_categorie) VALUES (?,?,?,?,?,?,?)";
	private static final String INSERT_RETRAIT = "INSERT INTO RETRAITS (no_article, rue, code_postal, ville) VALUES(?,?,?,?)";
	private static final String UPDATE_ENCHERE = "UPDATE ARTICLES_VENDUS SET nom_article=?, description=?,"
			+ "date_debut_encheres=?, date_fin_encheres=?, prix_initial=?, no_utilisateur=?, no_categorie=? WHERE no_article=?";
	private static final String UPDATE_RETRAIT = "UPDATE RETRAITS SET rue=?, code_postal=?, ville=? WHERE no_article=?";
	private static final String DELETE_RETRAITS = "DELETE FROM RETRAITS WHERE no_article = ?";
	private static final String DELETE_ENCHERE = "DELETE FROM ARTICLES_VENDUS WHERE no_article = ?";
	private static final String SELECT_NUM_CATEGORIE = "SELECT no_categorie FROM CATEGORIES WHERE libelle=?";
// selection d'un article avec sa catégorie et son lieu de retrait pour affichage
	private static final String SELECT_ARTICLE_ENCHERE = "SELECT a.no_article, nom_article, a.description, date_debut_encheres, date_fin_encheres, "
			+ "prix_initial, prix_vente, u.no_utilisateur, pseudo, telephone, c.no_categorie, c.libelle, r.rue as rueRetrait, r.code_postal as "
			+ "cpRetrait, r.ville as villeRetrait FROM ARTICLES_VENDUS as a JOIN CATEGORIES as c ON a.no_categorie=c.no_categorie "
			+ "JOIN RETRAITS as r ON r.no_article=a.no_article "
			+ "JOIN UTILISATEURS as u ON u.no_utilisateur=a.no_utilisateur WHERE a.no_article=?;";
	private static final String DERNIERE_ENCHERE = "SELECT TOP 1 date_Enchere, e.montant_enchere, e.no_utilisateur, pseudo FROM ENCHERES as e "
			+ "JOIN UTILISATEURS as u ON e.no_utilisateur=u.no_utilisateur WHERE no_article=? ORDER BY date_enchere DESC";
	public static final String SEARCH = "SELECT a.no_article, nom_article, date_debut_encheres, date_fin_encheres, prix_initial, "
			+ "libelle, u.no_utilisateur, pseudo FROM ARTICLES_VENDUS as a JOIN CATEGORIES as c ON "
			+ "a.no_categorie=c.no_categorie JOIN UTILISATEURS as u ON "
			+ "a.no_utilisateur=u.no_utilisateur WHERE libelle LIKE ? AND (nom_article LIKE ? OR description LIKE ?)";
	public static final String ENCHERIR = "INSERT INTO ENCHERES (date_enchere, montant_enchere, no_article, no_utilisateur) VALUES(SYSDATETIME (),?,?,?);";
	public static final String CREDIT_DEBIT_ENCHERISSEUR = "UPDATE UTILISATEURS SET credit=? WHERE pseudo=?";
	public static final String SELECT_ARTICLE_ENCHERI = "SELECT e.no_article, MAX(montant_enchere) as montant_enchere, u.pseudo, "
			+ "nom_article, prix_initial, date_debut_encheres, " + "date_fin_encheres FROM ENCHERES as e "
			+ "JOIN UTILISATEURS as u ON e.no_utilisateur = u.no_utilisateur "
			+ "JOIN ARTICLES_VENDUS as a ON e.no_article = a.no_article "
			+ "WHERE u.pseudo=? AND date_fin_encheres > SYSDATETIME()"
			+ "GROUP BY e.no_article, u.pseudo, a.nom_article, a.prix_initial, a.date_debut_encheres, a.date_fin_encheres";
	public static final String ENCHERES_REMPORTEES = "SELECT montant_enchere, e.no_article, e.no_utilisateur, u.pseudo, a.no_categorie, "
			+ "libelle, date_debut_encheres, date_fin_encheres, description, nom_article FROM ENCHERES as e "
			+ "JOIN UTILISATEURS as u ON e.no_utilisateur = u.no_utilisateur "
			+ "JOIN ARTICLES_VENDUS as a ON e.no_article = a.no_article "
			+ "JOIN CATEGORIES as c ON a.no_categorie = c.no_categorie "
			+ "WHERE pseudo = ? AND libelle LIKE ? AND ( nom_article LIKE ? OR description LIKE ?) AND a.date_fin_encheres < SYSDATETIME()";
	private static final String UPDATE_PRIX_VENTE = "UPDATE ARTICLES_VENDUS SET prix_vente = ? WHERE no_article = ?";
	public static final String SELECT_ENCHERES_OUVERTES = "SELECT a.no_article, nom_article, date_debut_encheres, date_fin_encheres, prix_initial, libelle, "
			+ "u.no_utilisateur, pseudo FROM ARTICLES_VENDUS as a JOIN CATEGORIES as c ON a.no_categorie=c.no_categorie JOIN UTILISATEURS as u "
			+ "ON a.no_utilisateur=u.no_utilisateur WHERE libelle LIKE ? AND (nom_article LIKE ? OR description LIKE ?) "
			+ "AND date_debut_encheres < SYSDATETIME() AND date_fin_encheres > SYSDATETIME();";
	// nécessitent les filtres ET le pseudo de l'utilisateur qui fait la requête :
	public static final String SELECT_MES_VENTES_TERMINEES = "SELECT a.no_article, nom_article, date_debut_encheres, date_fin_encheres, prix_initial, libelle, "
			+ "			u.no_utilisateur, pseudo FROM ARTICLES_VENDUS as a JOIN CATEGORIES as c ON a.no_categorie=c.no_categorie JOIN UTILISATEURS as u "
			+ "			ON a.no_utilisateur=u.no_utilisateur WHERE (libelle LIKE ? AND (nom_article LIKE ? OR description LIKE ?) "
			+ "			AND (date_fin_encheres < DATEADD(DAY,-1,SYSDATETIME())) AND pseudo=?)";
	public static final String SELECT_MES_VENTES_ENCOURS = "SELECT a.no_article, nom_article, date_debut_encheres, date_fin_encheres, prix_initial, "
			+ "libelle, u.no_utilisateur, pseudo FROM ARTICLES_VENDUS as a JOIN CATEGORIES as c ON a.no_categorie=c.no_categorie "
			+ "JOIN UTILISATEURS as u ON a.no_utilisateur=u.no_utilisateur "
			+ "WHERE (libelle LIKE ? AND (nom_article LIKE ? OR description LIKE ?) AND (date_fin_encheres > DATEADD(DAY,-1,SYSDATETIME())) "
			+ "AND date_debut_encheres <= SYSDATETIME() AND pseudo=?);";
	public static final String SELECT_MES_VENTES_A_VENIR = "SELECT a.no_article, nom_article, date_debut_encheres, date_fin_encheres, prix_initial, libelle, "
			+ "			u.no_utilisateur, pseudo FROM ARTICLES_VENDUS as a JOIN CATEGORIES as c ON a.no_categorie=c.no_categorie JOIN UTILISATEURS as u "
			+ "			ON a.no_utilisateur=u.no_utilisateur WHERE (libelle LIKE ? AND (nom_article LIKE ? OR description LIKE ?) "
			+ "			AND ( date_debut_encheres > SYSDATETIME()) AND pseudo=? )";

	// ---------------------------------//

	// ---------------------------------//

	// creation d'un article via formulaire
	@Override
	public void insertEnchere(Article article) throws BusinessException {
		try (Connection cnx = ConnectionProvider.getConnection()) {
			try {
				cnx.setAutoCommit(false);
				PreparedStatement pstmt = cnx.prepareStatement(INSERT_ENCHERE, PreparedStatement.RETURN_GENERATED_KEYS);

				pstmt.setString(1, article.getNomArticle());
				pstmt.setString(2, article.getDescription());
				pstmt.setDate(3, java.sql.Date.valueOf(article.getDebutEnchere()));
				pstmt.setDate(4, java.sql.Date.valueOf(article.getFinEnchere()));
				pstmt.setInt(5, article.getPrixInit());
				pstmt.setInt(6, article.getVendeur().getNo_users());
				pstmt.setInt(7, selectNumCategorie(article).getNumCategorie());

				int nb = pstmt.executeUpdate();
				if (nb == 1) {
					ResultSet rs = pstmt.getGeneratedKeys();
					if (rs.next()) {
						article.setNo_article(rs.getInt(1));
					}
					rs.close();
					pstmt.close();

				}
				cnx.commit();
			} catch (SQLException e) {
				e.printStackTrace();
				cnx.rollback();
				throw new BusinessException("Impossible de sauvegarder");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BusinessException("Erreur d'acces a la base de donnees");
		}
	}

	public void insertRetrait(Article article, Retrait retrait) throws BusinessException {
		try (Connection cnx = ConnectionProvider.getConnection()) {
			try {
				cnx.setAutoCommit(false);
				PreparedStatement pstmt = cnx.prepareStatement(INSERT_RETRAIT);

				pstmt.setInt(1, article.getNo_article());
				pstmt.setString(2, retrait.getNomRue());
				pstmt.setString(3, retrait.getCodePostal());
				pstmt.setString(4, retrait.getNomVille());

				pstmt.executeUpdate();
				pstmt.close();
				cnx.commit();
			} catch (SQLException e) {
				e.printStackTrace();
				cnx.rollback();
				throw new BusinessException("Impossible de sauvegarder");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BusinessException("Erreur d'acces a la base de donnees");
		}
	}

	@Override
	public List<Article> rechercheFiltres(String categorie, String filtres) throws BusinessException {

		List<Article> listeArticles = new ArrayList<Article>();
		PreparedStatement pstmt = null;
		Connection con = null;
		ResultSet rs = null;
		String champ = "%" + filtres + "%";

		try {
			con = ConnectionProvider.getConnection();

			pstmt = con.prepareStatement(SELECT_ENCHERES_OUVERTES);
			pstmt.setString(1, categorie);
			pstmt.setString(2, champ);
			pstmt.setString(3, champ);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				int numeroArticle = rs.getInt("no_article");
				String nom = rs.getString("nom_article");
				int prix = rs.getInt("prix_initial");
				LocalDate debut = rs.getDate("date_debut_encheres").toLocalDate();
				LocalDate fin = rs.getDate("date_fin_encheres").toLocalDate();

				Enchere e = selectEnchere(numeroArticle); // dernière enchere sur l'article
				Article article = new Article();
				Utilisateur user = new Utilisateur();
				user.setPseudo(rs.getString("pseudo"));

				if (e != null) {
					e.setMontantEnchere(e.getMontantEnchere());
					article.setDerniereEnchere(e);
				} else {
					e = new Enchere();
					e.setMontantEnchere(rs.getInt("prix"));
					article.setDerniereEnchere(e);
				}

				article.setNo_article(numeroArticle);
				article.setNomArticle(nom);
				article.setVendeur(user);
				article.setDebutEnchere(debut);
				article.setFinEnchere(fin);
				article.setPrixInit(prix);

				listeArticles.add(article);
			}

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw new BusinessException("Impossible de fermer le ResultSet");
			}
			try {
				if (pstmt != null) {
					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw new BusinessException("Impossible de fermer le Statement");
			}
			try {
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw new BusinessException("Impossible de fermer la connexion");
			}
		}
		return listeArticles;
	}

	@Override
	public Article selectArticleEnchere(int idArticle) throws BusinessException {
		Categorie c = new Categorie();
		Article a = new Article();
		Retrait r = new Retrait();
		Utilisateur v = new Utilisateur();
		Enchere e = selectEnchere(idArticle);

		try (Connection cnx = ConnectionProvider.getConnection()) {
			try {
				PreparedStatement pstmt = cnx.prepareStatement(SELECT_ARTICLE_ENCHERE);
				pstmt.setInt(1, idArticle);

				ResultSet rs = pstmt.executeQuery();

				// affectation des infos sur l'article
				rs.next();

				c.setLibelle(rs.getString("libelle"));
				c.setNumCategorie(rs.getInt("no_categorie"));

				if (c != null) {
					a.setCategorie(c);
				}

				r.setNomRue(rs.getString("rueRetrait"));
				r.setCodePostal(rs.getString("cpRetrait"));
				r.setNomVille(rs.getString("villeRetrait"));

				v.setNo_users(rs.getInt("no_utilisateur"));
				v.setPseudo(rs.getString("pseudo"));
				v.setPhone(rs.getString("telephone"));

				a.setVendeur(v);
				a.setRetrait(r);
				a.setNomArticle(rs.getString("nom_article"));
				a.setDescription(rs.getString("description"));
				a.setNo_article(idArticle);
				a.setDebutEnchere(rs.getDate("date_debut_encheres").toLocalDate());
				a.setFinEnchere(rs.getDate("date_fin_encheres").toLocalDate());

				a.setPrixInit(rs.getInt("prix_initial"));
				a.setPrixVente(rs.getInt("prix_vente"));
				if (e != null) {
					a.setDerniereEnchere(e);
				}
				cnx.close();
				pstmt.close();
				return a;

			} catch (SQLException be) {
				be.printStackTrace();
				throw new BusinessException("Impossible d'afficher l'article");
			}
		} catch (SQLException be) {
			be.printStackTrace();
			throw new BusinessException("Erreur d'acces a la base de donnees");
		}
	}

	public Categorie selectNumCategorie(Article article) throws BusinessException {

		Categorie cat = new Categorie();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			try {
				cnx.setAutoCommit(false);

				// recup numCategorie
				PreparedStatement pstmt = cnx.prepareStatement(SELECT_NUM_CATEGORIE);
				pstmt.setString(1, article.getCategorie().getLibelle());

				ResultSet rs = pstmt.executeQuery();
				if (!rs.next()) {
					BusinessException businessException = new BusinessException("Ce libelle n'existe pas");
					throw businessException;
				} else {
					cat.setNumCategorie(rs.getInt("no_categorie"));

					rs.close();
					pstmt.close();
				}
				cnx.commit();
				return cat;

			} catch (SQLException e) {
				e.printStackTrace();
				cnx.rollback();
				throw new BusinessException("Impossible de sauvegarder");
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new BusinessException("Impossible de sauvegarder");
		}

	}

	public Enchere selectEnchere(int idArticle) throws BusinessException {
		Enchere e = new Enchere();
		Utilisateur u = new Utilisateur();

		// sélection de la dernière enchere sur 1 article

		try (Connection cnx = ConnectionProvider.getConnection()) {
			try {
				PreparedStatement pstmt = cnx.prepareStatement(DERNIERE_ENCHERE);
				pstmt.setInt(1, idArticle);

				ResultSet rs = pstmt.executeQuery();
				if (rs.next()) {
					u.setPseudo(rs.getString("pseudo"));

					e.setDateEnchere(rs.getDate("date_enchere").toLocalDate());
					e.setMontantEnchere(rs.getInt("montant_enchere"));
					e.setUtilisateur(u);
				}
				cnx.close();
				pstmt.close();
				return e;
			} catch (SQLException be) {
				be.printStackTrace();
				cnx.rollback();
				throw new BusinessException("Impossible de récupérer la dernière enchère");
			}
		} catch (SQLException be) {
			be.printStackTrace();
			throw new BusinessException("Erreur d'acces a la base de donnees");
		}
	}

	public void encherir(Enchere e) throws BusinessException {
		try (Connection cnx = ConnectionProvider.getConnection()) {
			try {
				cnx.setAutoCommit(false);
				PreparedStatement pstmt = cnx.prepareStatement(ENCHERIR);

				pstmt.setInt(1, e.getMontantEnchere());
				pstmt.setInt(2, e.getNo_article());
				pstmt.setInt(3, e.getNo_utilisateur());

				pstmt.executeUpdate();

				pstmt.close();

				// recrédit enchérisseur précédent

				if (e.getUtilisateur() != null) {

					Utilisateur precedent = UtilisateurManager.getInstance().profil(e.getUtilisateur().getPseudo());

					PreparedStatement pstmt1 = cnx.prepareStatement(CREDIT_DEBIT_ENCHERISSEUR);

					pstmt1.setInt(1, precedent.getCredit() + e.getUtilisateur().getCredit());

					pstmt1.setString(2, precedent.getPseudo());

					pstmt1.executeUpdate();

					pstmt1.close();
				}

				// débit enchérisseur

				Utilisateur encherisseur = DAOFactory.getUtilisateurDAO().getUser(e.getNo_utilisateur());

				PreparedStatement pstmt2 = cnx.prepareStatement(CREDIT_DEBIT_ENCHERISSEUR);

				pstmt2.setInt(1, encherisseur.getCredit() - e.getMontantEnchere());
				pstmt2.setString(2, encherisseur.getPseudo());

				pstmt2.executeUpdate();

				pstmt2.close();

				cnx.commit();
			} catch (SQLException esql) {
				esql.printStackTrace();
				cnx.rollback();
				throw new BusinessException("Impossible d'enregistrer l'enchère");
			}
		} catch (

		SQLException esql) {
			esql.printStackTrace();
			throw new BusinessException("Erreur d'acces a la base de donnees");
		}
	}

	@Override
	public void updateEnchere(Article article) throws BusinessException {
		try (Connection cnx = ConnectionProvider.getConnection()) {
			try {
				cnx.setAutoCommit(false);
				PreparedStatement pstmt = cnx.prepareStatement(UPDATE_ENCHERE);
				pstmt.setString(1, article.getNomArticle());
				pstmt.setString(2, article.getDescription());
				pstmt.setDate(3, java.sql.Date.valueOf(article.getDebutEnchere()));
				pstmt.setDate(4, java.sql.Date.valueOf(article.getFinEnchere()));
				pstmt.setInt(5, article.getPrixInit());
				pstmt.setInt(6, article.getVendeur().getNo_users());
				pstmt.setInt(7, selectNumCategorie(article).getNumCategorie());
				pstmt.setInt(8, article.getNo_article());

				int nb = pstmt.executeUpdate();

				pstmt.close();
				cnx.commit();

			} catch (SQLException e) {
				e.printStackTrace();
				cnx.rollback();
				throw new BusinessException("Impossible de sauvegarder");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BusinessException("Erreur d'acces a la base de donnees");
		}
	}

	@Override
	public void updateRetrait(Article article, Retrait retrait) throws BusinessException {
		try (Connection cnx = ConnectionProvider.getConnection()) {
			try {
				cnx.setAutoCommit(false);
				PreparedStatement pstmt = cnx.prepareStatement(UPDATE_RETRAIT);

				pstmt.setString(1, retrait.getNomRue());
				pstmt.setString(2, retrait.getCodePostal());
				pstmt.setString(3, retrait.getNomVille());
				pstmt.setInt(4, article.getNo_article());

				int nb = pstmt.executeUpdate();

				pstmt.close();
				cnx.commit();

			} catch (SQLException e) {
				e.printStackTrace();
				cnx.rollback();
				throw new BusinessException("Impossible de sauvegarder");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BusinessException("Erreur d'acces a la base de donnees");
		}
	}

	@Override
	public void deleteEnchere(int idArticle) throws BusinessException {
		try (Connection cnx = ConnectionProvider.getConnection()) {
			try {
				cnx.setAutoCommit(false);
				PreparedStatement pstmt = cnx.prepareStatement(DELETE_RETRAITS);
				pstmt.setInt(1, idArticle);
				int nb = pstmt.executeUpdate();
				if (nb == 1) {
					pstmt.close();
					pstmt = cnx.prepareStatement(DELETE_ENCHERE);
					pstmt.setInt(1, idArticle);
					int nb2 = pstmt.executeUpdate();
					if (nb2 == 1) {
						pstmt.close();
						cnx.commit();
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
				cnx.rollback();
				throw new BusinessException("Impossible de sauvegarder");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BusinessException("Erreur d'acces a la base de donnees");
		}
	}

	@Override
	public List<Article> getArticleEncheri(String pseudo) throws BusinessException {

		List<Article> getEnchere = new ArrayList<>();
		try {
			Connection con = ConnectionProvider.getConnection();
			PreparedStatement pstmt = con.prepareStatement(SELECT_ARTICLE_ENCHERI);
			ResultSet rs = null;

			pstmt.setString(1, pseudo);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				int numeroArticle = rs.getInt("no_article");
				String nom = rs.getString("nom_article");
				int prix = rs.getInt("prix_initial");
				int derniereEnchere = rs.getInt("montant_enchere");
				LocalDate debut = rs.getDate("date_debut_encheres").toLocalDate();
				LocalDate fin = rs.getDate("date_fin_encheres").toLocalDate();

				Article a = new Article();
				Enchere e = new Enchere();
				Utilisateur u = new Utilisateur();
				u.setPseudo(pseudo);
				e.setMontantEnchere(derniereEnchere);
				e.setUtilisateur(u);
				a.setDerniereEnchere(e);
				a.setNo_article(numeroArticle);
				a.setNomArticle(nom);
				a.setDebutEnchere(debut);
				a.setFinEnchere(fin);
				a.setPrixInit(prix);

				getEnchere.add(a);
			}
			rs.close();
			pstmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return getEnchere;
	}

	@Override
	public void updatePrixVente(int nouveauPrixVente, int noArticle) throws BusinessException, SQLException {
		Connection cnx = null;
		try {
			cnx = ConnectionProvider.getConnection();
			cnx.setAutoCommit(false);
			PreparedStatement pstmt = cnx.prepareStatement(UPDATE_PRIX_VENTE);

			pstmt.setInt(1, nouveauPrixVente);
			pstmt.setInt(2, noArticle);
			int nb = pstmt.executeUpdate();

			if (nb == 1) {
				cnx.commit();
				cnx.close();
				pstmt.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
			cnx.rollback();
			BusinessException businessException = new BusinessException("Mise à jour du prix de vente impossible");
			throw businessException;
		}

	}

	public List<Article> encheresTerminees(String pseudo, String categorie, String filtres) throws BusinessException {

		List<Article> getEncheresTerminees = new ArrayList<>();
		String champ = "%" + filtres + "%";

		try {
			Connection con = ConnectionProvider.getConnection();
			PreparedStatement pstmt = con.prepareStatement(SELECT_MES_VENTES_TERMINEES);
			ResultSet rs = null;

			pstmt.setString(1, categorie);
			pstmt.setString(2, champ);
			pstmt.setString(3, champ);
			pstmt.setString(4, pseudo);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				int numeroArticle = rs.getInt("no_article");
				String nom = rs.getString("nom_article");
				int prix = rs.getInt("prix_initial");
				LocalDate debut = rs.getDate("date_debut_encheres").toLocalDate();
				LocalDate fin = rs.getDate("date_fin_encheres").toLocalDate();

				Enchere e = selectEnchere(numeroArticle);
				Article article = new Article();
				Utilisateur user = new Utilisateur();
				user.setPseudo(rs.getString("pseudo"));

				if (e != null) {
					e.setMontantEnchere(e.getMontantEnchere());
					article.setDerniereEnchere(e);
				} else {
					e = new Enchere();
					e.setMontantEnchere(rs.getInt("prix"));
					article.setDerniereEnchere(e);
				}

				article.setNo_article(numeroArticle);
				article.setNomArticle(nom);
				article.setVendeur(user);
				article.setDebutEnchere(debut);
				article.setFinEnchere(fin);
				article.setPrixInit(prix);

				getEncheresTerminees.add(article);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return getEncheresTerminees;
	}

	@Override
	public List<Article> getEncheresRemportees(String pseudo, String categorie, String filtres)
			throws BusinessException {

		List<Article> getEncheresRemportees = new ArrayList<>();
		String champ = "%" + filtres + "%";

		try {
			Connection con = ConnectionProvider.getConnection();
			PreparedStatement pstmt = con.prepareStatement(ENCHERES_REMPORTEES);
			ResultSet rs = null;

			pstmt.setString(1, pseudo);
			pstmt.setString(2, categorie);
			pstmt.setString(3, champ);
			pstmt.setString(4, champ);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				int numeroArticle = rs.getInt("no_article");
				String nom = rs.getString("nom_article");
				LocalDate debut = rs.getDate("date_debut_encheres").toLocalDate();
				LocalDate fin = rs.getDate("date_fin_encheres").toLocalDate();

				Enchere e = selectEnchere(numeroArticle); // dernière enchere
				Article article = new Article();
				Utilisateur user = new Utilisateur();
				user.setPseudo(rs.getString("pseudo"));

				if (e != null) {
					e.setMontantEnchere(e.getMontantEnchere());
					article.setDerniereEnchere(e);
				} else {
					e = new Enchere();
					e.setMontantEnchere(rs.getInt("prix"));
					article.setDerniereEnchere(e);
				}

				article.setNo_article(numeroArticle);
				article.setNomArticle(nom);
				article.setVendeur(user);
				article.setDebutEnchere(debut);
				article.setFinEnchere(fin);

				if (e.getUtilisateur().getPseudo().equals(pseudo)) {
					getEncheresRemportees.add(article);
				}

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return getEncheresRemportees;
	}

	public List<Article> ventesEnCours(String pseudo, String categorie, String filtres) throws BusinessException {

		List<Article> ventesEnCours = new ArrayList<>();
		String champ = "%" + filtres + "%";

		try {
			Connection con = ConnectionProvider.getConnection();
			PreparedStatement pstmt = con.prepareStatement(SELECT_MES_VENTES_ENCOURS);
			ResultSet rs = null;

			pstmt.setString(1, categorie);
			pstmt.setString(2, champ);
			pstmt.setString(3, champ);
			pstmt.setString(4, pseudo);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				int numeroArticle = rs.getInt("no_article");
				String nom = rs.getString("nom_article");
				int prix = rs.getInt("prix_initial");
				LocalDate debut = rs.getDate("date_debut_encheres").toLocalDate();
				LocalDate fin = rs.getDate("date_fin_encheres").toLocalDate();

				Enchere e = selectEnchere(numeroArticle);
				Article article = new Article();
				Utilisateur user = new Utilisateur();
				user.setPseudo(rs.getString("pseudo"));

				if (e != null) {
					e.setMontantEnchere(e.getMontantEnchere());
					article.setDerniereEnchere(e);
				} else {
					e = new Enchere();
					e.setMontantEnchere(rs.getInt("prix"));
					article.setDerniereEnchere(e);
				}

				article.setNo_article(numeroArticle);
				article.setNomArticle(nom);
				article.setVendeur(user);
				article.setDebutEnchere(debut);
				article.setFinEnchere(fin);
				article.setPrixInit(prix);

				ventesEnCours.add(article);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ventesEnCours;
	}

	public List<Article> ventesAVenir(String pseudo, String categorie, String filtres) throws BusinessException {

		List<Article> ventesAVenir = new ArrayList<>();
		String champ = "%" + filtres + "%";

		try {
			Connection con = ConnectionProvider.getConnection();
			PreparedStatement pstmt = con.prepareStatement(SELECT_MES_VENTES_A_VENIR);
			ResultSet rs = null;

			pstmt.setString(1, categorie);
			pstmt.setString(2, champ);
			pstmt.setString(3, champ);
			pstmt.setString(4, pseudo);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				int numeroArticle = rs.getInt("no_article");
				String nom = rs.getString("nom_article");
				int prix = rs.getInt("prix_initial");
				LocalDate debut = rs.getDate("date_debut_encheres").toLocalDate();
				LocalDate fin = rs.getDate("date_fin_encheres").toLocalDate();

				Enchere e = selectEnchere(numeroArticle);
				Article article = new Article();
				Utilisateur user = new Utilisateur();
				user.setPseudo(rs.getString("pseudo"));

				if (e != null) {
					e.setMontantEnchere(e.getMontantEnchere());
					article.setDerniereEnchere(e);
				} else {
					e = new Enchere();
					e.setMontantEnchere(rs.getInt("prix"));
					article.setDerniereEnchere(e);
				}

				article.setNo_article(numeroArticle);
				article.setNomArticle(nom);
				article.setVendeur(user);
				article.setDebutEnchere(debut);
				article.setFinEnchere(fin);
				article.setPrixInit(prix);

				ventesAVenir.add(article);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ventesAVenir;
	}

}

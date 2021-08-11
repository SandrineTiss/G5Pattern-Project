package fr.eni.g5pattern.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.eni.g5pattern.bll.UtilisateurManager;
import fr.eni.g5pattern.bo.Article;
import fr.eni.g5pattern.bo.BusinessException;
import fr.eni.g5pattern.bo.Enchere;
import fr.eni.g5pattern.bo.Utilisateur;
import fr.eni.g5pattern.dal.DAOFactory;
import fr.eni.g5pattern.dal.UtilisateurDAO;

public class UtilisateurDAOJdbcImpl implements UtilisateurDAO {

	private static final String CONNECT = "SELECT no_utilisateur, pseudo, email FROM UTILISATEURS WHERE (email=? OR pseudo=?) "
			+ "AND mot_de_passe=HASHBYTES('SHA1',?);";
	private static final String INSERT_USER = "INSERT INTO UTILISATEURS(pseudo, nom, prenom, email, telephone, rue, code_postal, "
			+ "ville, mot_de_passe, credit, administrateur) VALUES(?, ?, ?, ?, ?, ?, ?, ?, HASHBYTES('SHA1', ?), ?, ?);";
	private static final String VERIF_PSEUDO = "SELECT COUNT(pseudo) FROM UTILISATEURS where pseudo=?;";
	private static final String VERIF_MAIL = "SELECT COUNT(email) FROM UTILISATEURS where email=?;";
	private static final String INSERT_PSEUDO_ERREUR = "Le pseudo choisi est deja  utilise. Veuillez en choisir un autre.";
	private static final String INSERT_MAIL_ERREUR = "L'e-mail choisi est deja  utilise. Veuillez en choisir un autre.";
	private static final String PROFIL = " SELECT no_utilisateur, pseudo, nom, prenom, email, telephone, rue, code_postal, ville, credit "
			+ "FROM UTILISATEURS WHERE pseudo=?;";
	private static final String UPDATE_PROFIL = "UPDATE UTILISATEURS SET pseudo=?, nom=?, prenom=?, email=?, telephone=?, rue=?, "
			+ "code_postal=?, ville=?, mot_de_passe=(HASHBYTES('SHA1', ?)) WHERE pseudo=? AND mot_de_passe=HASHBYTES('SHA1',?); ";
	private static final String DELETE_USER = "DELETE FROM UTILISATEURS WHERE pseudo=? AND mot_de_passe=HASHBYTES('SHA1',?);";
	private static final String VERIF_FOR_UPDATE = "SELECT COUNT(pseudo),COUNT(email) FROM UTILISATEURS WHERE no_utilisateur<>? AND (pseudo=? OR email=?);";
	private static final String SELECT_USER_ID = "SELECT * FROM UTILISATEURS WHERE no_utilisateur = ? ;";
	private static final String UPDATE_PASSWORD = "UPDATE UTILISATEURS SET mot_de_passe=(HASHBYTES('SHA1', ?)) WHERE pseudo=? AND email=? ; ";
	private static final String UPDATE_CREDIT = "UPDATE UTILISATEURS SET credit = ? WHERE no_utilisateur = ?";
	public static final String CREDIT_DEBIT_ENCHERISSEUR = "UPDATE UTILISATEURS SET credit=? WHERE pseudo=?";

	// ---------------------------------//
	// ---------------------------------//

	public int verifPseudo(String pseudo) throws BusinessException {
		Connection cnx = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count = 0;

		try {
			cnx = ConnectionProvider.getConnection();
			pstmt = cnx.prepareStatement(VERIF_PSEUDO);
			pstmt.setString(1, pseudo);
			rs = pstmt.executeQuery();

			rs.next();
			count = rs.getInt(1);
			if (count != 0) {
				throw new BusinessException(INSERT_PSEUDO_ERREUR);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (cnx != null)
				try {
					cnx.close();
				} catch (SQLException e) {
					throw new BusinessException("problème SQL 1");
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException e) {
					throw new BusinessException("problème SQL 2");
				}
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					throw new BusinessException("problème SQL 3");
				}

		}
		return count;
	}

	public int verifMail(String mail) throws BusinessException {
		Connection cnx = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count = 0;

		try {
			cnx = ConnectionProvider.getConnection();
			pstmt = cnx.prepareStatement(VERIF_MAIL);
			pstmt.setString(1, mail);
			rs = pstmt.executeQuery();

			rs.next();
			count = rs.getInt(1);
			if (count != 0) {
				throw new BusinessException(INSERT_MAIL_ERREUR);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (cnx != null)
				try {
					cnx.close();
				} catch (SQLException e) {
					throw new BusinessException("problème SQL 4");
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException e) {
					throw new BusinessException("problème SQL 5");
				}
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					throw new BusinessException("problème SQL 6");
				}
		}
		return count;
	}

	@Override
	public Utilisateur insert(Utilisateur user) throws BusinessException {
		PreparedStatement pstmt = null;
		Connection cnx = null;
		ResultSet rs = null;
		verifMail(user.getMail());
		verifPseudo(user.getPseudo());

		try {
			cnx = ConnectionProvider.getConnection();
			cnx.setAutoCommit(false);
			pstmt = cnx.prepareStatement(INSERT_USER, PreparedStatement.RETURN_GENERATED_KEYS);

			pstmt.setString(1, user.getPseudo());
			pstmt.setString(2, user.getNom());
			pstmt.setString(3, user.getPrenom());
			pstmt.setString(4, user.getMail());
			pstmt.setString(5, user.getPhone());
			pstmt.setString(6, user.getRue());
			pstmt.setString(7, user.getCp());
			pstmt.setString(8, user.getVille());
			pstmt.setString(9, user.getPassword());
			pstmt.setInt(10, user.getCredit());
			pstmt.setInt(11, user.getAdmin());

			try {
				verifPseudo(user.getPseudo());
			} catch (Exception e) {

			}
			try {
				verifMail(user.getMail());
			} catch (Exception e) {

			}
			try {
				pstmt.executeUpdate();
				rs = pstmt.getGeneratedKeys();
				if (rs.next()) {
					user.setNo_users(rs.getInt(1));
				}
				cnx.commit();
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {

		} finally {
			if (cnx != null)
				try {
					cnx.close();
				} catch (SQLException e) {

				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException e) {

				}
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {

				}
		}
		return user;
	}

	// --------------------------------//
	// VERIFIER PSEUDO ALPHANUMERIQUE //
	// --------------------------------//

	public boolean pseudoConforme(Utilisateur utilisateur) {

		String pseudo = utilisateur.getPseudo();
		boolean ok;
		int i = 0;

		do {
			ok = Character.isLetterOrDigit(pseudo.charAt(i));
			i++;
		} while (i < pseudo.length() || !ok);
		return ok;
	}

	@Override
	public Utilisateur connect(String login, String password) throws BusinessException {

		if (login == null || password == null) {
			BusinessException businessException = new BusinessException("erreur données");
			throw businessException;
		}

		Utilisateur user = new Utilisateur();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(CONNECT);
			pstmt.setString(1, login);
			pstmt.setString(2, login);
			pstmt.setString(3, password);

			ResultSet rs = pstmt.executeQuery();

			// si aucun rà©sultat en bdd

			if (!rs.next()) {
				BusinessException businessException = new BusinessException(
						"Ce couple login/mot de passe n'appartient pas à  un utilisateur enregistré !");
				throw businessException;
			} else

			{
				user.setNo_users(rs.getInt("no_utilisateur"));
				user.setPseudo(rs.getString("pseudo"));
			}
			rs.close();
			cnx.close();

		} catch (Exception e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException("Connexion à  la base impossible");
			throw businessException;
		}
		return user;
	}

	@Override
	public Utilisateur profil(String pseudo) throws BusinessException {
		if (pseudo == null) {
			BusinessException businessException = new BusinessException("utilisateur inexistant");
			throw businessException;
		}

		Utilisateur user = new Utilisateur();

		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(PROFIL);
			pstmt.setString(1, pseudo);

			ResultSet rs = pstmt.executeQuery();

			if (!rs.next()) {
				rs.close();
				cnx.close();
				BusinessException businessException = new BusinessException("utilisateur inexistant");
				throw businessException;
			} else
			// si l'utilisateur existe, alors cet utilisateur reçoit son id (vers la
			// servlet
			// pour pouvoir être utilisée dans un cookie ?)
			{
				user.setNo_users(rs.getInt("no_utilisateur"));
				user.setPseudo(rs.getString("pseudo"));
				user.setNom(rs.getString("nom"));
				user.setPrenom(rs.getString("prenom"));
				user.setMail(rs.getString("email"));
				user.setPhone(rs.getString("telephone"));
				user.setRue(rs.getString("rue"));
				user.setCp(rs.getString("code_postal"));
				user.setVille(rs.getString("ville"));
				user.setCredit(rs.getInt("credit"));
				rs.close();
				cnx.close();
				return user;
			}

		} catch (Exception e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException("Connexion à  la base impossible");
			throw businessException;
		}

	}

	@Override
	public Utilisateur update(Utilisateur user, String aModifier, String password)
			throws BusinessException, SQLException {
		PreparedStatement pstmt = null;
		Connection cnx = null;
		int id = verifUser(aModifier);
		ResultSet resultat;
		if (user == null || password == null || aModifier == null) {
			BusinessException businessException = new BusinessException("erreur données");
			throw businessException;
		}
		try {

			cnx = ConnectionProvider.getConnection();
			cnx.setAutoCommit(false);

			pstmt = cnx.prepareStatement(VERIF_FOR_UPDATE);

			pstmt.setInt(1, id);
			pstmt.setString(2, user.getPseudo());
			pstmt.setString(3, user.getMail());

			resultat = pstmt.executeQuery();

			resultat.next();

			int count = resultat.getInt(1);

			if (count == 0) {
				try {

					PreparedStatement pstmt1 = cnx.prepareStatement(UPDATE_PROFIL);

					pstmt1.setString(1, user.getPseudo());
					pstmt1.setString(2, user.getNom());
					pstmt1.setString(3, user.getPrenom());
					pstmt1.setString(4, user.getMail());
					pstmt1.setString(5, user.getPhone());
					pstmt1.setString(6, user.getRue());
					pstmt1.setString(7, user.getCp());
					pstmt1.setString(8, user.getVille());
					pstmt1.setString(9, user.getPassword());
					pstmt1.setString(10, aModifier);
					pstmt1.setString(11, password);
					int rs = pstmt1.executeUpdate();
					if (rs > 0) {
						cnx.commit();
						cnx.close();
						pstmt1.close();
						return user;
					} else {
						cnx.rollback();
						cnx.close();
						pstmt1.close();
						Utilisateur u = new Utilisateur();
						return u;
					}

				} catch (Exception e) {
					cnx.rollback();
					cnx.close();
					e.printStackTrace();

					try {
						cnx.rollback();
					} catch (SQLException e1) {
						BusinessException businessException = new BusinessException(
								"Mise à  jour du profil impossible");
						throw businessException;
					}
					BusinessException businessException = new BusinessException("Mise à  jour du profil impossible");
					throw businessException;

				}
			} else {
				BusinessException businessException = new BusinessException(
						"Mise à  jour du profil impossible, ce pseudo ou cette adresse mail est déjà  utilisé");
				throw businessException;
			}

		} catch (Exception e) {
			e.printStackTrace();
			try {
				cnx.rollback();
			} catch (SQLException e1) {
				BusinessException businessException = new BusinessException("Mise à  jour du profil impossible");
				throw businessException;
			}
			BusinessException businessException = new BusinessException("Mise à  jour du profil impossible");
			throw businessException;
		}
	}

	private int verifUser(String aModifier) throws BusinessException {
		Utilisateur user = new Utilisateur();

		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(PROFIL);
			pstmt.setString(1, aModifier);

			ResultSet rs = pstmt.executeQuery();

			if (!rs.next()) {
				BusinessException businessException = new BusinessException("utilisateur inexistant");
				throw businessException;
			} else
			// si l'utilisateur existe, alors cet utilisateur reÃ§oit son id
			{
				user.setNo_users(rs.getInt("no_utilisateur"));
			}
			rs.close();
			cnx.close();
		} catch (Exception e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException("Connexion à  la base impossible");
			throw businessException;
		}
		return user.getNo_users();
	}

	@Override
	public void delete(Utilisateur user) throws BusinessException {
		PreparedStatement pstmt = null;
		Connection cnx = null;

		if (user == null) {
			BusinessException businessException = new BusinessException("Erreur, ce compte n'existe pas !");
			throw businessException;
		}

		try {
			cnx = ConnectionProvider.getConnection();
			cnx.setAutoCommit(false);

			// rechercher les ventes en cours

			List<Article> VentesEnCours = new ArrayList<>();

			VentesEnCours = DAOFactory.getArticleDAO().ventesEnCours(user.getPseudo(), "%", "%");

			// recréditer le dernier enchérisseur de chaque article

			if (!VentesEnCours.isEmpty()) {
				for (Article article : VentesEnCours) {

					// récupération dernière enchère

					Enchere e = DAOFactory.getArticleDAO().selectEnchere(article.getNo_article());

					// recrédit dernier enchérisseur

					if (e.getUtilisateur() != null) {

						Utilisateur precedent = UtilisateurManager.getInstance().profil(e.getUtilisateur().getPseudo());

						PreparedStatement pstmt1 = cnx.prepareStatement(CREDIT_DEBIT_ENCHERISSEUR);

						pstmt1.setInt(1, precedent.getCredit() + e.getMontantEnchere());

						pstmt1.setString(2, precedent.getPseudo());

						pstmt1.executeUpdate();

						pstmt1.close();
					}
				}
			}

			pstmt = cnx.prepareStatement(DELETE_USER);

			pstmt.setString(1, user.getPseudo());
			pstmt.setString(2, user.getPassword());

			int rs = pstmt.executeUpdate();

			if (rs == 1) {
				cnx.commit();
			} else {
				cnx.rollback();
				BusinessException e = new BusinessException("Mauvais mot de passe");
				throw e;
			}
			cnx.close();
			pstmt.close();

		} catch (Exception e) {
			e.printStackTrace();
			BusinessException except = new BusinessException("Mauvais mot de passe, suppression impossible");
			throw except;
		}

	}

	public Utilisateur getUser(int no_utilisateur) throws BusinessException, SQLException {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Utilisateur user = new Utilisateur();
		try {
			con = ConnectionProvider.getConnection();
			try {
				pstmt = con.prepareStatement(SELECT_USER_ID);

				pstmt.setInt(1, no_utilisateur);
				rs = pstmt.executeQuery();
				rs.next();
				user.setPseudo(rs.getString("pseudo"));
				user.setCredit(rs.getInt("credit"));
				// a completer si besoin
			} finally {
				try {
					rs.close();
				} catch (SQLException e) {
				}
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		} catch (SQLException e) {
		} finally {
			con.close();
		}
		return user;
	}

	@Override
	public Utilisateur updatePassword(Utilisateur user) throws BusinessException, SQLException {
		PreparedStatement pstmt = null;
		Connection cnx = null;
		ResultSet resultat;
		int ok = 0;

		try {
			cnx = ConnectionProvider.getConnection();
			cnx.setAutoCommit(false);
			PreparedStatement pstmt1 = cnx.prepareStatement(UPDATE_PASSWORD);

			pstmt1.setString(1, user.getPassword());
			pstmt1.setString(2, user.getPseudo());
			pstmt1.setString(3, user.getMail());

			ok = pstmt1.executeUpdate();

			if (ok == 0) {
				BusinessException businessException = new BusinessException("utilisateur inexistant");
				throw businessException;
			}

			cnx.commit();
			cnx.close();
			pstmt1.close();

		} catch (Exception e) {
			e.printStackTrace();
			cnx.rollback();
			BusinessException businessException = new BusinessException("Mise à  jour du mot de passe impossible");
			throw businessException;
		}

		return user;
	}

	@Override
	public void updateCreditVente(int nouveauCreditVente, int noUser) throws BusinessException, SQLException {
		Connection cnx = null;
		try {
			cnx = ConnectionProvider.getConnection();
			cnx.setAutoCommit(false);
			PreparedStatement pstmt = cnx.prepareStatement(UPDATE_CREDIT);

			pstmt.setInt(1, nouveauCreditVente);
			pstmt.setInt(2, noUser);

			int nb = pstmt.executeUpdate();

			if (nb == 1) {
				cnx.commit();
				cnx.close();
				pstmt.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
			cnx.rollback();
			BusinessException businessException = new BusinessException("Mise à  jour du credit impossible");
			throw businessException;
		}

	}

}

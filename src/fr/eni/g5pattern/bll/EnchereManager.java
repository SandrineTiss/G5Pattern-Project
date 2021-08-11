package fr.eni.g5pattern.bll;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import fr.eni.g5pattern.bo.Article;
import fr.eni.g5pattern.bo.BusinessException;
import fr.eni.g5pattern.bo.Categorie;
import fr.eni.g5pattern.bo.Enchere;
import fr.eni.g5pattern.bo.Retrait;
import fr.eni.g5pattern.bo.Utilisateur;
import fr.eni.g5pattern.dal.DAOFactory;

public class EnchereManager {

	private static EnchereManager instance;

	public static EnchereManager getInstance() {
		if (instance == null) {
			instance = new EnchereManager();
		}
		return instance;
	}

	private EnchereManager() {

	}

	// creation nouvel article
	public Article insertArticle(String nomArticle, String description, Categorie categorie, int prixInit,
			LocalDate debutEnchere, LocalDate finEnchere, Utilisateur vendeur) throws BusinessException {

		Article article = new Article();
		article.setNomArticle(nomArticle);
		article.setDescription(description);
		article.setCategorie(categorie);
		article.setPrixInit(prixInit);
		article.setDebutEnchere(debutEnchere);
		article.setFinEnchere(finEnchere);
		article.setVendeur(vendeur);

		DAOFactory.getArticleDAO().insertEnchere(article);
		return article;
	}

	public Retrait insertRetrait(Article article, String rue, String cp, String ville) {
		Retrait retrait = new Retrait();
		article.setNo_article(article.getNo_article());
		retrait.setNomRue(rue);
		retrait.setCodePostal(cp);
		retrait.setNomVille(ville);

		try {
			DAOFactory.getArticleDAO().insertRetrait(article, retrait);
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		return retrait;
	}

	// récupération d'un article avec sa catégorie et son lieu de retrait

	public Article selectArticleEnchere(int id) throws BusinessException {

		Article a = DAOFactory.getArticleDAO().selectArticleEnchere(id);

		if (a != null) {
			return a;
		} else {
			throw new BusinessException("impossible d'afficher cet article");
		}
	}

	public List<Article> rechercheFiltres(String categorie, String filtres, int page) throws BusinessException {

		List<Article> articles = DAOFactory.getArticleDAO().rechercheFiltres(categorie, filtres);

		if (articles != null) {
			return articles;
		} else {
			throw new BusinessException("Aucun articles correspondant à votre recherche.");
		}
	}

	public void encherir(Integer idArticle, Integer proposition, Integer derniereEnchere, Integer encherisseur,
			String encherisseurPrecedent) throws BusinessException {
		// enchérisseur précédent
		Enchere e = new Enchere();

		e.setMontantEnchere(proposition);
		e.setNo_article(idArticle);
		if (!encherisseurPrecedent.equals("")) {
			Utilisateur precedent = new Utilisateur();
			precedent.setPseudo(encherisseurPrecedent);
			precedent.setCredit(derniereEnchere);
			e.setUtilisateur(precedent);
		}
		e.setNo_utilisateur(encherisseur);

		DAOFactory.getArticleDAO().encherir(e);

	}

	// modification Article

	public Article modifierArticle(String nomArticle, String description, Categorie categorie, int prixInit,
			LocalDate debutEnchere, LocalDate finEnchere, int idArticle, Utilisateur vendeur) throws BusinessException {

		Article article = new Article();
		article.setNomArticle(nomArticle);
		article.setDescription(description);
		article.setCategorie(categorie);
		article.setPrixInit(prixInit);
		article.setDebutEnchere(debutEnchere);
		article.setFinEnchere(finEnchere);
		article.setVendeur(vendeur);
		article.setNo_article(idArticle);
		DAOFactory.getArticleDAO().updateEnchere(article);
		return article;
	}

	// modification Retrait
	public Retrait modifierRetrait(Article article, String rue, String cp, String ville) {
		Retrait retrait = new Retrait();
		article.setNo_article(article.getNo_article());
		retrait.setNomRue(rue);
		retrait.setCodePostal(cp);
		retrait.setNomVille(ville);

		try {
			DAOFactory.getArticleDAO().updateRetrait(article, retrait);
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		return retrait;
	}

	public void supprimerEnchere(int idArticle) throws BusinessException {
		DAOFactory.getArticleDAO().deleteEnchere(idArticle);
	}

	public List<Article> getArticleEncheri(int no_article, String pseudo) throws SQLException, BusinessException {
		List<Article> e = DAOFactory.getArticleDAO().getArticleEncheri(pseudo);

		if (e != null) {
			return e;
		} else {
			throw new BusinessException("Aucune offre d'enchere actuellement.");
		}
	}

	public void ajoutPrixVente(int nouveauPrixVente, int noArticle) throws BusinessException, SQLException {
		Article article = new Article();
		article.setNo_article(noArticle);
		DAOFactory.getArticleDAO().updatePrixVente(nouveauPrixVente, noArticle);
	}

	public List<Article> encheresTerminees(String pseudo, String categorie, String filtres) throws BusinessException {
		List<Article> e = DAOFactory.getArticleDAO().encheresTerminees(pseudo, categorie, filtres);

		if (e != null) {
			return e;
		} else {
			throw new BusinessException("Aucune offre d'enchere actuellement.");
		}
	}

	public List<Article> getEncheresRemportees(String pseudo, String libelle, String filtres)
			throws SQLException, BusinessException {
		List<Article> e = DAOFactory.getArticleDAO().getEncheresRemportees(pseudo, libelle, filtres);

		if (e != null) {
			return e;
		} else {
			throw new BusinessException("Aucune offre d'enchere actuellement.");
		}
	}

	public List<Article> ventesEnCours(String pseudo, String categorie, String filtres) throws BusinessException {
		List<Article> e = DAOFactory.getArticleDAO().ventesEnCours(pseudo, categorie, filtres);

		if (e != null) {
			return e;
		} else {
			throw new BusinessException("Aucune offre d'enchere actuellement.");
		}
	}

	public List<Article> ventesAVenir(String pseudo, String categorie, String filtres) throws BusinessException {
		List<Article> e = DAOFactory.getArticleDAO().ventesAVenir(pseudo, categorie, filtres);
		if (e != null) {
			return e;
		} else {
			throw new BusinessException("Aucune offre d'enchere actuellement.");
		}
	}

	public Enchere selectEnchere(int idArticle) throws BusinessException {
		Enchere e = DAOFactory.getArticleDAO().selectEnchere(idArticle);

		if (e != null) {
			return e;
		} else {
			throw new BusinessException("Aucune enchere actuellement.");
		}

	}

}

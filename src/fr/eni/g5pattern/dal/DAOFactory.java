package fr.eni.g5pattern.dal;

import fr.eni.g5pattern.jdbc.ArticleDAOJdbcImpl;
import fr.eni.g5pattern.jdbc.UtilisateurDAOJdbcImpl;

public abstract class DAOFactory {

	public static UtilisateurDAO getUtilisateurDAO() {
		return new UtilisateurDAOJdbcImpl();
	}

	public static ArticleDAO getArticleDAO() {
		return new ArticleDAOJdbcImpl();
	}

}

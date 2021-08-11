package fr.eni.g5pattern.dal;

import java.sql.SQLException;
import java.util.List;

import fr.eni.g5pattern.bo.Article;
import fr.eni.g5pattern.bo.BusinessException;
import fr.eni.g5pattern.bo.Enchere;
import fr.eni.g5pattern.bo.Retrait;

public interface ArticleDAO {

	public void insertEnchere(Article article) throws BusinessException;

	public void insertRetrait(Article article, Retrait retrait) throws BusinessException;

	public List<Article> rechercheFiltres(String categorie, String filtres) throws BusinessException;

	public Article selectArticleEnchere(int id) throws BusinessException;

	public void encherir(Enchere e) throws BusinessException;

	public void updateEnchere(Article article) throws BusinessException;

	public void updateRetrait(Article article, Retrait retrait) throws BusinessException;

	public void deleteEnchere(int idArticle) throws BusinessException;

	public Enchere selectEnchere(int idArticle) throws BusinessException;

	public List<Article> getArticleEncheri(String pseudo) throws BusinessException;

	public void updatePrixVente(int nouveauPrixVente, int noArticle) throws BusinessException, SQLException;

	public List<Article> encheresTerminees(String pseudo, String categorie, String filtres) throws BusinessException;

	public List<Article> getEncheresRemportees(String pseudo, String libelle, String filtres) throws BusinessException;

	public List<Article> ventesEnCours(String pseudo, String categorie, String filtres) throws BusinessException;

	public List<Article> ventesAVenir(String pseudo, String categorie, String filtres) throws BusinessException;;

}
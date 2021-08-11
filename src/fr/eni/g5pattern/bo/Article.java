package fr.eni.g5pattern.bo;

import java.time.LocalDate;

public class Article {

	private int no_article;
	private String nomArticle;
	private String description;
	private LocalDate debutEnchere;
	private LocalDate finEnchere;
	private int prixInit;
	private int prixVente;
	private Utilisateur vendeur;
	private Categorie categorie;
	private Enchere derniereEnchere;
	private Retrait retrait;

	public int getNo_article() {
		return no_article;
	}

	public void setNo_article(int no_article) {
		this.no_article = no_article;
	}

	public String getNomArticle() {
		return nomArticle;
	}

	public void setNomArticle(String nomArticle) {
		this.nomArticle = nomArticle;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getDebutEnchere() {
		return debutEnchere;
	}

	public void setDebutEnchere(LocalDate debutEnchere) {
		this.debutEnchere = debutEnchere;
	}

	public LocalDate getFinEnchere() {
		return finEnchere;
	}

	public void setFinEnchere(LocalDate finEnchere) {
		this.finEnchere = finEnchere;
	}

	public int getPrixInit() {
		return prixInit;
	}

	public void setPrixInit(int prixInit) {
		this.prixInit = prixInit;
	}

	public int getPrixVente() {
		return prixVente;
	}

	public void setPrixVente(int prixVente) {
		this.prixVente = prixVente;
	}

	public Utilisateur getVendeur() {
		return vendeur;
	}

	public void setVendeur(Utilisateur vendeur) {
		this.vendeur = vendeur;
	}

	public Categorie getCategorie() {
		return categorie;
	}

	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}

	public Enchere getDerniereEnchere() {
		return derniereEnchere;
	}

	public void setDerniereEnchere(Enchere derniereEnchere) {
		this.derniereEnchere = derniereEnchere;
	}

	public Retrait getRetrait() {
		return retrait;
	}

	public void setRetrait(Retrait retrait) {
		this.retrait = retrait;
	}

	// --------------//
	// CONSTRUCTEURS//
	// --------------//
	public Article() {
	}

	public Article(int no_article, String nomArticle, String description, LocalDate debutEnchere, LocalDate finEnchere,
			int prixInit, int prixVente, Utilisateur vendeur, Categorie categorie) {
		this.no_article = no_article;
		this.nomArticle = nomArticle;
		this.description = description;
		this.debutEnchere = debutEnchere;
		this.finEnchere = finEnchere;
		this.prixInit = prixInit;
		this.prixVente = prixVente;
		this.vendeur = vendeur;
		this.categorie = categorie;
	}

	private Article(String nomArticle, String description, LocalDate debutEnchere, LocalDate finEnchere, int prixInit,
			int prixVente, Utilisateur vendeur, Categorie categorie) {
		this.nomArticle = nomArticle;
		this.description = description;
		this.debutEnchere = debutEnchere;
		this.finEnchere = finEnchere;
		this.prixInit = prixInit;
		this.prixVente = prixVente;
		this.vendeur = vendeur;
		this.categorie = categorie;
	}

	@Override
	public String toString() {
		return "Article [no_article=" + no_article + ", nomArticle=" + nomArticle + ", description=" + description
				+ ", debutEnchere=" + debutEnchere + ", finEnchere=" + finEnchere + ", prixInit=" + prixInit
				+ ", prixVente=" + prixVente + ", vendeur=" + vendeur + ", categorie=" + categorie
				+ ", derniereEnchere=" + derniereEnchere + ", retrait=" + retrait + "]";
	}

	
}

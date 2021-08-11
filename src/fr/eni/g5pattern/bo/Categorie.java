package fr.eni.g5pattern.bo;

public class Categorie {

	private int numCategorie;
	private String libelle;
	
	
	public Categorie() {
		
	}
	
	public Categorie(int numCategorie, String libelle) {
		this.numCategorie = numCategorie;
		this.libelle = libelle;
	}
	
	public Categorie(String libelle) {
		this.libelle = libelle;
	}
	
	
	public int getNumCategorie() {
		return numCategorie;
	}
	public void setNumCategorie(int numCategorie) {
		this.numCategorie = numCategorie;
	}
	public String getLibelle() {
		return libelle;
	}
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	
	
	@Override
	public String toString() {
		return "Categorie [numCategorie=" + numCategorie + ", libelle=" + libelle + "]";
	}
	
	
	
}

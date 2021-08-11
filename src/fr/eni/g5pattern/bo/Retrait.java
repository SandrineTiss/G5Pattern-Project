package fr.eni.g5pattern.bo;

public class Retrait {
	
	private int numArticle;
	private String nomRue;
	private String codePostal;
	private String nomVille;
	
	
	public Retrait() {
	}


	public Retrait(int numArticle, String nomRue, String codePostal, String nomVille) {
		super();
		this.numArticle =numArticle;
		this.nomRue = nomRue;
		this.codePostal = codePostal;
		this.nomVille = nomVille;
	}

	

	public int getNumArticle() {
		return numArticle;
	}


	public void setNumArticle(int numArticle) {
		this.numArticle = numArticle;
	}


	public String getNomRue() {
		return nomRue;
	}


	public void setNomRue(String nomRue) {
		this.nomRue = nomRue;
	}


	public String getCodePostal() {
		return codePostal;
	}


	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}


	public String getNomVille() {
		return nomVille;
	}


	public void setNomVille(String nomVille) {
		this.nomVille = nomVille;
	}


	@Override
	public String toString() {
		return "Retrait [numArticle=" + numArticle + ", nomRue=" + nomRue + ", codePostal=" + codePostal + ", nomVille="
				+ nomVille + "]";
	}



	
	
	

}

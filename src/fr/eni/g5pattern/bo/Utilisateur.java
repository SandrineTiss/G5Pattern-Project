package fr.eni.g5pattern.bo;

public class Utilisateur {

	private int no_users;
	private String pseudo;
	private String nom;
	private String prenom;
	private String mail;
	private String phone;
	private String rue;
	private String cp;
	private String ville;
	private String password;
	private int credit = 100;
	private int admin;

	// --------------//
	// CONSTRUCTEURS//
	// --------------//

	public Utilisateur() {

	}

	public Utilisateur(String pseudo, String nom, String prenom, String mail, String phone, String rue, String cp,
			String ville, String password) {
		this.pseudo = pseudo;
		this.nom = nom;
		this.prenom = prenom;
		this.mail = mail;
		this.phone = phone;
		this.rue = rue;
		this.cp = cp;
		this.ville = ville;
		this.password = password;
	}

	public Utilisateur(String pseudo, String nom, String prenom, String mail, String phone, String rue, String cp,
			String ville, int credit) {
		this.pseudo = pseudo;
		this.nom = nom;
		this.prenom = prenom;
		this.mail = mail;
		this.phone = phone;
		this.rue = rue;
		this.cp = cp;
		this.ville = ville;
		this.credit = credit;
	}

	public Utilisateur(String pseudo, String nom, String prenom, String email, String phone, String rue, String cp,
			String ville) {
		this.pseudo = pseudo;
		this.nom = nom;
		this.prenom = prenom;
		this.mail = email;
		this.phone = phone;
		this.rue = rue;
		this.cp = cp;
		this.ville = ville;

	}

	// ------------------//
	// GETTERS & SETTERS//
	// ------------------//
	public int getNo_users() {
		return no_users;
	}

	public void setNo_users(int no_users) {
		this.no_users = no_users;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRue() {
		return rue;
	}

	public void setRue(String rue) {
		this.rue = rue;
	}

	public String getCp() {
		return cp;
	}

	public void setCp(String cp) {
		this.cp = cp;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getCredit() {
		return credit;
	}

	public void setCredit(int credit) {
		this.credit = credit;
	}

	public int getAdmin() {
		return admin;
	}

	public void setAdmin() {
		this.admin = 0;
	}

	@Override
	public String toString() {
		return "Utilisateur [pseudo=" + pseudo + ", nom=" + nom + ", prenom=" + prenom + ", mail=" + mail + ", phone="
				+ phone + ", rue=" + rue + ", cp=" + cp + ", ville=" + ville + ", password=" + password + ", credit="
				+ credit + "]";
	}
}

package fr.eni.g5pattern.bll;

import java.sql.SQLException;

import fr.eni.g5pattern.bo.BusinessException;
import fr.eni.g5pattern.bo.Utilisateur;
import fr.eni.g5pattern.dal.DAOFactory;

public class UtilisateurManager {

	private static UtilisateurManager instance;

	public static UtilisateurManager getInstance() {
		if (instance == null) {
			instance = new UtilisateurManager();
		}
		return instance;
	}

	private UtilisateurManager() {
	}

	public Utilisateur connect(String login, String password) throws BusinessException {

		Utilisateur u = DAOFactory.getUtilisateurDAO().connect(login, password);
		if (u != null) {
			return u;
		} else {
			throw new BusinessException("Login/Mot de passe incorrect");
		}

	}

	public Utilisateur insert(String pseudo, String nom, String prenom, String mail, String phone, String rue,
			String cp, String ville, String password, int credit, int admin) throws BusinessException {

		Utilisateur user = new Utilisateur();
		user.setPseudo(pseudo);
		user.setNom(nom);
		user.setPrenom(prenom);
		user.setMail(mail);
		user.setPhone(phone);
		user.setRue(rue);
		user.setCp(cp);
		user.setVille(ville);
		user.setPassword(password);
		user.setCredit(credit);
		user.setAdmin();

		DAOFactory.getUtilisateurDAO().insert(user);
		return user;

	}

	public Utilisateur profil(String pseudo) throws BusinessException {

		Utilisateur u = DAOFactory.getUtilisateurDAO().profil(pseudo);

		if (u != null) {
			return u;
		} else {
			throw new BusinessException("impossible d'afficher cet utilisateur");
		}
	}

	public Utilisateur update(String aModifier, String pseudo, String nom, String prenom, String mail, String phone,
			String rue, String cp, String ville, String password, String newPassword)
			throws BusinessException, SQLException {
		Utilisateur user = new Utilisateur();
		user.setPseudo(pseudo);
		user.setNom(nom);
		user.setPrenom(prenom);
		user.setMail(mail);
		user.setPhone(phone);
		user.setRue(rue);
		user.setCp(cp);
		user.setVille(ville);
		user.setPassword(newPassword);

		Utilisateur u = DAOFactory.getUtilisateurDAO().update(user, aModifier, password);
		return u;
	}

	public void delete(String pseudo, String password) throws BusinessException {
		Utilisateur user = new Utilisateur();
		user.setPseudo(pseudo);
		user.setPassword(password);
		DAOFactory.getUtilisateurDAO().delete(user);
	}

	public Utilisateur updatePassword(String pseudo, String email, String password)
			throws BusinessException, SQLException {
		Utilisateur user = new Utilisateur();
		user.setPseudo(pseudo);
		user.setPassword(password);
		user.setMail(email);
		Utilisateur u = DAOFactory.getUtilisateurDAO().updatePassword(user);
		return u;
	}

	public void recreditVendeur(int nouveauCredit, int noUser) throws BusinessException, SQLException {
		Utilisateur user = new Utilisateur();
		user.setNo_users(noUser);
		DAOFactory.getUtilisateurDAO().updateCreditVente(nouveauCredit, noUser);
	}

}

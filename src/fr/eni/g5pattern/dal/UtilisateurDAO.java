package fr.eni.g5pattern.dal;

import java.sql.SQLException;

import fr.eni.g5pattern.bo.BusinessException;
import fr.eni.g5pattern.bo.Utilisateur;

public interface UtilisateurDAO {

// CREATE

	public Utilisateur insert(Utilisateur user) throws BusinessException;

// READ

	public Utilisateur connect(String login, String password) throws BusinessException;

	public Utilisateur profil(String pseudo) throws BusinessException;

	public Utilisateur getUser(int no_user) throws BusinessException, SQLException;

// UPDATE

	public Utilisateur update(Utilisateur user, String aModifier, String password)
			throws BusinessException, SQLException;

	public Utilisateur updatePassword(Utilisateur user) throws BusinessException, SQLException;
	
	public void updateCreditVente(int nouveauCreditVente, int noUser) throws BusinessException, SQLException;

// DELETE

	void delete(Utilisateur user) throws BusinessException;

}

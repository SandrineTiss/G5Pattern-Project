package fr.eni.g5pattern.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.g5pattern.bll.UtilisateurManager;
import fr.eni.g5pattern.bo.BusinessException;
import fr.eni.g5pattern.bo.Utilisateur;

/**
 * Servlet implementation class ModifierMonProfil
 */
@WebServlet("/ModifierMonProfil")
public class ModifierMonProfil extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String pseudo = null;
		Cookie[] cookies = request.getCookies();
		Boolean monProfil = false;
		if (cookies == null) {
			Cookie cookie = new Cookie("pseudo", "");
			response.addCookie(cookie);
			request.setAttribute("error", "Vous n'êtes pas connecté !");
			request.getRequestDispatcher("/Connexion").forward(request, response);
		} else {

			for (Cookie unCookie : cookies) {
				// renvoie de l'attibut "pseudo" du cookie
				if (unCookie.getName().equals("pseudo")) {

					monProfil = true;
					pseudo = unCookie.getValue();
				}
			}
		}
		request.setAttribute("monProfil", monProfil);

		// Renvoi des données pour affichage

		if (pseudo != null) {

			Utilisateur user = new Utilisateur();
			try {
				user = UtilisateurManager.getInstance().profil(pseudo);
			} catch (BusinessException e) {
				e.printStackTrace();
				request.setAttribute("error", "Affichage impossible");
				request.getRequestDispatcher("/Accueil").forward(request, response);
			}
			request.setAttribute("user_pseudo", user);
			request.getRequestDispatcher("/WEB-INF/jsp/modifierMonProfil.jsp").forward(request, response);

		} else {

			request.setAttribute("error", "Merci de vous connecter pour accéder à votre profil");
			request.getRequestDispatcher("/Connexion").forward(request, response);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		BusinessException be = new BusinessException();
		String userAModifier = null;
		String pseudo = request.getParameter("pseudo");
		String nom = request.getParameter("nom");
		String prenom = request.getParameter("prenom");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String rue = request.getParameter("rue");
		String cp = request.getParameter("cp");
		String ville = request.getParameter("ville");
		String password = request.getParameter("password");
		String newPassword = request.getParameter("newPassword");
		String confirmation = request.getParameter("confirmation");
		String credit = request.getParameter("credit");
		Utilisateur user = null;
		Utilisateur u = new Utilisateur(pseudo, nom, prenom, email, phone, rue, cp, ville, credit);
		request.setAttribute("user_pseudo", u);

		// récupération du compte à modifier

		Cookie[] cookies = request.getCookies();

		for (Cookie unCookie : cookies) {
			// renvoie de l'attibut "pseudo" du cookie
			if (unCookie.getName().equals("pseudo")) {
				userAModifier = unCookie.getValue();
			}
		}
		// Avec changement MDP

		if (newPassword != "") {
			if (!newPassword.equals(confirmation)) {
				request.setAttribute("error", "password / confirmation différents");
				request.getRequestDispatcher("WEB-INF/jsp/modifierMonProfil.jsp").forward(request, response);
			} else {
				try {
					user = UtilisateurManager.getInstance().update(userAModifier, pseudo, nom, prenom, email, phone,
							rue, cp, ville, password, newPassword);

					request.setAttribute("user_pseudo", user.getPseudo());

					for (Cookie unCookie : cookies) {
						// renvoie de l'attibut "pseudo" du cookie
						if (unCookie.getName().equals("pseudo")) {
							unCookie.setValue(user.getPseudo());
							response.addCookie(unCookie);
						}
					}
					request.setAttribute("info", "Votre profil a bien été modifié");
					request.getRequestDispatcher("/Accueil").forward(request, response);
				} catch (BusinessException | SQLException e) {
					e.printStackTrace();
					request.setAttribute("error", "impossible de modifier le profil, vérifiez votre Mot de passe");
					request.getRequestDispatcher("WEB-INF/jsp/modifierMonProfil.jsp").forward(request, response);
				}
			}

			// sans changement MDP
		} else {
			try {
				user = UtilisateurManager.getInstance().update(userAModifier, pseudo, nom, prenom, email, phone, rue,
						cp, ville, password, password);

			} catch (BusinessException | SQLException e) {
				e.printStackTrace();
				request.setAttribute("error", "ce pseudo ou ce mail existe déjà");
				request.getRequestDispatcher("/WEB-INF/jsp/modifierMonProfil.jsp").forward(request, response);
			}
			if (user.getPseudo() != null) {
				for (Cookie unCookie : cookies) {
					if (unCookie.getName().equals("pseudo")) {
						unCookie.setValue(user.getPseudo());
						response.addCookie(unCookie);
					}
				}
				request.setAttribute("user", user);
				request.getRequestDispatcher("/WEB-INF/jsp/modifierMonProfil.jsp").forward(request, response);
			} else {
				request.setAttribute("error", "impossible de modifier le profil, vérifiez votre Mot de passe");
				request.getRequestDispatcher("WEB-INF/jsp/modifierMonProfil.jsp").forward(request, response);
			}

		}
	}
}

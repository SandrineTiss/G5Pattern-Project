package fr.eni.g5pattern.servlet;

import java.io.IOException;

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
 * Servlet implementation class afficherUnProfil
 */
@WebServlet("/AfficherMonProfil")
public class AfficherMonProfil extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String pseudo = null;
		Cookie[] cookies = request.getCookies();
		Boolean monProfil = false;

		for (Cookie unCookie : cookies) {
			// renvoie de l'attibut "pseudo" du cookie
			if (unCookie.getName().equals("pseudo")) {

				monProfil = true;
				pseudo = unCookie.getValue();
			}
		}
		request.setAttribute("monProfil", monProfil);

		// Renvoi des données pour affichage

		if (pseudo != null) {

			Utilisateur user = new Utilisateur();
			try {
				user = UtilisateurManager.getInstance().profil(pseudo);
				request.setAttribute("user_pseudo", user);
				request.getRequestDispatcher("/WEB-INF/jsp/afficherUnProfil.jsp").forward(request, response);
			} catch (BusinessException e) {
				e.printStackTrace();
				request.setAttribute("error", "Affichage impossible");
				request.getRequestDispatcher("/Accueil").forward(request, response);
			}

		} else {

			request.setAttribute("error", "Merci de vous connecter pour accéder à votre profil");
			request.getRequestDispatcher("/Connexion").forward(request, response);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

}

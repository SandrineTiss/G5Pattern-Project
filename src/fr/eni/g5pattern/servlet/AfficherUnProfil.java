package fr.eni.g5pattern.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.g5pattern.bll.UtilisateurManager;
import fr.eni.g5pattern.bo.BusinessException;
import fr.eni.g5pattern.bo.Utilisateur;

/**
 * Servlet implementation class afficherUnProfil
 */
@WebServlet("/AfficherUnProfil")
public class AfficherUnProfil extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String pseudo = null;
		Boolean monProfil = false;

		// Renvoi des données pour affichage
		pseudo = request.getParameter("profilVendeur");
		request.setAttribute("monProfil", monProfil);

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
			request.getRequestDispatcher("/WEB-INF/jsp/afficherUnProfil.jsp").forward(request, response);

		} else {

			request.setAttribute("error", "Merci de vous connecter pour accéder à votre profil");
			request.getRequestDispatcher("/Connexion").forward(request, response);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

}

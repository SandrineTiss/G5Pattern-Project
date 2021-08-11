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
 * Servlet implementation class ContacterGagnant
 */
@WebServlet("/ContacterGagnant")
public class ContacterGagnant extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String gagnantName = request.getParameter("gagnant");
		Boolean monProfil = false;
		request.setAttribute("monProfil", monProfil);

		Utilisateur gagnant = new Utilisateur();
		try {
			gagnant = UtilisateurManager.getInstance().profil(gagnantName);
			request.setAttribute("user_pseudo", gagnant);
			request.getRequestDispatcher("/WEB-INF/jsp/afficherUnProfil.jsp").forward(request, response);

		} catch (BusinessException e) {
			e.printStackTrace();
			request.setAttribute("error", "Affichage impossible");
			request.getRequestDispatcher("/Accueil").forward(request, response);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}

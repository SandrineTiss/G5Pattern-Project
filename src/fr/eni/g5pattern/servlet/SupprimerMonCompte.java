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

/**
 * Servlet implementation class SupprimerMonCompte
 */
@WebServlet("/SupprimerMonCompte")
public class SupprimerMonCompte extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/jsp/confirmationSuppression.jsp").forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String pseudo = null;
		String password = request.getParameter("password");

		Cookie[] cookies = request.getCookies();

		for (Cookie unCookie : cookies) {
			// renvoie de l'attibut "pseudo" du cookie
			if (unCookie.getName().equals("pseudo")) {

				pseudo = unCookie.getValue();
			}
		}
		if (pseudo != "" && password != null) {
			try {
				UtilisateurManager.getInstance().delete(pseudo, password);
				for (Cookie unCookie : cookies) {
					if (unCookie.getName().equals("pseudo")) {
						unCookie.setValue("");
						response.addCookie(unCookie);
					}
				}
				request.getRequestDispatcher("/Accueil").forward(request, response);
			} catch (BusinessException e) {
				request.setAttribute("error", "Suppression de compte impossible");
				request.getRequestDispatcher("/Accueil").forward(request, response);
			}
		}
	}
}

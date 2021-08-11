package fr.eni.g5pattern.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.g5pattern.bll.EnchereManager;
import fr.eni.g5pattern.bo.BusinessException;

/**
 * Servlet implementation class SupprimerUneVente
 */
@WebServlet("/SupprimerUneVente")
public class SupprimerUneVente extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pseudo = null ;
		Cookie[] cookies = request.getCookies();
		for (Cookie unCookie : cookies) {
			// renvoie de l'attibut "pseudo" du cookie
			if (unCookie.getName().equals("pseudo")) {
				pseudo = unCookie.getValue();
			}
		}
		
		if (pseudo != null) {
			int idArticle = Integer.parseInt(request.getParameter("idArticle"));
			try {
				EnchereManager.getInstance().supprimerEnchere(idArticle);
				request.setAttribute("info", "suppression effectuée avec succés");
				request.getRequestDispatcher("/Accueil").forward(request, response);
			} catch (BusinessException e) {
				e.printStackTrace();
				request.setAttribute("ERROR", "suppression non valide");
				request.getRequestDispatcher("/AfficherUneEnchere").forward(request, response);
			}
		}
		else {
		request.setAttribute("ERROR", "vous n'êtes pas connecté");
		request.getRequestDispatcher("/Accueil").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

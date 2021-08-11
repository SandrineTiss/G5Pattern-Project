package fr.eni.g5pattern.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Accueil
 */
@WebServlet("/Accueil")
public class Accueil extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Cookie[] cookies = request.getCookies();

		if (cookies == null) {
			Cookie cookie = new Cookie("pseudo", "");
			Cookie cookie2 = new Cookie("maintienConnexion", "");
			response.addCookie(cookie);
			response.addCookie(cookie2);

		} else {
			for (Cookie unCookie : cookies) {
				// renvoie de l'attibut "pseudo" du cookie
				if (unCookie.getName().equals("pseudo")) {
					request.setAttribute("pseudo", unCookie.getValue());
				}
			}
		}
		request.getRequestDispatcher("/RechercherEncheres2").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		// recuperation cookie de la servlet Connexion
		Cookie[] cookies = request.getCookies();

		if (cookies != null) {
			for (Cookie unCookie : cookies) {
				// renvoie de l'attibut "pseudo" du cookie
				if (unCookie.getName().equals("pseudo")) {
					request.setAttribute("pseudo", unCookie.getValue());
				}
			}
		}

		// retour page accueil
		request.getRequestDispatcher("/RechercherEncheres2").forward(request, response);
	}

}

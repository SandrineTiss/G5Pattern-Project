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
 * Servlet implementation class Connexion
 */
@WebServlet("/Connexion")
public class Connexion extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String error = request.getParameter("error");
		request.setAttribute("error", error);
		request.getRequestDispatcher("/WEB-INF/jsp/connexion.jsp").forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String login = request.getParameter("identifiant");
		String pwd = request.getParameter("password");
		String remember = request.getParameter("cookie");
		Cookie[] cookies = request.getCookies();
		String NON = "non";
		String OUI = "oui";

		Utilisateur u = new Utilisateur();
		try {
			u = UtilisateurManager.getInstance().connect(login, pwd);
			if (cookies == null) {
				Cookie cookie = new Cookie("pseudo", u.getPseudo());
				if (remember == null) {
					cookie.setMaxAge(-1);
					Cookie cookie2 = new Cookie("maintienConnexion", NON);
					response.addCookie(cookie2);
				} else {
					cookie.setMaxAge(31536000);
					Cookie cookie2 = new Cookie("maintienConnexion", OUI);
					response.addCookie(cookie2);
				}
				response.addCookie(cookie);

			} else {
				for (Cookie unCookie : cookies) {
					if (unCookie.getName().equals("pseudo")) {
						unCookie.setValue(u.getPseudo());
						if (remember == null) {
							unCookie.setMaxAge(-1);
							Cookie cookie2 = new Cookie("maintienConnexion", NON);
							response.addCookie(cookie2);
						} else {
							unCookie.setMaxAge(31536000);
							Cookie cookie2 = new Cookie("maintienConnexion", OUI);
							response.addCookie(cookie2);
						}
						response.addCookie(unCookie);
					}
				}
			}
			request.getRequestDispatcher("/Accueil").forward(request, response);

		} catch (BusinessException e) {
			request.setAttribute("error", "Ce couple login/mot de passe n'est pas associé à un utilisateur existant");
			request.getRequestDispatcher("/WEB-INF/jsp/connexion.jsp").forward(request, response);
		}

	}

}

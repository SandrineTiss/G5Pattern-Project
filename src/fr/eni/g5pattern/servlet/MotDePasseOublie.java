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
 * Servlet implementation class MotDePasseOublie
 */
@WebServlet("/MotDePasseOublie")
public class MotDePasseOublie extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Cookie[] cookies = request.getCookies();
		String pseudo = null;

		if (cookies == null) {
			Cookie cookie = new Cookie("pseudo", "");
			response.addCookie(cookie);
		} else {
			for (Cookie unCookie : cookies) {
				if (unCookie.getName().equals("pseudo")) {
					unCookie.setValue("");
					response.addCookie(unCookie);
				}
			}
		}
		request.getRequestDispatcher("/WEB-INF/jsp/MotDePasseOublie.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String pseudo = request.getParameter("pseudo");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String confirmation = request.getParameter("confirmation");
		Cookie[] cookies = request.getCookies();

		// réinitialisation du mot de passe et redirection vers l'accueil

		Utilisateur u = new Utilisateur();
		try {

			if (password.equals(confirmation)) {
				u = UtilisateurManager.getInstance().updatePassword(pseudo, email, password);

				if (cookies == null) {
					Cookie cookie = new Cookie("pseudo", pseudo);
					response.addCookie(cookie);
				} else {
					for (Cookie unCookie : cookies) {
						// renvoie de l'attibut "pseudo" du cookie
						if (unCookie.getName().equals("pseudo")) {
							request.setAttribute("pseudo", pseudo);
						}
					}
				}
				request.setAttribute("info", "Votre mot de passe a été réinitialisé, vous pouvez vous connecter !");
				request.getRequestDispatcher("/Accueil").forward(request, response);
			} else {
				request.setAttribute("error",
						"le nouveau mot de passe et sa confirmation doivent être identiques, merci de réessayer !");
				request.getRequestDispatcher("/Accueil").forward(request, response);
			}

		} catch (BusinessException | SQLException e) {
			request.setAttribute("error", "Mise à  jour du mot de passe impossible, vérifier le couple mail/pseudo");
			request.getRequestDispatcher("/Accueil").forward(request, response);
		}
	}
}

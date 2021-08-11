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
 * Servlet implementation class CreationUtilisateurd
 */
@WebServlet("/CreationUtilisateur")
public class CreationUtilisateur extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String CHAMPS_PSEUDO = "pseudo";
	public static final String CHAMPS_NOM = "nom";
	public static final String CHAMPS_PRENOM = "prenom";
	public static final String CHAMPS_EMAIL = "email";
	public static final String CHAMPS_PHONE = "phone";
	public static final String CHAMPS_RUE = "rue";
	public static final String CHAMPS_CP = "cp";
	public static final String CHAMPS_VILLE = "ville";
	public static final String CHAMPS_PASSWORD = "password";
	public static final String CHAMPS_CONFIRMATION = "confirmation";

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/creationCompte.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Cookie[] cookies = request.getCookies();

		String pseudo = request.getParameter(CHAMPS_PSEUDO);
		String nom = request.getParameter(CHAMPS_NOM);
		String prenom = request.getParameter(CHAMPS_PRENOM);
		String email = request.getParameter(CHAMPS_EMAIL);
		String phone = request.getParameter(CHAMPS_PHONE);
		String rue = request.getParameter(CHAMPS_RUE);
		String cp = request.getParameter(CHAMPS_CP);
		String ville = request.getParameter(CHAMPS_VILLE);
		String password = request.getParameter(CHAMPS_PASSWORD);
		String confirmation = request.getParameter(CHAMPS_CONFIRMATION);
		Utilisateur user;

		if (!password.equals(confirmation)) {
			Utilisateur utilisateur = new Utilisateur(pseudo, nom, prenom, email, phone, rue, cp, ville);
			request.setAttribute("user_pseudo", utilisateur);
			request.setAttribute("error", "password / confirmation différents");
			request.getRequestDispatcher("/WEB-INF/jsp/creationCompte.jsp").forward(request, response);
		} else {
			try {
				user = UtilisateurManager.getInstance().insert(pseudo, nom, prenom, email, phone, rue, cp, ville,
						password, 100, 0);

				if (cookies == null) {
					Cookie cookie = new Cookie("pseudo", user.getPseudo());
					response.addCookie(cookie);
				} else {
					for (Cookie unCookie : cookies) {
						if (unCookie.getName().equals("pseudo")) {
							unCookie.setValue(user.getPseudo());
							response.addCookie(unCookie);
						}
					}
				}
			} catch (BusinessException e) {
				e.printStackTrace();
				request.setAttribute("error", "Ce pseudo ou ce mail existe déjà");
				Utilisateur utilisateur = new Utilisateur(pseudo, nom, prenom, email, phone, rue, cp, ville);
				request.setAttribute("user_pseudo", utilisateur);
				request.getRequestDispatcher("/WEB-INF/jsp/creationCompte.jsp").forward(request, response);
			}
		}

		request.getRequestDispatcher("/Accueil").forward(request, response);
	}

}

package fr.eni.g5pattern.servlet;

import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.g5pattern.bll.EnchereManager;
import fr.eni.g5pattern.bll.UtilisateurManager;
import fr.eni.g5pattern.bo.Article;
import fr.eni.g5pattern.bo.BusinessException;
import fr.eni.g5pattern.bo.Categorie;
import fr.eni.g5pattern.bo.Retrait;
import fr.eni.g5pattern.bo.Utilisateur;

/**
 * Servlet implementation class Enchere
 */
@WebServlet("/Enchere")
public class Enchere extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String nomArticle = request.getParameter("nomArticle");
		String description = request.getParameter("description");
		String categorie = request.getParameter("categorie");
		String prixInit = request.getParameter("prixInit");

		// recuperation cookie "pseudo"
		String pseudo = null;
		Cookie[] cookies = request.getCookies();

		for (Cookie unCookie : cookies) {
			// renvoie de l'attibut "pseudo" du cookie
			if (unCookie.getName().equals("pseudo")) {

				pseudo = unCookie.getValue();
				request.setAttribute("pseudo", unCookie.getValue());
			}
		}

		// SELECT adresse user et renvoi des attributs pour preremplir le formulaire
		if (pseudo != null) {

			Utilisateur user = new Utilisateur();
			try {

				user = UtilisateurManager.getInstance().profil(pseudo);
				request.setAttribute("rueUser", user.getRue());
				request.setAttribute("cpUser", user.getCp());
				request.setAttribute("villeUser", user.getVille());
			} catch (BusinessException e) {
				e.printStackTrace();
				request.setAttribute("error", "Affichage impossible");
				request.getRequestDispatcher("/Accueil").forward(request, response);
			}

			LocalDate dateDuJour = LocalDate.now();
			LocalDate jourPlus1 = LocalDate.now().plusDays(1);
			request.setAttribute("categorie", categorie);
			request.setAttribute("nomArticle", nomArticle);
			request.setAttribute("description", description);
			request.setAttribute("prixInit", prixInit);
			request.setAttribute("jourPlus1", jourPlus1);
			request.setAttribute("dateDuJour", dateDuJour);
			// redirection page creationEnchere
			request.getRequestDispatcher("/WEB-INF/jsp/creationEnchere.jsp").forward(request, response);

		} else {
			// redirection page connexion
			request.setAttribute("error", "Merci de vous connecter pour créer une enchère");
			request.getRequestDispatcher("/Connexion").forward(request, response);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		// gestion du no_utilisateur
		Cookie[] cookies = request.getCookies();
		String pseudo = null;
		for (Cookie unCookie : cookies) {
			// renvoie de l'attibut "pseudo" du cookie
			if (unCookie.getName().equals("pseudo")) {
				pseudo = unCookie.getValue();
				request.setAttribute("pseudo", unCookie.getValue());
			}
		}

		if (pseudo != null) {
			// INSERT champs formulaire dans ARTICLES_VENDUS

			// traitement du format des dates
			String datedebut = null;
			String datefin = null;
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate dateDuJour = LocalDate.now();
			LocalDate jourPlus1 = LocalDate.now().plusDays(1);
			try {
				datedebut = request.getParameter("debutEnchere");
				datefin = request.getParameter("finEnchere");
			} catch (DateTimeException e) {
				e.printStackTrace();
			}

			// recuperation des infos formulaire

			String nomArticle = request.getParameter("nomArticle");
			String description = request.getParameter("description");
			Categorie categorie = new Categorie(request.getParameter("categorie"));
			int prixInit = Integer.parseInt(request.getParameter("prixInit"));
			LocalDate debutEnchere = LocalDate.parse(datedebut, dtf);
			LocalDate finEnchere = LocalDate.parse(datefin, dtf);
			String rueRetrait = request.getParameter("rue");
			String cpRetrait = request.getParameter("cp");
			String villeRetrait = request.getParameter("ville");
			Utilisateur user = null;
			Article article;
			Retrait retrait;

			try {
				user = UtilisateurManager.getInstance().profil(pseudo);
			} catch (BusinessException e1) {
				e1.printStackTrace();
			}

			// gestion erreurs dates
			if ((debutEnchere.isBefore(dateDuJour)) || (finEnchere.isBefore(debutEnchere))
					|| (debutEnchere.equals(finEnchere))) {
				Article a = new Article();
				a.setCategorie(categorie);
				a.setNomArticle(nomArticle);

				if (debutEnchere.isBefore(dateDuJour)) {
					request.setAttribute("nomArticle", nomArticle);
					request.setAttribute("description", description);
					request.setAttribute("categorie", a.getCategorie().getLibelle());
					request.setAttribute("prixInit", prixInit);
					request.setAttribute("error", "impossible de commencer une enchere avant la date du jour");
				}
				if (finEnchere.isBefore(debutEnchere)) {

					request.setAttribute("error", "impossible de finir une enchere avant la date du debut");
					request.setAttribute("nomArticle", nomArticle);
					request.setAttribute("description", description);
					request.setAttribute("categorie", a.getCategorie().getLibelle());
					;
					request.setAttribute("prixInit", prixInit);
				}
				if (debutEnchere.equals(finEnchere)) {

					request.setAttribute("nomArticle", nomArticle);
					request.setAttribute("description", description);
					request.setAttribute("categorie", a.getCategorie().getLibelle());
					;
					request.setAttribute("prixInit", prixInit);
					request.setAttribute("error", "l'enchere doit durer au moins un jour");
				}

				request.setAttribute("jourPlus1", jourPlus1);
				request.setAttribute("dateDuJour", dateDuJour);
				request.setAttribute("rueUser", user.getRue());
				request.setAttribute("cpUser", user.getCp());
				request.setAttribute("villeUser", user.getVille());
				request.getRequestDispatcher("/WEB-INF/jsp/creationEnchere.jsp").forward(request, response);
			} else {

				try {
					article = EnchereManager.getInstance().insertArticle(nomArticle, description, categorie, prixInit,
							debutEnchere, finEnchere, user);
					retrait = EnchereManager.getInstance().insertRetrait(article, rueRetrait, cpRetrait, villeRetrait);
					request.setAttribute("info", "Création d'une nouvelle enchère réussie");
					request.getRequestDispatcher("/Accueil").forward(request, response);
				} catch (BusinessException e) {
					e.printStackTrace();
					request.setAttribute("ERROR", "Problème création nouvelle enchère");
					request.getRequestDispatcher("/Accueil").forward(request, response);
				}
			}
		} else {
			request.setAttribute("ERROR", "vous n'êtes pas connecté");
			request.getRequestDispatcher("/Accueil").forward(request, response);
		}
	}

}

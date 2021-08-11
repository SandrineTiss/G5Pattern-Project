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
 * Servlet implementation class afficherUneEnchere
 */
@WebServlet("/AfficherUneEnchere")
public class AfficherUneEnchere extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String pseudo = null;
		Cookie[] cookies = request.getCookies();
		Integer idArticle = Integer.parseInt(request.getParameter("id"));
		for (Cookie unCookie : cookies) {
			// renvoie de l'attibut "pseudo" du cookie
			if (unCookie.getName().equals("pseudo")) {
				pseudo = unCookie.getValue();
			}
		}
		// Renvoi des données pour affichage
		if (pseudo != null) {
			Utilisateur user = null;
			Article a = new Article();
			try {
				user = UtilisateurManager.getInstance().profil(pseudo);
				a = EnchereManager.getInstance().selectArticleEnchere(idArticle);
				String vendeur = a.getVendeur().getPseudo();
				request.setAttribute("idArticle", idArticle);
				request.setAttribute("article", a);
				request.setAttribute("user", user);
				request.setAttribute("vendeur", vendeur);

				if (pseudo.equals(vendeur)) {
					request.getRequestDispatcher("/WEB-INF/jsp/modifierEnchere.jsp").forward(request, response);
				} else {
					request.getRequestDispatcher("/EncherirArticle").forward(request, response);
				}

			} catch (BusinessException e) {
				e.printStackTrace();
				request.setAttribute("error", "Affichage impossible");
				request.getRequestDispatcher("/Accueil").forward(request, response);
			}
		} else {
			request.setAttribute("error", "Merci de vous connecter pour accéder à votre profil");
			request.getRequestDispatcher("/Accueil").forward(request, response);
		}
	}

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

		// traitement du format des dates
		String datedebut = null;
		String datefin = null;
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate dateDuJour = LocalDate.now();
		LocalDate jourPlus1 = LocalDate.now().plusDays(1);
		LocalDate jourPlus2 = LocalDate.now().plusDays(2);
		try {
			datedebut = request.getParameter("debutEnchere");
			datefin = request.getParameter("finEnchere");
		} catch (DateTimeException e) {
			e.printStackTrace();
		}

		// recuperation des infos formulaire
		Integer idArticle = Integer.parseInt(request.getParameter("idArticle"));
		String vendeur = request.getParameter("vendeur");
		String nomArticle = request.getParameter("nomArticle");
		String description = request.getParameter("description");
		Categorie cat = new Categorie(request.getParameter("categorie"));
		int prixInit = Integer.parseInt(request.getParameter("prixInit"));
		LocalDate debutEnchere = LocalDate.parse(datedebut, dtf);
		LocalDate finEnchere = LocalDate.parse(datefin, dtf);
		String rueRetrait = request.getParameter("rue");
		String cpRetrait = request.getParameter("cp");
		String villeRetrait = request.getParameter("ville");
		Utilisateur user = null;
		Article article = null;
		Retrait retrait = null;

		// gestion erreurs dates
		if ((debutEnchere.isBefore(dateDuJour)) || (finEnchere.isBefore(debutEnchere))
				|| (debutEnchere.equals(finEnchere))) {
			// renvoie infos formulaire
			try {
				article = EnchereManager.getInstance().selectArticleEnchere(idArticle);
				user = UtilisateurManager.getInstance().profil(pseudo);
				request.setAttribute("idArticle", idArticle);
				request.setAttribute("article", article);
				request.setAttribute("user", user);
				request.setAttribute("vendeur", vendeur);
				request.setAttribute("jourPlus1", jourPlus1);
				request.setAttribute("jourPlus2", jourPlus2);
				request.setAttribute("debutEnchere", dateDuJour.plusDays(1));

			} catch (BusinessException e) {
				e.printStackTrace();
				request.getRequestDispatcher("/WEB-INF/jsp/modifierEnchere.jsp").forward(request, response);
			}

			if (debutEnchere.isBefore(dateDuJour)) {
				request.setAttribute("error", "impossible de commencer une enchere avant la date du jour");
				request.getRequestDispatcher("/WEB-INF/jsp/modifierEnchere.jsp").forward(request, response);
			}
			if (finEnchere.isBefore(debutEnchere)) {
				request.setAttribute("error", "impossible de finir une enchere avant la date du debut");
				request.getRequestDispatcher("/WEB-INF/jsp/modifierEnchere.jsp").forward(request, response);
			}
			if (debutEnchere.equals(finEnchere)) {
				request.setAttribute("error", "l'enchere doit durer au moins un jour");
				request.getRequestDispatcher("/WEB-INF/jsp/modifierEnchere.jsp").forward(request, response);
			}
		} else {
			try {
				// utilisateur
				user = UtilisateurManager.getInstance().profil(pseudo);
				// methode UPDATE
				article = EnchereManager.getInstance().modifierArticle(nomArticle, description, cat, prixInit,
						debutEnchere, finEnchere, idArticle, user);
				retrait = EnchereManager.getInstance().modifierRetrait(article, rueRetrait, cpRetrait, villeRetrait);
				article = EnchereManager.getInstance().selectArticleEnchere(idArticle);
				request.setAttribute("idArticle", idArticle);
				request.setAttribute("article", article);
				request.setAttribute("user", user);
				request.setAttribute("vendeur", vendeur);
				request.setAttribute("info", "modification réussie");
				request.getRequestDispatcher("/Accueil").forward(request, response);

			} catch (BusinessException e) {
				e.printStackTrace();
				request.setAttribute("error", "erreur modification");
				request.getRequestDispatcher("/WEB-INF/jsp/modifierEnchere.jsp").forward(request, response);
			}
		}

	}

}

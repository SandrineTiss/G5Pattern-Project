package fr.eni.g5pattern.servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.g5pattern.bo.Article;
import fr.eni.g5pattern.bo.BusinessException;
import fr.eni.g5pattern.dal.DAOFactory;

/**
 * Servlet implementation class RechercherEncheres
 */
@WebServlet("/RechercherEncheres2")
public class RechercherEncheres2 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String choix = request.getParameter("choix");

		Cookie[] cookies = request.getCookies();
		String pseudo = null;

		request.setAttribute("vente", "");
		request.setAttribute("achat", "achats");

		if (cookies == null) {
			Cookie cookie = new Cookie("pseudo", "");
			Cookie cookie2 = new Cookie("maintienConnexion", "");
			response.addCookie(cookie);
			response.addCookie(cookie2);
			request.setAttribute("pseudo", "");

		} else {
			for (Cookie unCookie : cookies) {
				// renvoie de l'attibut "pseudo" du cookie
				if (unCookie.getName().equals("pseudo")) {
					request.setAttribute("pseudo", unCookie.getValue());
				}
			}
		}

		List<Article> articlesSearch = new ArrayList<>();

		try {
			articlesSearch = DAOFactory.getArticleDAO().rechercheFiltres("%", "");
			List<Article> encheresOuvertes = new ArrayList<>();

			for (Article article : articlesSearch) {
				if (article.getFinEnchere().isAfter(LocalDate.now())
						|| article.getFinEnchere().equals(LocalDate.now())) {
					if (article.getDebutEnchere().isBefore(LocalDate.now())
							|| article.getDebutEnchere().equals(LocalDate.now())) {
						encheresOuvertes.add(article);
					}
				}

			}

			if (!encheresOuvertes.isEmpty()) {
				request.setAttribute("affichageDemande", encheresOuvertes);
				request.getRequestDispatcher("/WEB-INF/jsp/accueil.jsp").forward(request, response);
			} else {
				request.setAttribute("rech", "Pas d'enchere actuellement.");
				request.getRequestDispatcher("/WEB-INF/jsp/accueil.jsp").forward(request, response);
			}
		} catch (BusinessException e) {
			request.setAttribute("error", "Affichage impossible");
			request.getRequestDispatcher("/WEB-INF/jsp/accueil.jsp").forward(request, response);
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// récupération du choix entre achats et ventes dans la variable nommée choix !
		String choix = request.getParameter("choix");

		if (choix != null) {
			if (choix.equals("ventes")) {
				request.setAttribute("vente", "ventes");
			} else {
				request.setAttribute("achat", "achats");
			}
		} else {
			request.setAttribute("achat", "achats");
		}

		String filtres = request.getParameter("filtres");
		request.setAttribute("filtres", filtres);
		if (filtres == null || filtres.equals("")) {
			filtres = "%";
		}

		String libelle = request.getParameter("categorie");

		if (libelle == null) {
			libelle = "toutes";
		}
		request.setAttribute("categorie", libelle);

		/// récupération des booléens vérifiées avec page accueil

		boolean checkboxVentesEnCours = request.getParameter("venteEnCours") != null;
		request.setAttribute("venteEnCours", request.getParameter("venteEnCours"));
		boolean checkboxVenteTermine = request.getParameter("venteTermine") != null;
		request.setAttribute("venteTermine", request.getParameter("venteTermine"));
		boolean checkboxVenteAVenir = request.getParameter("venteNonDebute") != null;
		boolean checkboxAchatEnCours = request.getParameter("enchereEnCours") != null;
		request.setAttribute("venteNonDebute", request.getParameter("venteNonDebute"));
		request.setAttribute("enchereEnCours", request.getParameter("enchereEnCours"));
		boolean checkboxAchatGagne = request.getParameter("enchereGagne") != null;
		request.setAttribute("enchereGagne", request.getParameter("enchereGagne"));
		boolean checkboxAchatOuvert = request.getParameter("enchereOuvert") != null;
		request.setAttribute("enchereOuvert", request.getParameter("enchereOuvert"));

		Cookie[] cookies = request.getCookies();
		String pseudo = null;

		if (libelle.equals("toutes")) {
			libelle = "%%";
		}

		for (Cookie unCookie : cookies) {

			if (unCookie.getName().equals("pseudo")) {
				pseudo = unCookie.getValue();
				request.setAttribute("pseudo", pseudo);
			}
		}

		if (!checkboxAchatEnCours && !checkboxAchatGagne && !checkboxVenteAVenir && !checkboxVenteTermine
				&& !checkboxVentesEnCours) {
			checkboxAchatOuvert = true;
		}

		List<Article> rechercheEncheres = new ArrayList<>();

		// ENCHERE OUVERTES
		if (checkboxAchatOuvert) {
			List<Article> articlesSearch = new ArrayList<>();
			try {
				articlesSearch = DAOFactory.getArticleDAO().rechercheFiltres(libelle, filtres);
				if (!articlesSearch.isEmpty()) {
					for (Article article : articlesSearch) {
						rechercheEncheres.add(article);
					}
				} else {
					request.setAttribute("rech", "Pas d'enchere ouverte pour le moment, revenez plus tard :).");
					request.getRequestDispatcher("/WEB-INF/jsp/accueil.jsp");
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		// MES ENCHERES EN COURS
		if (checkboxAchatEnCours && !checkboxAchatOuvert) {
			List<Article> encherePlacees = new ArrayList<>();
			try {
				encherePlacees = DAOFactory.getArticleDAO().getArticleEncheri(pseudo);

				if (!encherePlacees.isEmpty()) {
					for (Article article : encherePlacees) {
						rechercheEncheres.add(article);
					}
				} else {
					request.setAttribute("rech", "Vous n'avez pas placé d'enchere pour le moment.");
					request.getRequestDispatcher("/WEB-INF/jsp/accueil.jsp");
				}
			} catch (BusinessException e) {
				request.setAttribute("error", "Problème de données");
				request.getRequestDispatcher("/WEB-INF/jsp/accueil.jsp");
			}
		}

		// MES ENCHERES REMPORTEES
		if (checkboxAchatGagne) {
			List<Article> enchereRemportees = new ArrayList<>();
			try {
				enchereRemportees = DAOFactory.getArticleDAO().getEncheresRemportees(pseudo, libelle, filtres);

				if (!enchereRemportees.isEmpty()) {
					for (Article article : enchereRemportees) {
						rechercheEncheres.add(article);
					}
				} else {
					request.setAttribute("rech", "Vous n'avez pas remporté d'encheres pour le moment.");
					request.getRequestDispatcher("/WEB-INF/jsp/accueil.jsp");
				}
			} catch (BusinessException e) {
				request.setAttribute("error", "Problème de données");
				request.getRequestDispatcher("/WEB-INF/jsp/accueil.jsp");
			}
		}

		// MES VENTES EN COURS
		if (checkboxVentesEnCours) {
			List<Article> articlesSearchVentesEnCours = new ArrayList<>();
			try {
				articlesSearchVentesEnCours = DAOFactory.getArticleDAO().ventesEnCours(pseudo, libelle, filtres);

				if (!articlesSearchVentesEnCours.isEmpty()) {
					for (Article article : articlesSearchVentesEnCours) {
						rechercheEncheres.add(article);
					}
				} else {
					request.setAttribute("rech", "Vous n'avez pas de vente en cours.");
					request.getRequestDispatcher("/WEB-INF/jsp/accueil.jsp");
				}
			} catch (BusinessException e) {
				request.setAttribute("error", "Problème de données");
				request.getRequestDispatcher("/WEB-INF/jsp/accueil.jsp");
			}
		}

		// MES VENTES NON DEBUTEES
		if (checkboxVenteAVenir) {
			List<Article> articlesSearchVentesAVenir = new ArrayList<>();
			try {
				articlesSearchVentesAVenir = DAOFactory.getArticleDAO().ventesAVenir(pseudo, libelle, filtres);

				if (!articlesSearchVentesAVenir.isEmpty()) {
					for (Article article : articlesSearchVentesAVenir) {
						rechercheEncheres.add(article);
					}
				} else {
					request.setAttribute("rech", "Vous n'avez pas de vente non débutée.");
					request.getRequestDispatcher("/WEB-INF/jsp/accueil.jsp");
				}
			} catch (BusinessException e) {
				request.setAttribute("error", "Problème de données");
				request.getRequestDispatcher("/WEB-INF/jsp/accueil.jsp");
			}
		}

		// MES VENTES TERMINEES
		if (checkboxVenteTermine) {

			List<Article> articlesSearchVentesTerminees = new ArrayList<>();
			try {
				articlesSearchVentesTerminees = DAOFactory.getArticleDAO().encheresTerminees(pseudo, libelle, filtres);
				if (!articlesSearchVentesTerminees.isEmpty()) {
					for (Article article : articlesSearchVentesTerminees) {
						rechercheEncheres.add(article);
					}
				} else {
					request.setAttribute("rech", "Vous n'avez pas de vente terminée.");
				}
			} catch (BusinessException e) {
				request.setAttribute("error", "Problème de données");
				request.getRequestDispatcher("/WEB-INF/jsp/accueil.jsp");
			}
		}
		request.setAttribute("affichageDemande", rechercheEncheres);
		request.getRequestDispatcher("/WEB-INF/jsp/accueil.jsp").forward(request, response);
	}
}
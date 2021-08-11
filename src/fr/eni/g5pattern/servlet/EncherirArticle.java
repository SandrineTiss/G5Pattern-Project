package fr.eni.g5pattern.servlet;

import java.io.IOException;
import java.time.LocalDate;

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
import fr.eni.g5pattern.bo.Utilisateur;

/**
 * Servlet implementation class EncherirArticle
 */
@WebServlet("/EncherirArticle")
public class EncherirArticle extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String pseudo = null;
		Cookie[] cookies = request.getCookies();
		LocalDate dateCourante = LocalDate.now();
		Integer idArticle = Integer.parseInt(request.getParameter("id"));
		for (Cookie unCookie : cookies) {
			// renvoie de l'attibut "pseudo" du cookie
			if (unCookie.getName().equals("pseudo")) {
				pseudo = unCookie.getValue();
			}
		}
		// Renvoi des données pour affichage
		if (!pseudo.equals("")) {
			Utilisateur user = null;
			Article a = new Article();
			try {
				// enchérisseur
				user = UtilisateurManager.getInstance().profil(pseudo);
				// article en vente

				a = EnchereManager.getInstance().selectArticleEnchere(idArticle);
				// ajout condition prix si dernière enchere ou pas

				if (a.getDerniereEnchere().getMontantEnchere() == 0) {
					a.getDerniereEnchere().setMontantEnchere(a.getPrixInit());
				}

				// ajout Jessy condition (vendeur = pseudo)-----------------------------------
				String vendeur = a.getVendeur().getPseudo();
				if (pseudo.equals(vendeur)) {
					request.setAttribute("dateDuJour", dateCourante);
					request.setAttribute("article", a);
					request.setAttribute("user", user);
					request.setAttribute("vendeur", vendeur);
					if (dateCourante.isAfter(a.getFinEnchere())) {
						request.getRequestDispatcher("/WEB-INF/jsp/venteTermineeVendeur.jsp").forward(request,
								response);
					} else {
						request.getRequestDispatcher("/AfficherUneEnchere").forward(request, response);
					}
				} else {
					request.setAttribute("vendeur", vendeur);
					request.setAttribute("article", a);
					request.setAttribute("user", user);

					// --------------------------------------
					if (dateCourante.isAfter(a.getFinEnchere())) {
						if (pseudo.equals(a.getDerniereEnchere().getUtilisateur().getPseudo())) {
							request.getRequestDispatcher("/WEB-INF/jsp/VenteRemportee.jsp").forward(request, response);

						} else {
							request.setAttribute("info", "Cette enchère est terminée");
							request.setAttribute("terminee", true);
							request.getRequestDispatcher("/Accueil").forward(request, response);

						}
					} else if (dateCourante.isBefore(a.getDebutEnchere())) {
						request.setAttribute("info", "Cette enchère n'est pas commencée ! Revenez bientôt");
						request.getRequestDispatcher("/Accueil").forward(request, response);
					} else {
						request.getRequestDispatcher("/WEB-INF/jsp/encherirArticle.jsp").forward(request, response);
					}
				}

			} catch (BusinessException e) {
				e.printStackTrace();
				request.setAttribute("error", "Affichage impossible");
				request.getRequestDispatcher("/Accueil").forward(request, response);
			}
		} else {
			request.setAttribute("error", "Merci de vous connecter pour accéder à cette page");
			request.getRequestDispatcher("/Accueil").forward(request, response);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Integer idArticle = Integer.parseInt(request.getParameter("idArticle"));
		Integer proposition = Integer.parseInt(request.getParameter("proposition"));
		Integer derniereEnchere = Integer.parseInt(request.getParameter("derniereEnchere"));
		String encherisseurPrecedent = request.getParameter("encherisseurPrecedent");
		Integer creditDisponible = Integer.parseInt(request.getParameter("creditEncherisseur"));
		Integer encherisseur = Integer.parseInt(request.getParameter("noEncherisseur"));

		try {
			if (creditDisponible >= proposition && derniereEnchere < proposition) {
				EnchereManager.getInstance().encherir(idArticle, proposition, derniereEnchere, encherisseur,
						encherisseurPrecedent);
				request.setAttribute("info", "Votre enchère a bien été enregistrée");
				request.getRequestDispatcher("/Accueil").forward(request, response);

			} else {
				request.setAttribute("error", "Vérifiez votre crédit disponible");
				request.getRequestDispatcher("/WEB-INF/jsp/encherirArticle.jsp").forward(request, response);
			}

		} catch (BusinessException e) {
			e.printStackTrace();
			request.setAttribute("error", "Impossible d'enchérir");
			request.getRequestDispatcher("/WEB-INF/jsp/encherirArticle.jsp").forward(request, response);
		}

	}

}

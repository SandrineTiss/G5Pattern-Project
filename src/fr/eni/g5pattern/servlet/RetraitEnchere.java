package fr.eni.g5pattern.servlet;

import java.io.IOException;
import java.sql.SQLException;

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
 * Servlet implementation class RetraitEnchere
 */
@WebServlet("/RetraitEnchere")
public class RetraitEnchere extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Cookie[] cookies = request.getCookies();
		String pseudo = null;
		if (cookies == null) {
			Cookie cookie = new Cookie("pseudo", "");
			Cookie cookie2 = new Cookie("maintienConnexion", "");
			response.addCookie(cookie);
			response.addCookie(cookie2);
		} else {
			for (Cookie unCookie : cookies) {
				// renvoie de l'attibut "pseudo" du cookie
				if (unCookie.getName().equals("pseudo")) {
					pseudo = unCookie.getValue();
				}
			}
		}

		int idArticle = Integer.parseInt(request.getParameter("idArticle"));

		if (pseudo != null) {
			Utilisateur user = null;
			Article a = new Article();
			try {
				user = UtilisateurManager.getInstance().profil(pseudo);
				a = EnchereManager.getInstance().selectArticleEnchere(idArticle);
				String vendeur = a.getVendeur().getPseudo();

				if (pseudo.equals(vendeur)) {
					// recuperation infos
					int prixVente = a.getDerniereEnchere().getMontantEnchere();
					int creditActuel = user.getCredit();
					int noUser = user.getNo_users();
					int nouveauCredit = creditActuel + prixVente;
					String acquereur = a.getDerniereEnchere().getUtilisateur().getPseudo();

					// methode update nouvauCredit dans utilisateurs
					UtilisateurManager.getInstance().recreditVendeur(nouveauCredit, noUser);
					// methode update montant dans la colonne prix_vente ARTICLES_VENDUS
					EnchereManager.getInstance().ajoutPrixVente(prixVente, idArticle);

					// redirection vers accueil
					request.setAttribute("info", "Retrait valide");
					request.getRequestDispatcher("/Accueil").forward(request, response);
				} else {
					request.setAttribute("error", "Vous n'etes pas le vendeur");
					request.getRequestDispatcher("/Accueil").forward(request, response);
				}
			} catch (BusinessException | SQLException e) {
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
		Integer idArticle = Integer.parseInt(request.getParameter("idArticle"));
		Integer idGagnant = Integer.parseInt(request.getParameter("noEncherisseur"));
	}

}

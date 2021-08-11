package fr.eni.g5pattern.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class SeDeconnecter
 */
@WebServlet("/SeDeconnecter")
public class SeDeconnecter extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Cookie[] cookies = request.getCookies();
		for (Cookie unCookie : cookies) {
			if (unCookie.getName().equals("pseudo")) {
				unCookie.setValue("");
				response.addCookie(unCookie);
			}
		}
		HttpSession session = request.getSession();
		session.invalidate();
		request.getRequestDispatcher("/RechercherEncheres2").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}

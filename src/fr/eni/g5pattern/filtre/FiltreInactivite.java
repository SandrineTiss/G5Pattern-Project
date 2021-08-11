package fr.eni.g5pattern.filtre;

import java.io.IOException;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class FlitreInactivite
 */
@WebFilter(dispatcherTypes = { DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.INCLUDE,
		DispatcherType.ERROR }, urlPatterns = { "*" })
public class FiltreInactivite implements Filter {

	/**
	 * Default constructor.
	 */
	public FiltreInactivite() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpSession session = ((HttpServletRequest) request).getSession(false);
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		Cookie[] cookies = req.getCookies();

		// si la session est nulle :

		if (session == null) {
			if (cookies != null) {
				for (Cookie cookie : cookies) {
					if (cookie.getName().equals("maintienConnexion")) {
						String maintienConnexion = cookie.getValue();
						if (maintienConnexion.equals("non")) {
							for (Cookie unCookie : cookies) {
								if (unCookie.getName().equals("pseudo")) {
									unCookie.setValue("");
									resp.addCookie(unCookie);
								}
							}
						}
					}
				}
			}
		}
		chain.doFilter(req, resp);

	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
	}

}

package com.demo.fliter;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.demo.bean.UserBean;
import com.demo.dao.UserDao;
import com.demo.dao.impl.UserDaoImpl;
import com.demo.util.CookieUtil;

/**
 * Servlet Filter implementation class LoginFliter
 */
public class LoginFliter implements Filter {

	/**
	 * Default constructor.
	 */
	public LoginFliter() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		try {
			// in web.xml set all pages should be fliter
			// change to Child class
			HttpServletRequest req = (HttpServletRequest) request;
			// 1.judge if need to login again. if the user just logined, we
			// chain.doFliter immediately
			UserBean userBean = (UserBean) req.getSession().getAttribute("userBean");
			if (userBean != null) {
				chain.doFilter(request, response);
			} else {
				// 1.get all cookies
				Cookie[] cookies = req.getCookies();
				// 2.get the auto_login cookie from alot of cookies[]
				Cookie findCookie = CookieUtil.findCookie(cookies, "auto_login");
				// 3.if not null, the user login before,
				// I don't know why here is a bug, if I check it's null for
				// findCookie, it also will go to else in the dispatcher...so I
				// need another judgement by "#"
				if (findCookie == null || (!findCookie.getValue().contains("#"))) {
					chain.doFilter(request, response);
				} else {
					// expired for the session, not exist the UserBean, need
					// auto login
					// get the values from cookie
					String value = findCookie.getValue();
					String username = value.split("#")[0];
					String password = value.split("#")[1];

					// wrap into the UserBean
					UserBean newUB = new UserBean();
					newUB.setUsername(username);
					newUB.setPassword(password);

					// relogin through the UserDao
					UserDao uDao = new UserDaoImpl();
					userBean = uDao.login(newUB);

					// set to session for save for the index to do judgement
					req.getSession().setAttribute("userBean", userBean);
					chain.doFilter(request, response);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			chain.doFilter(request, response);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}

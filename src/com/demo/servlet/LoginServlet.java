package com.demo.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.demo.bean.UserBean;
import com.demo.dao.UserDao;
import com.demo.dao.impl.UserDaoImpl;

public class LoginServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String autologin = request.getParameter("auto_login");
			// wrap the parameter from request to Userbean
			UserBean user = new UserBean();
			user.setUsername(username);
			// user.setId(id);
			user.setPassword(password);

			// verify the data by UserDaoImpl
			UserDao udao = new UserDaoImpl();
			UserBean isSuccess = udao.login(user);
			if (isSuccess != null) {
				// check if the autoLogin click
				if ("on".equals(autologin)) {
					// send cookie to web browser
					Cookie cookie = new Cookie("auto_login", username + "#" + password);
					System.out.println("the first time set: " + cookie.getValue());
					cookie.setMaxAge(60 * 60 * 24 * 7);// 7days available
					cookie.setPath(request.getContextPath());// local path
					response.addCookie(cookie);

				}

				// login success goto index
				request.getSession().setAttribute("userBean", isSuccess);
				response.sendRedirect("index.jsp");
			} else {
				// login fail
				request.getRequestDispatcher("login.jsp").forward(request, response);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}

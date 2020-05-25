package com.demo.util;

import javax.servlet.http.Cookie;
/**
 * from cookies find the cookie we need.
 * @author Howins
 *
 */
public class CookieUtil {
	public static Cookie findCookie(Cookie[] cookies, String cookieName) {
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				cookieName.equals(cookie.getName());
				return cookie;
			}
		}
		return null;
	}
}

package com.pollapp.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

/**
 *	Once per request Filter implementation
 *  that adds a cookie to conform with 
 *  AngularJs's different csrf header name 
 *
 */
public class AngularSpringSecurityCsrfAdapterFilter extends OncePerRequestFilter {


	/**
	 * This will add a new cookie with name XSRF-TOKEN
	 * and spring security's csrf token as value
	 * if one does not already exist or is not up to date
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class.getName());

		if(csrf != null) {
			Cookie cookie = WebUtils.getCookie(request, "XSRF-TOKEN");
			String token = csrf.getToken();
			if( cookie == null || token != null && !(token.equals(cookie.getValue()))){
				cookie = new Cookie("XSRF-TOKEN", token);
				cookie.setPath("/");
				response.addCookie(cookie);
			}
		}
		filterChain.doFilter(request, response);
	}

}

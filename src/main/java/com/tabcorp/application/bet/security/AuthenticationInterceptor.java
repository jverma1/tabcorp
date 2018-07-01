package com.tabcorp.application.bet.security;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class AuthenticationInterceptor extends HandlerInterceptorAdapter {
	private Logger logger = Logger.getLogger(AuthenticationInterceptor.class.getName());
	private static String AUTH_HEADER_NAME = "Authorization";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String authToken = request.getHeader(AUTH_HEADER_NAME);
		if (authToken == null) {
			logger.log(Level.INFO, "Missing authorization token");
			response.setStatus(Response.Status.FORBIDDEN.getStatusCode());
			return false;
		}

		if (!isValidToken(authToken)) {
			logger.log(Level.INFO, "Invalid authorization token");
			response.setStatus(Response.Status.FORBIDDEN.getStatusCode());
			return false;
		}

		logger.log(Level.INFO, "----Authorization Successful----");
		return true;
	}

	private boolean isValidToken(String authToken) {
		if (authToken.equals("abc123")) {
			return true;
		}
		return false;
	}

}

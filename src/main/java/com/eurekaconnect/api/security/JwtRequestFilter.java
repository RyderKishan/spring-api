package com.eurekaconnect.api.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	private JwtTokenUtil jwtTokenUtil;

	public JwtRequestFilter(JwtTokenUtil jwtTokenUtil) {
		this.jwtTokenUtil = jwtTokenUtil;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		try {
			String token = jwtTokenUtil.resolveToken(request);
			if (token != null && jwtTokenUtil.validateToken(token)) {
				Authentication auth = jwtTokenUtil.getAuthentication(token);
				if (auth != null) {
					SecurityContextHolder.getContext().setAuthentication(auth);
				}
			}
		} catch (Exception e) {
			System.out.println("Unable to get JWT Token :: " + e.getMessage());
		}
		chain.doFilter(request, response);
	}

}

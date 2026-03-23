package com.vgd.springboot.start.springboothello.configuration;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import lombok.RequiredArgsConstructor;

import static java.util.Objects.nonNull;

import com.vgd.springboot.start.springboothello.services.JwtService;
import com.vgd.springboot.start.springboothello.services.StartUserDetailsService;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
	private final JwtService jwtService;
	private final StartUserDetailsService startUserDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String authHeader = request.getHeader("Authorization");
		if (nonNull(authHeader) && authHeader.startsWith("Bearer ")) {
			String token = authHeader.substring(7);
			try {
				String userName = jwtService.validateTokenAndGetUser(token);
				UserDetails userDetails = startUserDetailsService.loadUserByUsername(userName);
				
				UsernamePasswordAuthenticationToken authToken = 
	                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(authToken);
			} catch(Exception e) {}
		}
		
		filterChain.doFilter(request, response);
	}
}

package com.vgd.springboot.start.springboothello.services.utils;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import static java.util.Objects.isNull;

import com.vgd.springboot.start.springboothello.entity.UserPrincipal;

public class SecurityUtils {
	private SecurityUtils() {}
	
	public static UserPrincipal getAuthorizedUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (isNull(auth) || !auth.isAuthenticated()) {
			throw new AccessDeniedException("User is not authorized");
		}
		
		return (UserPrincipal) auth.getPrincipal();
	}
}

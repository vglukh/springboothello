package com.vgd.springboot.start.springboothello.services;

import java.nio.charset.StandardCharsets;
import java.util.*;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

import static java.util.stream.Collectors.toList;

import com.vgd.springboot.start.springboothello.entity.UserPrincipal;

@Service
@RequiredArgsConstructor
public class JwtService {
    private String TOKEN_KEY;
	
	@Value("${springboothello.security.token}")
	public void setTokenKey(String tokenValue) {
		TOKEN_KEY = tokenValue;
	}
	
	@SuppressWarnings("unchecked")
	public UserPrincipal extractUserDetails(String token) {
		try {
			SecretKey key = Keys.hmacShaKeyFor(TOKEN_KEY.getBytes(StandardCharsets.UTF_8));
			
			Claims claims = Jwts.parser()
					.verifyWith(key)
					.build()
					.parseSignedClaims(token)
					.getPayload();
			
			
			String idStr = claims.get("id", String.class);
			List<String> rolesNames = (List<String>) claims.get("roles");
			
			List<SimpleGrantedAuthority> roles = rolesNames.stream()
					.map(r -> new SimpleGrantedAuthority(r))
					.collect(toList());
			
			return new UserPrincipal(UUID.fromString(idStr), claims.getSubject(), "", roles);
		
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new RuntimeException();
		}
	}
}

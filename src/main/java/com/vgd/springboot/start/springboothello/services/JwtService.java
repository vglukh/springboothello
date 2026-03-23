package com.vgd.springboot.start.springboothello.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

@Service
public class JwtService {
	private static final int TWO_HOURS = 7200000;
	
    public String TOKEN_KEY;
	
	@Value("${springboothello.security.token}")
	public void setTokenKey(String tokenValue) {
		TOKEN_KEY = tokenValue;
	}
	
	public String generateToken(String userName) {
		return JWT.create()
				.withSubject(userName)
				.withExpiresAt(new Date(System.currentTimeMillis() + TWO_HOURS))
				.sign(createAlgorithm());
	}
	
	public String validateTokenAndGetUser(String token) {
		return JWT.require(createAlgorithm())
				.build()
				.verify(token)
				.getSubject();
	}
	
	private Algorithm createAlgorithm() {
		return Algorithm.HMAC512(TOKEN_KEY);
	}
}

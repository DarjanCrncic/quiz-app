package com.darjan.quizapp.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

@Component
public class JWTUtils {
	private static final Logger logger = LoggerFactory.getLogger(JWTUtils.class);

	@Value("${jwt.secret}")
	private String jwtSecret;

	@Value("${jwt.expiration.ms}")
	private int jwtExpirationMs;
	
	public String generateJwtToken(Authentication authentication) {
		SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
		CustomUserDetails userPrincipal = (CustomUserDetails) authentication.getPrincipal();

		logger.info("jwt principal: " + userPrincipal.getUser().toString());
		return Jwts.builder()
				.setIssuer("quiz-app")
				.setSubject("JWT Token")
				.claim("username", userPrincipal.getUsername())
				.claim("userId", userPrincipal.getUser().getId())
				.setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
				.signWith(key)
				.compact();
	}

	public String getUserNameFromJwtToken(String token) {
		SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
		return String.valueOf( Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().get("username"));
	}

	public boolean validateJwtToken(String authToken) {
		SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
		try {
			Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(authToken);
			return true;
		} catch (MalformedJwtException e) {
			logger.error("Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			logger.error("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			logger.error("JWT token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("JWT claims string is empty: {}", e.getMessage());
		} catch (SignatureException e) {
			logger.error("JWT has been tampered with, signature does not match: {}", e.getMessage());
		}

		return false;
	}
}
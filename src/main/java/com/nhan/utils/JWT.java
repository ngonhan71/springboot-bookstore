package com.nhan.utils;

import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.nhan.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWT {
	private static final String SECRET_KEY = "bookstore_A3427&*^*&#@";

	public String getEmail(String token) {
		final Claims claims = extractAllClaims(token);
		return claims.get("email").toString();
	}

	private Date getExpiration(String token) {
		final Claims claims = extractAllClaims(token); 
		return claims.getExpiration();
	}

	public String generateToken(Map<String, Object> extraClaims, User user) {
		return Jwts.builder().setClaims(extraClaims).setSubject(user.getEmail())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
				.signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
	}
	
	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
			return true;
		} catch (ExpiredJwtException e) {
			return false;
		}
	}

	public boolean isTokenValid(String token, User user) {
		String email = getEmail(token);
		return (email.equals(user.getEmail())) && !isTokenExpired(token);
	}

	private boolean isTokenExpired(String token) {
		return getExpiration(token).before(new Date());
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
	}
}

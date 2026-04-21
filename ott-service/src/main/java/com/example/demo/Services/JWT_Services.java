package com.example.demo.Services;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JWT_Services {
	
	private static final SecretKey SECRECT_KEY =
	        Keys.hmacShaKeyFor(
	        	"this_is_my_super_secrect_key_this_is_my_super_secrect_key".getBytes()
	        );

	public boolean validate(Claims claims) throws Exception {
	       Date expiration = claims.getExpiration();
	       return expiration.after(new Date());
	}


	public Claims extractToken(String token) {
	    return Jwts.parser()
	           .verifyWith(SECRECT_KEY) 
	           .build()
	           .parseClaimsJws(token)
	           .getBody();
	}

}
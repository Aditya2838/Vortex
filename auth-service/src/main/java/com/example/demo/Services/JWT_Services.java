package com.example.demo.Services;


import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.example.demo.Entities.My_User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JWT_Services {

    @Autowired
    UserDetailsService userDetailsService;

    private static final SecretKey SECRECT_KEY =
        Keys.hmacShaKeyFor(
        	"this_is_my_super_secrect_key_this_is_my_super_secrect_key".getBytes()
        );

    private static final long EXPIRATION = 1000 * 60 * 15;

    public String generateToken(My_User user) {
    	UserDetails my_user = userDetailsService.loadUserByUsername(user.getUsername());
    	List<String> roles = my_user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        return Jwts.builder()
            .subject(user.getUsername())
            .claim("roles" , roles)
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + EXPIRATION))
            .signWith(SECRECT_KEY)
            .compact();
    }
    
    private boolean checkExpiry(String token) {
        return extractClaim(token)
                .getExpiration()
                .before(new Date()); 
    }

	public Claims extractClaim(String token) {
        return Jwts.parser()
            .verifyWith(SECRECT_KEY)
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

    public boolean validToken(String token) {
        String usernameFromToken = extractClaim(token).getSubject();
        String usernameFromDb =
            userDetailsService.loadUserByUsername(usernameFromToken)
                .getUsername();

        return usernameFromToken.equals(usernameFromDb)
                && !checkExpiry(token);
    }
    
}
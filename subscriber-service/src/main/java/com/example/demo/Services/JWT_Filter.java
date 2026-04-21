package com.example.demo.Services;

import java.io.IOException;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWT_Filter extends OncePerRequestFilter {
    
    @Autowired
    private JWT_Services jwt_Services;
    String username ;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7).trim(); // Added trim() to avoid space issues

            try {
                Claims claims = jwt_Services.extractToken(token);
                this.username = claims.getSubject();

                if (jwt_Services.isTokenValid(claims)) {
                	List<String> roles = claims.get("roles", List.class);
                	List<SimpleGrantedAuthority> authorities = roles.stream()
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());
                    UsernamePasswordAuthenticationToken auth =
                            new UsernamePasswordAuthenticationToken(username, null, authorities);

                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            } catch (ExpiredJwtException e) {
                handleException(response, "Token expired, please login again.");
                return;
            } catch (Exception e) {
                e.printStackTrace();
                handleException(response, "Token error: " + e.getMessage());
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private void handleException(HttpServletResponse response, String msg) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(msg);
    }
}
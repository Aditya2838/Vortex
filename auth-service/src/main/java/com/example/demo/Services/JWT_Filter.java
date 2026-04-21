package com.example.demo.Services;

import java.io.IOException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class JWT_Filter extends OncePerRequestFilter {

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    JWT_Services jwt_Services;
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")
                && SecurityContextHolder.getContext().getAuthentication() == null) {

            String token = header.substring(7);
            try {
                String username = jwt_Services.extractClaim(token).getSubject();
                UserDetails user = userDetailsService.loadUserByUsername(username);

                if (jwt_Services.validToken(token)) {
                    UsernamePasswordAuthenticationToken auth =
                            new UsernamePasswordAuthenticationToken(
                                    user,
                                    null,
                                    user.getAuthorities()
                            );
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }

            } catch (ExpiredJwtException e) {
	            System.out.println("Token expired, please login again.");
	            response.getWriter().write("Token expired, please login again.");
	            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	            return;
	        }catch (Exception e) {
	            System.out.println("Token error, please try again.");
	            response.getWriter().write("Token error, please try again.");
	            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	            return;
	        }	
        }
        filterChain.doFilter(request, response);
    }
}
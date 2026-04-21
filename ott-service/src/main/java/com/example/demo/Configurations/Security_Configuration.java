package com.example.demo.Configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.demo.Services.JWT_Filter;

@Configuration
public class Security_Configuration {
	
	@Autowired
	JWT_Filter filter;
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    http
	    .csrf(csrf -> csrf.disable())
	    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
	    .authorizeHttpRequests(auth -> auth
	            .requestMatchers("/public").permitAll() 
	            .requestMatchers("/Admin/**" ).hasAuthority("Admin")
	            .anyRequest().authenticated())
	    .formLogin(form -> form.disable())
	    .httpBasic(form -> form.disable())
	    .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
	    
	    return http.build();
	}
}
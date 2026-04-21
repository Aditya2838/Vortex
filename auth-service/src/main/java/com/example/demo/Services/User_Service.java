package com.example.demo.Services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import com.example.demo.Entities.My_User;
import com.example.demo.Repository.User_Repository;

@Service
public class User_Service {

    @Autowired
    private User_Repository repo;

    @Autowired
    private JWT_Services jwt_Services;

    @Autowired
    private AuthenticationManager manager;

    public boolean save(My_User user) {
    	Optional<My_User> User = findById(user.getUsername());
    	if(User.isPresent() || user.getEmail().isBlank() || user.getPassword().isBlank() || user.getRoles().isBlank())
    		return false; //Not saved
        repo.save(user);
		return true;  //All the details are valid and saved.
    }

    public Optional<My_User> findById(String id) {
        return repo.findById(id);
    }

    public String verify(My_User user) {
        try {
            Authentication auth = manager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    user.getUsername(),
                    user.getPassword()
                )
            );
            return jwt_Services.generateToken(user);
        } catch (AuthenticationException e) {
            return "Invalid credentials";
        }
    }
}
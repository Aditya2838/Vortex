package com.example.demo.Controllers;

import java.util.Map;

import org.apache.hc.core5.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Entities.My_User;
import com.example.demo.Services.User_Service;

@RestController
public class User_Controller {

    @Autowired
    private User_Service service;

    @Autowired
    private PasswordEncoder pe;

    @PostMapping("/Register")
    public ResponseEntity<?> saveUser(@RequestBody My_User user) {
        user.setPassword(pe.encode(user.getPassword()));
        if(service.save(user))
        	return ResponseEntity.status(HttpStatus.SC_CREATED)
                             .body(Map.of("message", "Added User"));
        return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST)
                .body(Map.of("message", "User Details Invalid."));
    }
    
    
    @PostMapping("/Login")
    public ResponseEntity<?> login(@RequestBody My_User user) {
        String result = service.verify(user);
        if ("Invalid credentials".equals(result)) {
            return ResponseEntity.status(HttpStatus.SC_UNAUTHORIZED)
                                 .body(Map.of("message", result));
        }
        return ResponseEntity.ok(Map.of("Generated Token", result));
    }

    @GetMapping
    public ResponseEntity<?> getmsg() {
        return ResponseEntity.ok(Map.of("message", "Welcome"));
    }
}
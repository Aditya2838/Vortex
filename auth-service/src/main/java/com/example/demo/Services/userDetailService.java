package com.example.demo.Services;

import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.Entities.My_User;
import com.example.demo.Repository.User_Repository;

@Service
public class userDetailService implements UserDetailsService{
	@Autowired
	User_Repository repo ;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = null ;
		Optional<My_User> temp = repo.findById(username);
		if(temp.isPresent()) {
			//user detail present
			My_User newUser = temp.get();
			String[] roles = newUser.getRoles().split(",");
			List<GrantedAuthority> authorities = new ArrayList<>();
			for(String role : roles) {
				authorities.add(new SimpleGrantedAuthority(role));
			}
			user = new User(username, newUser.getPassword(), authorities);
		}else {
			//User is not present
			throw new UsernameNotFoundException("No User Found!");
		}
		return user;
	}
}
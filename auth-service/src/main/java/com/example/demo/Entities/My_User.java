package com.example.demo.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class My_User {
	@Id
	@Column(nullable = false, unique = true)
	String username ;
	@Column(nullable = false, unique = true)
	String email ;
	@Column(nullable = false)
	String password ;
	@Column(nullable = false)
	String roles ;
	
	public My_User() {}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public My_User(String username, String email, String password, String roles) {
		super();
		this.username = username;
		this.email = email;
		this.password = password;
		this.roles = roles;
	}

	public String getRoles() {
		// TODO Auto-generated method stub
		return roles;
	}
	
}

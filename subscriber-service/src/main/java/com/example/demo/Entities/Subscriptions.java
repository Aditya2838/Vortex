package com.example.demo.Entities;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Subscriptions {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id ;
	String username ;
	@ManyToOne
	private MyPlans plan ;
	private LocalDate dateOfRecharged;
    private LocalDate dateOfExpiry;
    
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public MyPlans getPlan() {
		return plan;
	}
	public void setPlan(MyPlans plans) {
		this.plan = plans;
	}
	public LocalDate getDateOfRecharged() {
		return dateOfRecharged;
	}
	public void setDateOfRecharged(LocalDate dateOfRecharged) {
		this.dateOfRecharged = dateOfRecharged;
	}
	public LocalDate getDateOfExpiry() {
		return dateOfExpiry;
	}
	public void setDateOfExpiry(LocalDate dateOfExpiry) {
		this.dateOfExpiry = dateOfExpiry;
	}
}
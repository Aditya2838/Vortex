package com.example.demo.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Event_History {
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private long id ;
	
	private String username ; 
	private Integer nums_Ticket ;
	@ManyToOne
	private Event content ;
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
	public Integer getNums_Ticket() {
		return nums_Ticket;
	}
	public void setNums_Ticket(Integer nums_Ticket) {
		this.nums_Ticket = nums_Ticket;
	}
	public Event getContent() {
		return content;
	}
	public void setContent(Event content) {
		this.content = content;
	}
}

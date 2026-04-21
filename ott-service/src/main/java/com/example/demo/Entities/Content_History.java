package com.example.demo.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Content_History {
	
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private long id ;
	
	private String username ; 
	private String content_type ;
	@ManyToOne
	private Content content ;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public String getContent_type() {
		return content_type;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Content getContent() {
		return content;
	}
	public void setContent(Content content) {
		this.content = content;
	}
	public void setContent_type(String content_type) {
		this.content_type = content_type;
	}
}
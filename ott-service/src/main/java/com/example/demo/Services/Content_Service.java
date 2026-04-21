package com.example.demo.Services;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Entities.Content;
import com.example.demo.Repositories.Content_Repository;

@Service
public class Content_Service {
	
	@Autowired
	Content_Repository repo ;
	
	
	public void addContent(Content content) {
		repo.save(content);
	}
	
	public List<Content> getContent(){
		return repo.findAll();
	}
	
	public List<Content> getMovies(){
		return repo.findByType("MOVIE");
	}
	
	public List<Content> getSeries(){
		return repo.findByType("SERIES");
	}

}
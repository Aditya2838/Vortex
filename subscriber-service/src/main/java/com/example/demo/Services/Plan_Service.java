package com.example.demo.Services;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Entities.MyPlans;
import com.example.demo.Repositories.Plan_Repository;
import com.example.demo.Repositories.Subscription_Repository;

@Service
public class Plan_Service {
	@Autowired
	Plan_Repository repo ;
	
	@Autowired
	JWT_Filter filter;
	
	JWT_Services jwt_Services;
	
	@Autowired
	Subscription_Repository Subcription_repo;
	
	public void save(MyPlans plan) {
		repo.save(plan); 
	}
	
	public List<MyPlans> returnall(){
		return repo.findAll();
	}
	
	public Optional<MyPlans> getPlan(Long id) {
		return repo.findById(id);
	}

	public String getUsername() {
		return filter.username;
	}
	
}
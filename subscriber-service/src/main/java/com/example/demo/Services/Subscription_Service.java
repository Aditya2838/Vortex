package com.example.demo.Services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Entities.MyPlans;
import com.example.demo.Entities.Subscriptions;
import com.example.demo.Repositories.Subscription_Repository;

@Service
public class Subscription_Service {
	@Autowired
	JWT_Filter filter ; 
	
	@Autowired
	Subscription_Repository repo;
	
	@Autowired
	Plan_Service plan_Service;
	public String subscribe(Long id) {
		Optional<MyPlans> plan = plan_Service.getPlan(id);
		
		if(plan.isPresent()) {
			String username = plan_Service.getUsername();
			//Check for existing plan in this username only allow him to upgrade and not to subscribe for existing plans or lower plans.
			List<Subscriptions> user_Subscriptions = repo.findByUsername(username);
			for(Subscriptions subscriptions : user_Subscriptions) {
				if(subscriptions.getDateOfExpiry().isAfter(LocalDate.now()) && id <= subscriptions.getPlan().getId()) {
					return "Subscription request denied: You already have an active plan. Upgrades are allowed, but not downgrades or duplicates.";
				}
			}
			Subscriptions subscription = new Subscriptions();
			MyPlans myPlans = plan.get();
			subscription.setUsername(username);
			subscription.setPlan(myPlans);
			subscription.setDateOfRecharged(LocalDate.now());
		    subscription.setDateOfExpiry(LocalDate.now().plusDays(myPlans.getValidityDays()));
		    repo.save(subscription);
		    return "Great choice, " + username.toUpperCase() + 
		    		"! You’ve subscribed to " + myPlans.getPlanName() + 
		    		". Sit back and enjoy your Netflix experience.";
		}
		return "Invalid Plan";
	}
	
	public List<Subscriptions> history() {
		String username = plan_Service.getUsername();
		return repo.findByUsername(username);
	}

	public String plan() {
		List<Subscriptions> history = history();
		MyPlans existing_Plan = null ;
		for(Subscriptions sub : history) {
			if(sub.getDateOfExpiry().isAfter(LocalDate.now())) {
				//If plan is valid
				if (sub.getDateOfExpiry().isAfter(LocalDate.now())) {
				    if (existing_Plan == null || existing_Plan.getId() < sub.getPlan().getId()) {
				        existing_Plan = sub.getPlan();
				    }
				}
			}
		}
		return (existing_Plan != null) 
			        ? existing_Plan.getPlanName() 
			        : "No NETFLIX subscription";
	}
}
package com.example.demo.Controllers;

import java.util.List;
import java.util.Map;

import org.apache.hc.core5.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Entities.MyPlans;
import com.example.demo.Entities.Subscriptions;
import com.example.demo.Services.Plan_Service;
import com.example.demo.Services.Subscription_Service;

@RestController
public class My_Controller {
	
	@Autowired
	Plan_Service plan_service;
	
	@Autowired
	Subscription_Service subscription_Service;
	
	 	@PostMapping("/Admin/AddPlan")
	    public ResponseEntity<?> addPlan(@RequestBody MyPlans plan) {
	        plan_service.save(plan);
	        return ResponseEntity.status(HttpStatus.SC_CREATED)
	                             .body(Map.of("message", "Plan added successfully!"));
	    }

	    @GetMapping("/Plans")
	    public ResponseEntity<?> getPlans() {
	        System.out.println("Hitting the plans");
	        List<MyPlans> plans = plan_service.returnall();
	        if (plans.isEmpty()) {
	            return ResponseEntity.status(HttpStatus.SC_NO_CONTENT).build();
	        }
	        return ResponseEntity.ok(plans);
	    }

	    @PostMapping("/Subscribe/{id}")
	    public ResponseEntity<?> subscribe(@PathVariable Long id) {
	        System.out.println("Hitting Subscribe");
	        String result = subscription_Service.subscribe(id);
	        if ("No NETFLIX subscription".equals(result)) {
	            return ResponseEntity.status(HttpStatus.SC_FORBIDDEN)
	                                 .body(Map.of("message", result));
	        }
	        return ResponseEntity.ok(Map.of("message", result));
	    }

	    @GetMapping("/History")
	    public ResponseEntity<?> history() {
	        List<Subscriptions> subs = subscription_Service.history();
	        if (subs.isEmpty()) {
	            return ResponseEntity.status(HttpStatus.SC_NO_CONTENT).build();
	        }
	        return ResponseEntity.ok(subs);
	    }

	    @GetMapping("/Get-plan")    //Feign - Client Interface
		public String getPlan() {
			System.out.println("Hitting get-lan api!");
			return subscription_Service.plan();
		}
}
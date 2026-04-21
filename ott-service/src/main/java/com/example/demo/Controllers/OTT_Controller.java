package com.example.demo.Controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Services.Content_Service;
import com.example.demo.Services.Event_Service;
import com.example.demo.Services.JWT_Services;
import com.example.demo.Services.OTT_Services;
import com.example.demo.Services.SubscriptionClientWrapper;
import com.example.demo.Services.Subscription_Client;


@RestController
@RequestMapping("/Netflix")
public class OTT_Controller {
	
	@Autowired
	Subscription_Client subscription_Client;
	
	@Autowired
	Content_Service content_service;
	
	@Autowired
	Event_Service event_Service;
	
	@Autowired
	OTT_Services ott_Services;
	
	@Autowired
	JWT_Services jwt_Services;
	
	@Autowired
	CircuitBreakerFactory<?, ?> cbf;
	
	@Autowired
	SubscriptionClientWrapper subscriptionClientWrapper;
	
	@GetMapping
	public ResponseEntity<?> netflix(@RequestHeader("Authorization") String authHeader) {
	    System.out.println("Hitting netfix endpoint!");
	    String token = authHeader.substring(7);
	    String plan = subscriptionClientWrapper.plan("Bearer " + token);

	    if ("Service is not available for this moment , try again.".equals(plan)) {
	        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
	                             .body(Map.of("message", plan));
	    }
	    if ("No NETFLIX subscription".equals(plan)) {
	        return ResponseEntity.status(HttpStatus.FORBIDDEN)
	                             .body(Map.of("message", plan));
	    }
	    
	    String username = jwt_Services.extractToken(token).getSubject();
	    
	    List<Object> combined = new ArrayList<>();
	    combined.addAll(content_service.getContent());
	    combined.addAll(event_Service.getEvents());
	    return ResponseEntity.ok(
	    	    Map.of(
	    	        "message", "Welcome, " + username.toUpperCase() +
	    	                   ". You are subscribed to the " + plan + " plan.",
	    	        "contents", combined
	    	    )
	    	);
	}

	
	@GetMapping("/Movies")
    public ResponseEntity<?> getMovies(@RequestHeader("Authorization") String authHeader){
        String token = authHeader.substring(7);
        String plan = subscriptionClientWrapper.plan("Bearer " + token);

        if ("Service is not available for this moment , try again.".equals(plan)) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                                 .body(Map.of("message", plan));
        }
        if ("No NETFLIX subscription".equals(plan)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                                 .body(Map.of("message", plan));
        }
        return ResponseEntity.ok(content_service.getMovies());
    }
	
	@GetMapping("/Series")
    public ResponseEntity<?> getSeries(@RequestHeader("Authorization") String authHeader){
        String token = authHeader.substring(7);
        String plan = subscriptionClientWrapper.plan("Bearer " + token);

        if ("Service is not available for this moment , try again.".equals(plan)) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                                 .body(Map.of("message", plan));
        }
        if ("No NETFLIX subscription".equals(plan)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                                 .body(Map.of("message", plan));
        }
        return ResponseEntity.ok(content_service.getSeries());
    }

    @GetMapping("/Events")
    public ResponseEntity<?> getEvents(@RequestHeader("Authorization") String authHeader){
        String token = authHeader.substring(7);
        String plan = subscriptionClientWrapper.plan("Bearer " + token);

        if ("Service is not available for this moment , try again.".equals(plan)) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                                 .body(Map.of("message", plan));
        }
        if ("No NETFLIX subscription".equals(plan)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                                 .body(Map.of("message", plan));
        }
        return ResponseEntity.ok(event_Service.getEvents());
    }

    @GetMapping("/Events/{location}")
    public ResponseEntity<?> getEventsByLocation(@RequestHeader("Authorization") String authHeader,
                                                 @PathVariable String location){
        String token = authHeader.substring(7);
        String plan = subscriptionClientWrapper.plan("Bearer " + token);

        if ("Service is not available for this moment , try again.".equals(plan)) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                                 .body(Map.of("message", plan));
        }
        if ("No NETFLIX subscription".equals(plan)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                                 .body(Map.of("message", plan));
        }
        return ResponseEntity.ok(event_Service.getEventsByLocation(location));
    }
    

    @GetMapping("/Watch/{Id}")
    public ResponseEntity<?> watchNow(@RequestHeader("Authorization") String authHeader,
                                      @PathVariable Long Id) {
        String token = authHeader.substring(7);
        String plan = subscriptionClientWrapper.plan("Bearer " + token);
        String username = jwt_Services.extractToken(token).getSubject();

        if ("Service is not available for this moment , try again.".equals(plan)) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                                 .body(Map.of("message", plan));
        }
        if ("No NETFLIX subscription".equals(plan)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                                 .body(Map.of("message", plan));
        }
        return ResponseEntity.ok(ott_Services.watch(Id, username));
    }

    @GetMapping("/Book/{Id}")
    public ResponseEntity<?> bookNow(@RequestHeader("Authorization") String authHeader,
                                     @PathVariable Long Id , @RequestBody int nums_Tickets) {
        String token = authHeader.substring(7);
        String plan = subscriptionClientWrapper.plan("Bearer " + token);
        String username = jwt_Services.extractToken(token).getSubject();

        if ("Service is not available for this moment , try again.".equals(plan)) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                                 .body(Map.of("message", plan));
        }
        if ("No NETFLIX subscription".equals(plan)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                                 .body(Map.of("message", plan));
        }
        return ResponseEntity.ok(ott_Services.bookEvent(Id, username , nums_Tickets));
    }

    @GetMapping("/Watch-History")
    public ResponseEntity<?> watchHistory(@RequestHeader("Authorization") String authHeader){
        String token = authHeader.substring(7);
        String plan = subscriptionClientWrapper.plan("Bearer " + token);
        String username = jwt_Services.extractToken(token).getSubject();

        if ("Service is not available for this moment , try again.".equals(plan)) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                                 .body(Map.of("message", plan));
        }
        if ("No NETFLIX subscription".equals(plan)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                                 .body(Map.of("message", plan));
        }
        return ResponseEntity.ok(ott_Services.content_History(username));
    }

    @GetMapping("/Event-History")
    public ResponseEntity<?> eventHistory(@RequestHeader("Authorization") String authHeader){
        String token = authHeader.substring(7);
        String plan = subscriptionClientWrapper.plan("Bearer " + token);
        String username = jwt_Services.extractToken(token).getSubject();

        if ("Service is not available for this moment , try again.".equals(plan)) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                                 .body(Map.of("message", plan));
        }
        if ("No NETFLIX subscription".equals(plan)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                                 .body(Map.of("message", plan));
        }
        return ResponseEntity.ok(ott_Services.eventHistory(username));
    }

    @GetMapping("/Recommendations")
    public ResponseEntity<?> recommendations(@RequestHeader("Authorization") String authHeader){
        String token = authHeader.substring(7);
        String plan = subscriptionClientWrapper.plan("Bearer " + token);
        String username = jwt_Services.extractToken(token).getSubject();

        if ("Service is not available for this moment , try again.".equals(plan)) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                                 .body(Map.of("message", plan));
        }
        if ("No NETFLIX subscription".equals(plan)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                                 .body(Map.of("message", plan));
        }
        return ResponseEntity.ok(ott_Services.recommendations(username));
    }
    
    @GetMapping("/{content_Name}")
    public ResponseEntity<?> getContent(@RequestHeader("Authorization") String authHeader
    		,@PathVariable String content_Name){
        String token = authHeader.substring(7);
        String plan = subscriptionClientWrapper.plan("Bearer " + token);
        String username = jwt_Services.extractToken(token).getSubject();

        if ("Service is not available for this moment , try again.".equals(plan)) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                                 .body(Map.of("message", plan));
        }
        if ("No NETFLIX subscription".equals(plan)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                                 .body(Map.of("message", plan));
        }
        return ResponseEntity.ok(ott_Services.getContent(content_Name));
    }
}
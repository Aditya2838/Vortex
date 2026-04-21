package com.example.demo.Controllers;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Entities.Content;
import com.example.demo.Entities.Event;
import com.example.demo.Services.Content_Service;
import com.example.demo.Services.Event_Service;

@RestController
@RequestMapping("/Admin")
public class Content_Controller {
	
	@Autowired
	Content_Service content_service ;
	
	@Autowired
	Event_Service event_Service ;
	
	@PostMapping("/Add-Content")
	public ResponseEntity<String> addContent(@RequestBody Content content) {
		System.out.println("Hitting Add content controller");
		content_service.addContent(content);
		return ResponseEntity.ok("Content added successfully!");
	}
	
	@PostMapping("/Add-Event")
	public ResponseEntity<String> addEvent(@RequestBody Event content) {
		System.out.println("Hitting Add content controller");
		event_Service.addEvent(content);
		return ResponseEntity.ok("Event added successfully!");
	}
	
	@GetMapping("/Get-Content")
	public List<Content> getContent(){
		System.out.println("Hitting getting content controller");
		return content_service.getContent();
	}
	
	@GetMapping("/Get-Event")
	public List<Event> getEvent(){
		System.out.println("Hitting getting content controller");
		return event_Service.getEvents();
	}
}
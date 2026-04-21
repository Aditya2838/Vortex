package com.example.demo.Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Entities.Event;
import com.example.demo.Repositories.Event_Repository;

@Service
public class Event_Service {
	
	@Autowired
	Event_Repository repo ;
	
	public void addEvent(Event content) {
		repo.save(content);
	}

	public List<Event> getEvents() {
		return repo.findAll();
	}	
	
	public List<Event> getEventsByLocation(String location) {
	    location = location.toLowerCase();
	    List<Event> events = getEvents();
	    List<Event> result = new ArrayList<>();

	    for (Event event : events) {
	        if (event.getLocation().toLowerCase().contains(location)) {
	            result.add(event);
	        }
	    }
	    return result;
	}
}
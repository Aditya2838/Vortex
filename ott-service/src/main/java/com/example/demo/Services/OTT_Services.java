package com.example.demo.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.TreeMap;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Entities.Content;
import com.example.demo.Entities.Content_History;
import com.example.demo.Entities.Event;
import com.example.demo.Entities.Event_History;
import com.example.demo.Repositories.Content_History_Repository;
import com.example.demo.Repositories.Content_Repository;
import com.example.demo.Repositories.Event_History_Repository;
import com.example.demo.Repositories.Event_Repository;

@Service
public class OTT_Services {
	@Autowired
	Content_History_Repository content_History_Repository ;
	
	@Autowired
	Content_Repository content_Repository;
	
	@Autowired
	Event_Repository event_Repository;
	
	@Autowired
	Event_History_Repository  event_History_Repository;
	
	public String watch(long id , String username) {
		Optional<Content> list = content_Repository.findById(id);
		if(list.isPresent()) {
			Content content = list.get(); 
				List<Content_History> listHistory = content_History_Repository.findByUsernameAndContent_Id(username, id);
		        if (!listHistory.isEmpty()) return "Watching Now '" + content.getTitle() + "'";
				//Don't make duplicates entry of watched history.
				Content_History content_History = new Content_History();
				content_History.setUsername(username);
				content_History.setContent(content);
				content_History.setContent_type(content.getType());
				content_History_Repository.save(content_History);
				return "Watching Now '" + content.getTitle() + "'";
		}
		return "Invalid Content Type";
	}
	
	
	public String bookEvent(Long id , String username , int nums_Ticket) {
		Optional<Event> list = event_Repository.findById(id);
		if(list.isPresent()) {
			Event event = list.get(); 
			Event_History event_history = new Event_History();
			event_history.setUsername(username);
			event_history.setNums_Ticket(nums_Ticket);
			event_history.setContent(event);
			event_History_Repository.save(event_history);
			return "Thanks for Booking " + nums_Ticket + " Tickets , For '" + event.getTitle() + "'. Enjoy the Show!";
		}
		return "Invalid Content Type";
	}

	public List<Content_History> content_History(String username) {
		return content_History_Repository.findByUsername(username);
	}

	public List<Event_History> eventHistory(String username) {
		return event_History_Repository.findByUsername(username);
	}

	public List<Content> recommendations(String username) {
		List<Content> content = content_Repository.findAll();
		List<Content_History> content_History = content_History(username);
		
		if(content_History.isEmpty()) return content;
		
		TreeMap<String, Integer> map = new TreeMap<>();
		
		for(Content_History list : content_History) {
			map.put(list.getContent().getGenre() , map.getOrDefault(list.getContent().getGenre(), 0) + 1);
		}
		
		List<Content> recommendation_List = new ArrayList<>();
		
		for (Entry<String, Integer> entry : map.entrySet()) {
		    String genre = entry.getKey();
		    for(Content content_list : content) {
		    	if(content_list.getGenre().equals(genre)) {
		    		for(Content_History history_list : content_History) {
		    			if(history_list.getContent().getId() != content_list.getId()) {
		    				recommendation_List.add(content_list);
		    			}
		    		}
		    	}
		    }
		}
		return recommendation_List;
	}

	public Content getContent(String content_Name) {
		return content_Repository.findByTitle(content_Name);
	}
	
}
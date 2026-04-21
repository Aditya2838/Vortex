package com.example.demo.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Entities.Event;
import com.example.demo.Entities.Event_History;

@Repository
public interface Event_Repository extends JpaRepository<Event, Long> {

	List<Event> findByLocationIgnoreCase(String location);

}
package com.example.demo.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Entities.Event_History;

@Repository
public interface Event_History_Repository extends JpaRepository<Event_History, Long> {

	List<Event_History> findByUsername(String username);

}

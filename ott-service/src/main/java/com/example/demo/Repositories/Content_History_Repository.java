package com.example.demo.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entities.Content_History;

public interface Content_History_Repository extends JpaRepository<Content_History, Long>{

	List<Content_History> findByUsernameAndContent_Id(String username, long id);

	List<Content_History> findByUsername(String username);
}
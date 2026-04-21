package com.example.demo.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Entities.Content;

@Repository
public interface Content_Repository extends JpaRepository<Content, Long>{

	List<Content> findByType(String string);

	Content findByTitle(String content_Name);


}
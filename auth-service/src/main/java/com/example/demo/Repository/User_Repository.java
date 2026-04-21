package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Entities.My_User;

@Repository
public interface User_Repository  extends JpaRepository<My_User, String>{
	
}
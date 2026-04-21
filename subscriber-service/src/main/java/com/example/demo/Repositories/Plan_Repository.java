package com.example.demo.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entities.MyPlans;

public interface Plan_Repository extends JpaRepository<MyPlans, Long>{

}

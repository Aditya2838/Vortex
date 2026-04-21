package com.example.demo.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Entities.Subscriptions;

@Repository
public interface Subscription_Repository extends JpaRepository<Subscriptions, Long>{

	List<Subscriptions> findByUsername(String username);

}
package com.example.demo.Services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@Service
@FeignClient(name="subscriber-service" , url = "http://localhost:8082")
public interface Subscription_Client {
	@GetMapping("/Get-plan")
	public String plan(@RequestHeader("Authorization") String token);
}
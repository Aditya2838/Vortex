package com.example.demo.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionClientWrapper {

    private final Subscription_Client subscriptionClient;
    private final CircuitBreakerFactory<?, ?> cbf;

    @Autowired
    public SubscriptionClientWrapper(Subscription_Client subscriptionClient,
                                     CircuitBreakerFactory<?, ?> cbf) {
        this.subscriptionClient = subscriptionClient;
        this.cbf = cbf;
    }

    public String plan(String token) {
        return cbf.create("Subscription-CB")
                  .run(() -> subscriptionClient.plan(token),
                       ex -> rollBack(ex));
    }

	    private String rollBack(Throwable ex) {
	        // Fallback when Subscription-Service is unavailable
	        return "Service is not available for this moment , try again.";
	    }
}

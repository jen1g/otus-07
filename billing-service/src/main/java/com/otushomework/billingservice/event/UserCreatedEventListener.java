package com.otushomework.billingservice.event;

import com.otushomework.billingservice.service.BillingService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class UserCreatedEventListener {

    private final BillingService billingService;

    public UserCreatedEventListener(BillingService billingService) {
        this.billingService = billingService;
    }

    @KafkaListener(topics = "user-created-topic", groupId = "billing-service-group")
    public void consume(UserCreatedEvent event) {
        // Create an account for the new user //todo
        billingService.createAccount(event.getUserId());
    }
}

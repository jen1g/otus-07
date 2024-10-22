package com.otushomework.orderservice.service.impl;

import com.otushomework.orderservice.entity.WithdrawRequest;
import com.otushomework.orderservice.exception.InsufficientFundsException;
import com.otushomework.orderservice.service.KafkaMessageProducer;
import com.otushomework.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderServiceImpl implements OrderService {

    @Value("${billing.service.url}")
    private String billingServiceUrl;

    private final RestTemplate restTemplate = new RestTemplate();
    private final KafkaMessageProducer kafkaProducer;

    public OrderServiceImpl(KafkaMessageProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @Override
    public void createOrderWithHold(Long userId, double amount) throws InsufficientFundsException {
        // 1. Списываем средства через billing-service
        WithdrawRequest requestBody = new WithdrawRequest(userId, amount);
        String message;
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(billingServiceUrl + "/billing/withdraw", requestBody, String.class);
            if (response.getStatusCode().is2xxSuccessful()) {
              message = "Order processed successfully for userId: " + userId;
              kafkaProducer.sendMessage("order-notifications", message);
            }
        } catch (HttpStatusCodeException e) {
            message = "Order failed due to insufficient funds for userId: " + userId;
            kafkaProducer.sendMessage("order-notifications", message);
            throw new InsufficientFundsException(message);
        }
    }


}

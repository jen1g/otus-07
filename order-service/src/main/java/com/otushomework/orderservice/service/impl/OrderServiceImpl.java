package com.otushomework.orderservice.service.impl;

import com.otushomework.orderservice.entity.OrderRequest;
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
    public void createOrderWithHold(String userId, OrderRequest request) throws InsufficientFundsException {
        WithdrawRequest requestBody = new WithdrawRequest(Long.valueOf(userId), request.getTotalAmount());
        String message;
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(billingServiceUrl + "/billing/withdraw", requestBody, String.class);
            if (response.getStatusCode().is2xxSuccessful()) {
              message = "Заказ успешно обработан userId: " + userId;
              kafkaProducer.sendMessage("order-notifications", message);
            }
        } catch (HttpStatusCodeException e) {
            message = "Заказ не принят в обработку: Недостаточно средств userId: " + userId;
            kafkaProducer.sendMessage("order-notifications", message);
            throw new InsufficientFundsException(message);
        }
    }


}

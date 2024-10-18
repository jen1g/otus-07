package com.otushomework.billingservice.event;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class BillingEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public BillingEventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishTransactionEvent(BillingTransactionEvent event) {
        kafkaTemplate.send("billing-transaction-topic", event);
    }
}

package com.otushomework.paymentservice.service;

import com.otushomework.paymentservice.entity.PayStatus;
import com.otushomework.paymentservice.entity.Payment;
import com.otushomework.paymentservice.feign.BillingClient;
import com.otushomework.paymentservice.repository.PaymentRepository;
import com.otushomework.paymentservice.request.BillingRequest;
import com.otushomework.paymentservice.request.PaymentRequest;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private BillingClient billingClient;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Transactional
    public void processPayment(PaymentRequest paymentRequest) {
        BillingRequest billingRequest = new BillingRequest(paymentRequest.getUserId(), paymentRequest.getAmount());
        ResponseEntity<String> response = billingClient.withdraw(billingRequest);
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Ошибка при списании средств: " + response.getBody());
        }
        Payment payment = new Payment();
        payment.setOrderId(paymentRequest.getOrderId());
        payment.setUserId(paymentRequest.getUserId());
        payment.setAmount(paymentRequest.getAmount());
        payment.setPayStatus(PayStatus.COMPLETED);
        paymentRepository.save(payment);
    }

    @Transactional
    public void refundPayment(PaymentRequest paymentRequest) {
        BillingRequest billingRequest = new BillingRequest(paymentRequest.getUserId(), paymentRequest.getAmount());
        ResponseEntity<String> response = billingClient.refund(billingRequest);
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Ошибка при возврате средств: " + response.getBody());
        }

        Payment payment = paymentRepository.findByOrderId(paymentRequest.getOrderId())
                .orElseThrow(() -> new RuntimeException("Платеж не найден"));
        payment.setPayStatus(PayStatus.REFUNDED);
        paymentRepository.save(payment);
    }
}

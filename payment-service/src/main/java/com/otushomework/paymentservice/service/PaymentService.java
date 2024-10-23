package com.otushomework.paymentservice.service;

import com.otushomework.paymentservice.entity.PayStatus;
import com.otushomework.paymentservice.entity.Payment;
import com.otushomework.paymentservice.repository.PaymentRepository;
import com.otushomework.paymentservice.request.PaymentRequest;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Transactional
    public void processPayment(PaymentRequest paymentRequest) {
        Payment payment = new Payment();
        payment.setOrderId(paymentRequest.getOrderId());
        payment.setUserId(paymentRequest.getUserId());
        payment.setAmount(paymentRequest.getAmount());
        payment.setPayStatus(PayStatus.COMPLETED);
        paymentRepository.save(payment);
    }

    @Transactional
    public void refundPayment(PaymentRequest paymentRequest) {
        Payment payment = paymentRepository.findByOrderId(paymentRequest.getOrderId())
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        payment.setPayStatus(PayStatus.REFUND);
        paymentRepository.save(payment);
    }
}

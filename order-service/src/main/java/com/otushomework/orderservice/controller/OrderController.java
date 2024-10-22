package com.otushomework.orderservice.controller;

import com.otushomework.orderservice.exception.InsufficientFundsException;
import com.otushomework.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestParam Long userId, @RequestParam double orderPrice) {
        try {
            orderService.createOrderWithHold(userId, orderPrice);
        } catch (InsufficientFundsException e){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Order not created. Insufficient funds.");
        }
        return ResponseEntity.ok("Order created and funds reserved");
    }


}

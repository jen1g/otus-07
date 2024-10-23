package com.otushomework.orderservice.controller;

import com.otushomework.orderservice.entity.OrderRequest;
import com.otushomework.orderservice.exception.InsufficientFundsException;
import com.otushomework.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody OrderRequest request, @RequestHeader(value = "X-User-Id", required = false) String xUserId) {
        try {
            orderService.createOrderWithHold(xUserId, request);
        } catch (InsufficientFundsException e){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Недостаточно средств для создания заказа.");
        }
        return ResponseEntity.ok(String.format("Заказ %s принят в обработку", String.valueOf(request.getOrderId())));
    }


}

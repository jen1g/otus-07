package com.otushomework.orderservice.service;

import com.otushomework.orderservice.entity.OrderRequest;
import com.otushomework.orderservice.exception.InsufficientFundsException;

public interface OrderService {

    void createOrderWithHold(String userId, OrderRequest request) throws InsufficientFundsException;
}

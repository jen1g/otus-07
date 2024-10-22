package com.otushomework.orderservice.service;

import com.otushomework.orderservice.exception.InsufficientFundsException;

public interface OrderService {

    void createOrderWithHold(Long userId, double orderPrice) throws InsufficientFundsException;
}

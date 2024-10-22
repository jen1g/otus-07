package com.otushomework.orderservice.entity;

public class Order {

    private Long orderId;

    public Order(Long orderId) {
        this.orderId = orderId;
    }

    public Order() {
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}

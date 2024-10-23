package com.siamgeotest.otushomework.itemsservice.entity;

import java.util.List;

public class ItemRequest {

    private Long orderId;
    private List<ProductItem> items;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public List<ProductItem> getItems() {
        return items;
    }

    public void setItems(List<ProductItem> items) {
        this.items = items;
    }
}

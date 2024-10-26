package com.otushomework.orderservice.dto;

import com.otushomework.orderservice.entity.ProductItem;

import java.util.List;

public class OrderRequest {

    private List<ProductItem> items;
    private Double totalAmount;
    private String timeslotId;

    public List<ProductItem> getItems() {
        return items;
    }

    public void setItems(List<ProductItem> items) {
        this.items = items;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getTimeslotId() {
        return timeslotId;
    }

    public void setTimeslotId(String timeslotId) {
        this.timeslotId = timeslotId;
    }
}

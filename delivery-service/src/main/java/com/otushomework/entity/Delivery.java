package com.otushomework.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Delivery {

    @Id
    private Long orderId;
    private String timeslot;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getTimeslot() {
        return timeslot;
    }

    public void setTimeslot(String timeslot) {
        this.timeslot = timeslot;
    }
}

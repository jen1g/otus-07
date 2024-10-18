package com.otushomework.billingservice.model;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;


@Entity
public class Account {

    @Id
    private Integer userId;

    private Double balance;

    public Account() {
    }

    public Account(Integer userId, Double balance) {
        this.userId = userId;
        this.balance = balance;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}

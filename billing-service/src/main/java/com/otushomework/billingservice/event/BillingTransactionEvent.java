package com.otushomework.billingservice.event;

public class BillingTransactionEvent {
    private Long userId;
    private Double amount;
    private String transactionType; // "DEPOSIT" or "WITHDRAWAL"
    private String status; // "SUCCESS" or "FAILURE"

    public BillingTransactionEvent(Long userId, Double amount, String transactionType, String status) {
        this.userId = userId;
        this.amount = amount;
        this.transactionType = transactionType;
        this.status = status;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
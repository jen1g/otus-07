package com.otushomework.authservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class Session {

    @Id
    private String id;

    private Long userId;

    private String userName;

    private LocalDateTime expiresIn;

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public LocalDateTime getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(LocalDateTime expiresIn) {
        this.expiresIn = expiresIn;
    }
}

package com.otushomework.notificationservice.controller;

import com.otushomework.notificationservice.entity.Notification;
import com.otushomework.notificationservice.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private NotificationService service;

    @Autowired
    public NotificationController(NotificationService service) {
        this.service = service;
    }


    @GetMapping
    public List<Notification> getMessage() {
        List<Notification> balance = service.getAllNotifications();
        return balance;
    }
}

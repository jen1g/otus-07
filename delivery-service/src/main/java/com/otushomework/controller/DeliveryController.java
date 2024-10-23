package com.otushomework.controller;

import com.otushomework.request.DeliveryRequest;
import com.otushomework.service.DeliveryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/delivery")
public class DeliveryController {

    private final DeliveryService deliveryService;

    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @PostMapping("/schedule")
    public ResponseEntity<?> scheduleDelivery(@RequestBody DeliveryRequest deliveryRequest) {
        try {
            deliveryService.scheduleDelivery(deliveryRequest);
            return ResponseEntity.ok("Доставка создана");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Не удалось создать доставку: " + e.getMessage());
        }
    }

    @PostMapping("/cancel")
    public ResponseEntity<?> cancelDelivery(@RequestParam Long orderId) {
        try {
            deliveryService.cancelDelivery(orderId);
            return ResponseEntity.ok("Доставка отменена");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Не удалось отменить доставку: " + e.getMessage());
        }
    }
}
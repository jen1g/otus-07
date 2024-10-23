package com.siamgeotest.otushomework.itemsservice.controller;

import com.siamgeotest.otushomework.itemsservice.entity.ItemRequest;
import com.siamgeotest.otushomework.itemsservice.service.ItemsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ItemsController {

    private final ItemsService itemsService;

    public ItemsController(ItemsService itemsService) {
        this.itemsService = itemsService;
    }

    @PostMapping("/reserve")
    public ResponseEntity<?> reserveInventory(@RequestBody ItemRequest itemRequest) {
        try {
            itemsService.reserveProducts(itemRequest);
            return ResponseEntity.ok("Inventory reserved successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Inventory reservation failed: " + e.getMessage());
        }
    }

    @PostMapping("/release")
    public ResponseEntity<?> releaseInventory(@RequestBody ItemRequest itemRequest) {
        try {
            itemsService.releaseProducts(itemRequest);
            return ResponseEntity.ok("Inventory released successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Inventory release failed: " + e.getMessage());
        }
    }
}

package com.siamgeotest.otushomework.itemsservice.controller;

import com.siamgeotest.otushomework.itemsservice.entity.Product;
import com.siamgeotest.otushomework.itemsservice.entity.WarehouseRequest;
import com.siamgeotest.otushomework.itemsservice.service.WarehouseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WarehouseController {

    private final WarehouseService warehouseService;

    public WarehouseController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    @PostMapping("/reserve")
    public ResponseEntity<?> reserveInventory(@RequestBody WarehouseRequest itemRequest) {
        try {
            warehouseService.reserveProducts(itemRequest);
            return ResponseEntity.ok("Товар зарезервирован");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Не удалось зарезервировать товар: " + e.getMessage());
        }
    }

    @PostMapping("/release")
    public ResponseEntity<?> releaseInventory(@RequestBody WarehouseRequest warehouseRequest) {
        try {
            warehouseService.releaseProducts(warehouseRequest);
            return ResponseEntity.ok("Резервирование товара снято");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Не удалось разрезервировать товар: " + e.getMessage());
        }
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAvailableProducts() {
        List<Product> products = warehouseService.getAvailableProducts();
        return ResponseEntity.ok(products);
    }
}

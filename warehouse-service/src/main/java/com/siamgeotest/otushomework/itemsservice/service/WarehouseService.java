package com.siamgeotest.otushomework.itemsservice.service;

import com.siamgeotest.otushomework.itemsservice.entity.Product;
import com.siamgeotest.otushomework.itemsservice.entity.ProductItem;
import com.siamgeotest.otushomework.itemsservice.entity.WarehouseRequest;
import com.siamgeotest.otushomework.itemsservice.repository.WarehouseRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WarehouseService {

    private final WarehouseRepository warehouseRepository;

    public WarehouseService(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    @Transactional
    public void reserveProducts(WarehouseRequest warehouseRequest) {
        for (ProductItem item : warehouseRequest.getItems()) {
            Product product = warehouseRepository.findById(item.getProductId())
                    .orElseThrow(() -> new RuntimeException("Товар не найден"));
            if (product.getAvailableQuantity() >= item.getQuantity()) {
                product.setAvailableQuantity(product.getAvailableQuantity() - item.getQuantity());
                warehouseRepository.save(product);
            } else {
                throw new RuntimeException("Нет достаточного количества на складе ID: " + item.getProductId());
            }
        }
    }

    @Transactional
    public void releaseProducts(WarehouseRequest warehouseRequest) {
        for (ProductItem item : warehouseRequest.getItems()) {
            Product product = warehouseRepository.findById(item.getProductId())
                    .orElseThrow(() -> new RuntimeException("Товар не найден"));
            product.setAvailableQuantity(product.getAvailableQuantity() + item.getQuantity());
            warehouseRepository.save(product);
        }
    }

    public List<Product> getAvailableProducts() {
        return warehouseRepository.findAll();
    }
}

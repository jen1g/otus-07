package com.siamgeotest.otushomework.itemsservice.service;

import com.siamgeotest.otushomework.itemsservice.entity.ItemRequest;
import com.siamgeotest.otushomework.itemsservice.entity.Product;
import com.siamgeotest.otushomework.itemsservice.entity.ProductItem;
import com.siamgeotest.otushomework.itemsservice.repository.ItemsRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ItemsService {

    private final ItemsRepository itemsRepository;

    public ItemsService(ItemsRepository itemsRepository) {
        this.itemsRepository = itemsRepository;
    }

    @Transactional
    public void reserveProducts(ItemRequest itemRequest) {
        for (ProductItem item : itemRequest.getItems()) {
            Product product = itemsRepository.findById(item.getProductId())
                    .orElseThrow(() -> new RuntimeException("Товар не найден"));
            if (product.getAvailableQuantity() >= item.getQuantity()) {
                product.setAvailableQuantity(product.getAvailableQuantity() - item.getQuantity());
                itemsRepository.save(product);
            } else {
                throw new RuntimeException("Нет достаточного количества на складе ID: " + item.getProductId());
            }
        }
    }

    @Transactional
    public void releaseProducts(ItemRequest itemRequest) {
        for (ProductItem item : itemRequest.getItems()) {
            Product product = itemsRepository.findById(item.getProductId())
                    .orElseThrow(() -> new RuntimeException("Товар не найден"));
            product.setAvailableQuantity(product.getAvailableQuantity() + item.getQuantity());
            itemsRepository.save(product);
        }
    }
}

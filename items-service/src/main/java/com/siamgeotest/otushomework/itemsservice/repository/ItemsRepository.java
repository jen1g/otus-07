package com.siamgeotest.otushomework.itemsservice.repository;

import com.siamgeotest.otushomework.itemsservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemsRepository extends JpaRepository<Product, Long> {
}

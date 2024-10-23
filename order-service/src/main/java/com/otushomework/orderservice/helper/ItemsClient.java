package com.otushomework.orderservice.helper;

import com.otushomework.orderservice.request.ItemsRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "items-service", url = "${items.service.url}")
public interface ItemsClient {

    @PostMapping("/items/reserve")
    ResponseEntity<String> reserveInventory(@RequestBody ItemsRequest request);

    @PostMapping("/items/release")
    ResponseEntity<String> releaseInventory(@RequestBody ItemsRequest request);
}

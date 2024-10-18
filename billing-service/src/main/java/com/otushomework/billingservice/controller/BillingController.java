package com.otushomework.billingservice.controller;

import com.otushomework.billingservice.service.BillingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
public class BillingController {

    private final BillingService billingService;

    public BillingController(BillingService billingService) {
        this.billingService = billingService;
    }

    @GetMapping("/{userId}/balance")
    public ResponseEntity<Double> getBalance(@PathVariable Long userId) {
        Double balance = billingService.getBalance(userId);
        return ResponseEntity.ok(balance);
    }

    @PostMapping("/{userId}/deposit")
    public ResponseEntity<String> deposit(@PathVariable Long userId, @RequestParam Double amount) {
        billingService.deposit(userId, amount);
        return ResponseEntity.ok("Deposit successful");
    }

    @PostMapping("/{userId}/withdraw")
    public ResponseEntity<String> withdraw(@PathVariable Long userId, @RequestParam Double amount) {
        boolean success = billingService.withdraw(userId, amount);
        if (success) {
            return ResponseEntity.ok("Withdrawal successful");
        } else {
            return ResponseEntity.badRequest().body("Insufficient funds");
        }
    }
}

package com.otushomework.billingservice.controller;

import com.otushomework.billingservice.model.WithdrawRequest;
import com.otushomework.billingservice.service.BillingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/billing")
public class BillingController {

    private final BillingService billingService;

    public BillingController(BillingService billingService) {
        this.billingService = billingService;
    }

    @PostMapping
    public ResponseEntity<String> createAccount(@RequestParam long userId) {
        billingService.createAccount(userId);
        return ResponseEntity.ok("Account created");
    }

    @GetMapping("/{userId}/balance")
    public ResponseEntity<Double> getBalance(@PathVariable long userId) {
        Double balance = billingService.getBalance(userId);
        return ResponseEntity.ok(balance);
    }

    @PostMapping("/{userId}/deposit")
    public ResponseEntity<String> deposit(@PathVariable long userId, @RequestParam double amount) {
        System.out.println(amount + "add to deposit");
        billingService.deposit(userId, amount);
        return ResponseEntity.ok("Deposit successful");
    }

    @PostMapping("/withdraw")
    public ResponseEntity<String> withdraw(@RequestBody WithdrawRequest request) {
        boolean success = billingService.withdraw(request.getUserId(), request.getAmount());
        if (success) {
            return ResponseEntity.ok("Withdrawal successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Insufficient funds");
        }
    }
}

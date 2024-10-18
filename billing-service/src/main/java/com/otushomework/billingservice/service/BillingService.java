package com.otushomework.billingservice.service;

import com.otushomework.billingservice.model.Account;
import com.otushomework.billingservice.repository.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BillingService {

    private final AccountRepository accountRepository;

    public BillingService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account createAccount(Integer userId) {
        Account account = new Account(userId, 0.0);
        return accountRepository.save(account);
    }

    public Double getBalance(Long userId) {
        Account account = accountRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        return account.getBalance();
    }

    @Transactional
    public void deposit(Long userId, Double amount) {
        Account account = accountRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        account.setBalance(account.getBalance() + amount);
        accountRepository.save(account);
    }

    @Transactional
    public boolean withdraw(Long userId, Double amount) {
        Account account = accountRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        if (account.getBalance() >= amount) {
            account.setBalance(account.getBalance() - amount);
            accountRepository.save(account);
            return true;
        } else {
            return false;
        }
    }
}

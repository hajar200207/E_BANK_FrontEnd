package com.bank.solutions.controller;

import com.bank.solutions.model.Transaction;
import com.bank.solutions.model.TransactionType;
import com.bank.solutions.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/users/{userId}/accounts/{accountId}/transactions")
    public Transaction createTransaction(
            @PathVariable Long userId,
            @PathVariable Long accountId,
            @RequestParam Double amount,
            @RequestParam TransactionType type,
            @RequestParam String description) {

        return transactionService.createTransaction(accountId, amount, type, description);
    }
    @GetMapping("/users/{userId}/accounts/{accountId}/transactions")
    public List<Transaction> getAllTransactions(@PathVariable Long userId, @PathVariable Long accountId) {
        return transactionService.getAllTransactions(accountId);
    }
}

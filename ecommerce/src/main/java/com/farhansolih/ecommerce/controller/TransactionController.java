package com.farhansolih.ecommerce.controller;

import com.farhansolih.ecommerce.model.Transaction;
import com.farhansolih.ecommerce.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/checkout/{userId}")
    public Transaction checkout(@PathVariable UUID userId) {
        return transactionService.checkout(userId);
    }

    @GetMapping("/history/{userId}")
    public List<Transaction> getHistory(@PathVariable UUID userId) {
        return transactionService.getTransactionHistory(userId);
    }

    @GetMapping("/{transactionId}")
    public Transaction getTransactionDetail(@PathVariable UUID transactionId) {
        return transactionService.getTransactionDetails(transactionId);
    }
}
package com.farhansolih.ecommerce.service;

import com.farhansolih.ecommerce.model.*;
import com.farhansolih.ecommerce.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;

    public List<Transaction> getTransactionHistory(UUID userId) {
        return transactionRepository.findByUserIdOrderByTransactionDateDesc(userId);
    }

    public Transaction getTransactionDetails(UUID transactionId) {
        return transactionRepository.findById(transactionId).orElseThrow(() -> new ResourceAccessException("Transaksi tidak ditemukan"));
    }

    @Transactional
    public Transaction checkout(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceAccessException("User tidak ditemukan"));
        List<Cart> cartItems = cartRepository.findByUserId(userId);
        if (cartItems.isEmpty()) {
            throw new RuntimeException("Keranjang kosong, tidak bisa checkout.");
        }

        Transaction transaction = new Transaction();
        transaction.setId(UUID.randomUUID());
        transaction.setUser(user);
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setStatus(OrderStatus.DIPROSES); // Set status awal

        BigDecimal totalAmount = BigDecimal.ZERO;
        List<TransactionDetail> details = new ArrayList<>();

        for (Cart item : cartItems) {
            Product product = productRepository.findById(item.getProduct().getId()).orElseThrow(() -> new ResourceAccessException("Produk tidak ditemukan"));
            if (product.getStock() < item.getQuantity()) {
                throw new RuntimeException("Stok produk " + product.getName() + " tidak mencukupi.");
            }
            product.setStock(product.getStock() - item.getQuantity());
            productRepository.save(product);

            TransactionDetail detail = new TransactionDetail();
            detail.setId(UUID.randomUUID());
            detail.setProduct(product);
            detail.setQuantity(item.getQuantity());
            detail.setPrice(product.getPrice());
            detail.setTransaction(transaction);
            details.add(detail);
            totalAmount = totalAmount.add(product.getPrice().multiply(new BigDecimal(item.getQuantity())));
        }

        transaction.setDetails(details);
        transaction.setTotalAmount(totalAmount);
        Transaction savedTransaction = transactionRepository.save(transaction);
        cartRepository.deleteByUserId(userId);
        return savedTransaction;
    }
}
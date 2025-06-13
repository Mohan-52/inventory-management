package com.mohan.inventory_management.service;


import com.mohan.inventory_management.dto.TransactionRequest;
import com.mohan.inventory_management.dto.TransactionResponse;
import com.mohan.inventory_management.entity.Product;
import com.mohan.inventory_management.entity.Transaction;
import com.mohan.inventory_management.entity.User;
import com.mohan.inventory_management.repository.ProductRepository;
import com.mohan.inventory_management.repository.TransactionRepository;
import com.mohan.inventory_management.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Override
    public TransactionResponse recordTransaction(TransactionRequest request, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        int newQty = product.getQuantity() + request.getQuantityChange();
        if (newQty < 0) {
            throw new IllegalArgumentException("Insufficient stock.");
        }

        product.setQuantity(newQty);
        productRepository.save(product);

        Transaction tx = new Transaction();
        tx.setProduct(product);
        tx.setUser(user);
        tx.setQuantityChange(request.getQuantityChange());


        tx = transactionRepository.save(tx);
        return mapToResponse(tx);
    }

    @Override
    public List<TransactionResponse> getMyTransactionHistory(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        return transactionRepository.findByUserUserId(user.getUserId()).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private TransactionResponse mapToResponse(Transaction transaction) {
        TransactionResponse response = new TransactionResponse();
        response.setTransactionId(transaction.getTransactionId());
        response.setProductName(transaction.getProduct().getName());
        response.setQuantityChange(transaction.getQuantityChange());
        response.setTimestamp(transaction.getTimestamp().format(formatter));
        return response;
    }
}


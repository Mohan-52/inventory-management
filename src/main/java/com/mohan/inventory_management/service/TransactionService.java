package com.mohan.inventory_management.service;

import com.mohan.inventory_management.dto.TransactionRequest;
import com.mohan.inventory_management.dto.TransactionResponse;

import java.util.List;

public interface TransactionService {
    TransactionResponse recordTransaction(TransactionRequest request, String userEmail);
    List<TransactionResponse> getMyTransactionHistory(String userEmail);
}
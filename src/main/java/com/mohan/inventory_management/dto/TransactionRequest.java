package com.mohan.inventory_management.dto;


import lombok.Data;

@Data
public class TransactionRequest {
    private Long productId;

    // Positive for IN, negative for OUT
    private int quantityChange;
}
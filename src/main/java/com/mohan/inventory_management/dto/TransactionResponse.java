package com.mohan.inventory_management.dto;

import lombok.Data;

@Data
public class TransactionResponse {
    private Long transactionId;
    private String productName;
    private int quantityChange;
    private String timestamp;
}
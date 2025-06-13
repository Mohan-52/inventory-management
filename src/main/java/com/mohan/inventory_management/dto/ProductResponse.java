package com.mohan.inventory_management.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProductResponse {
    private Long productId;
    private String name;
    private int quantity;
    private Long createdBy;
    private LocalDateTime createdAt;
}
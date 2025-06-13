package com.mohan.inventory_management.dto;


import lombok.Data;

@Data
public class ProductRequest {
    private String name;
    private int quantity;
}
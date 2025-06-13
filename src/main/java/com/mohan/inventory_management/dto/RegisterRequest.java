package com.mohan.inventory_management.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String fullName;
    private String email;
    private String password;
    private String role;  // admin, manager, staff
}

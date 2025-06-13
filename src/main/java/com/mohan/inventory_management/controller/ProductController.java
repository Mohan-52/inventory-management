package com.mohan.inventory_management.controller;


import com.mohan.inventory_management.dto.ProductRequest;
import com.mohan.inventory_management.dto.ProductResponse;
import com.mohan.inventory_management.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")

public class ProductController {

    @Autowired
    private ProductService productService;

    // Create product (Admin, Manager)
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ProductResponse createProduct(@RequestBody  ProductRequest request,
                                         @AuthenticationPrincipal UserDetails userDetails) {
        return productService.createProduct(request, userDetails.getUsername());
    }

    // Update product (Admin or owning Manager)
    @PutMapping("/{productId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ProductResponse updateProduct(@PathVariable Long productId,
                                         @RequestBody  ProductRequest request,
                                         @AuthenticationPrincipal UserDetails userDetails) {
        return productService.updateProduct(productId, request, userDetails.getUsername());
    }

    // Delete product (Admin only)
    @DeleteMapping("/{productId}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteProduct(@PathVariable Long productId,
                              @AuthenticationPrincipal UserDetails userDetails) {
        productService.deleteProduct(productId, userDetails.getUsername());
    }

    // View all products (All roles)
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'STAFF')")
    public List<ProductResponse> getAllProducts() {
        return productService.getAllProducts();
    }
}

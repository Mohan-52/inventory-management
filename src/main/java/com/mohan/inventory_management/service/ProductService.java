package com.mohan.inventory_management.service;



import com.mohan.inventory_management.dto.ProductRequest;
import com.mohan.inventory_management.dto.ProductResponse;
import com.mohan.inventory_management.entity.Product;
import com.mohan.inventory_management.entity.User;
import com.mohan.inventory_management.repository.ProductRepository;
import com.mohan.inventory_management.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    @Autowired
    private  ProductRepository productRepository;

    @Autowired
    private  UserRepository userRepository;


    public ProductResponse createProduct(ProductRequest request, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Product product = new Product();
        product.setName(request.getName());
        product.setQuantity(request.getQuantity());
        product.setCreatedBy(user);


        Product saved = productRepository.save(product);
        return mapToResponse(saved);
    }

    public ProductResponse updateProduct(Long productId, ProductRequest request, String userEmail) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        // Only business logic: Controller ensures role-based access
        if (user.getRole().equals("manager") &&
                !product.getCreatedBy().getUserId().equals(user.getUserId())) {
            throw new IllegalArgumentException("Managers can only update their own products");
        }

        product.setName(request.getName());
        product.setQuantity(request.getQuantity());

        Product updated = productRepository.save(product);
        return mapToResponse(updated);
    }

    public void deleteProduct(Long productId, String userEmail) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        productRepository.delete(product);
    }

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private ProductResponse mapToResponse(Product product) {
        ProductResponse response = new ProductResponse();
        response.setProductId(product.getProductId());
        response.setName(product.getName());
        response.setQuantity(product.getQuantity());
        response.setCreatedBy(product.getCreatedBy().getUserId());

        response.setCreatedAt(product.getCreatedAt());

        return response;
    }
}

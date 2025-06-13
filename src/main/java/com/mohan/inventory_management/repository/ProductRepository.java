package com.mohan.inventory_management.repository;

import com.mohan.inventory_management.entity.Product;
import com.mohan.inventory_management.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCreatedBy(User user);
}

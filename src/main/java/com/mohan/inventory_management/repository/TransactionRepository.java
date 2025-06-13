package com.mohan.inventory_management.repository;

import com.mohan.inventory_management.entity.Transaction;
import com.mohan.inventory_management.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUserUserId(Long userId);
}
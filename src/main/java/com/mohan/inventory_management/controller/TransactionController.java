package com.mohan.inventory_management.controller;



import com.mohan.inventory_management.dto.TransactionRequest;
import com.mohan.inventory_management.dto.TransactionResponse;
import com.mohan.inventory_management.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")

public class TransactionController {

    @Autowired
    private TransactionService transactionService;

     //api/transactions
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public TransactionResponse recordTransaction(@RequestBody TransactionRequest request,
                                                 @AuthenticationPrincipal UserDetails userDetails) {
        return transactionService.recordTransaction(request, userDetails.getUsername());
    }

    //  GET /api/transactions/my-history
    @GetMapping("/my-history")
    @PreAuthorize("hasRole('STAFF')")
    public List<TransactionResponse> getMyHistory(@AuthenticationPrincipal UserDetails userDetails) {
        return transactionService.getMyTransactionHistory(userDetails.getUsername());
    }
}

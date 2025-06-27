package com.example.usercrud.controller;

import com.example.usercrud.Response.ApiResponse;
import com.example.usercrud.exception.UserNotFound;
import com.example.usercrud.model.Transaction;
import com.example.usercrud.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public ResponseEntity<ApiResponse<Transaction>> addTransaction(@Valid @RequestBody Transaction transaction) {
        Transaction saved = transactionService.addTransaction(transaction);
        return new ResponseEntity<>(new ApiResponse<>("Success", "Transaction added successfully", saved), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Transaction>>> getAll() {
        List<Transaction> list = transactionService.getAllTransaction();
        return new ResponseEntity<>(new ApiResponse<>("Success", "Fetched all transactions", list), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Transaction>> getById(@PathVariable Long id) throws UserNotFound {
        Transaction txn = transactionService.gettransactionById(id);
        return new ResponseEntity<>(new ApiResponse<>("Success", "Transaction fetched", txn), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable Long id) throws UserNotFound {
        transactionService.deleteTransaction(id);
        return new ResponseEntity<>(new ApiResponse<>("Success", "Transaction deleted successfully", null), HttpStatus.OK);
    }
}

package com.example.usercrud.controller;

import com.example.usercrud.Response.ApiResponse;
import com.example.usercrud.dto.TransferRequest;
import com.example.usercrud.dto.TransferResponse;
import com.example.usercrud.exception.UserNotFound;
import com.example.usercrud.model.Transaction;
import com.example.usercrud.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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

    @PostMapping("/transfer")
    public ResponseEntity<TransferResponse> transfer(@RequestBody TransferRequest request) {
        TransferResponse response = transactionService.transfer(
                request.getSourceAccountId(),
                request.getDestinationAccountId(),
                request.getAmount(),
                request.getCategoryId()
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Transaction>>> getAll() {
        List<Transaction> list = transactionService.getAllTransaction();
        return new ResponseEntity<>(new ApiResponse<>("Success", "Fetched all transactions", list), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Transaction>> getById(@PathVariable Long id) throws UserNotFound {
        Transaction txn = transactionService.getTransactionById(id);
        return new ResponseEntity<>(new ApiResponse<>("Success", "Transaction fetched", txn), HttpStatus.OK);
    }

    @GetMapping("/filter")
    public ResponseEntity<ApiResponse<List<Transaction>>> filterTransactions(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @RequestParam String category) {
        List<Transaction> filtered = transactionService.getFilteredTransactions(startDate, endDate, category);
        return new ResponseEntity<>(new ApiResponse<>("Success", "Transaction fetched", filtered), HttpStatus.OK);

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable Long id) throws UserNotFound {
        transactionService.deleteTransaction(id);
        return new ResponseEntity<>(new ApiResponse<>("Success", "Transaction deleted successfully", null), HttpStatus.OK);
    }
}

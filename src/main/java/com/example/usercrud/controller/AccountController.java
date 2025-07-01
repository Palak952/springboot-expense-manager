package com.example.usercrud.controller;

import com.example.usercrud.Response.ApiResponse;
import com.example.usercrud.exception.UserNotFound;
import com.example.usercrud.model.Account;
import com.example.usercrud.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;


    @PostMapping
    public ResponseEntity<ApiResponse<Account>> createAccount(@Valid @RequestBody Account account) {
        Account saved = accountService.addAccount(account);
        return new ResponseEntity<>(
                new ApiResponse<>("SUCCESS", "Account created successfully", saved),
                HttpStatus.CREATED
        );
    }


    @GetMapping
    public ResponseEntity<ApiResponse<List<Account>>> getAllAccounts() {
        List<Account> accounts = accountService.getAllAccounts();
        return new ResponseEntity<>(
                new ApiResponse<>("SUCCESS", "Fetched all accounts", accounts),
                HttpStatus.OK
        );
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Account>> getAccountById(@PathVariable Long id) throws UserNotFound {
        Account account = accountService.getAccountById(id);
        return new ResponseEntity<>(
                new ApiResponse<>("SUCCESS", "Fetched account by ID", account),
                HttpStatus.OK
        );
    }


    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Account>> updateAccount(
            @PathVariable Long id,
            @Valid @RequestBody Account updatedAccount
    ) throws UserNotFound {
        Account account = accountService.updateAccount(id, updatedAccount);
        return new ResponseEntity<>(
                new ApiResponse<>("SUCCESS", "Account updated successfully", account),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteAccount(@PathVariable Long id) throws UserNotFound {
        accountService.deleteAccount(id);
        return new ResponseEntity<>(
                new ApiResponse<>("SUCCESS", "Account deleted successfully", null),
                HttpStatus.OK
        );
    }
}


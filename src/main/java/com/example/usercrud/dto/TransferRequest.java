package com.example.usercrud.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferRequest {
    private Long sourceAccountId;
    private Long destinationAccountId;
    private Double amount;
    private Long categoryId;
}


package com.example.usercrud.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferResponse {
    private String message;
    private TransferDetails transferDetails;
    private AccountInfo sourceAccount;
    private AccountInfo destinationAccount;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TransferDetails {
        private Double amountTransferred;
        private LocalDateTime timestamp;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AccountInfo {
        private Long id;
        private String name;
        private Double oldBalance;
        private Double newBalance;

    }
}


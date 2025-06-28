package com.example.usercrud.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_trasaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_Id")
    private Long id;

    @NotNull(message = "Please Enter Amount ")
    @Positive(message = "Amount must be positive")
    @Column(name = "amount", nullable = false)
    private Double amount;

    @NotBlank(message = "Please Enter Category ")
    @Column(name = "category", nullable = false)
    private String category;

    @NotBlank(message = "Please Enter type (Income/Expense/Transfer)")
    @Column(name = "type", nullable = false)
    private String Type;

    @Column(name = "note")
    private String note;

    @Column(updatable = false)
    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    @PrePersist
    public void updateCreatedAt() {
        this.createAt = LocalDateTime.now();
    }

    public void updateUpdatedAt() {
        this.updateAt = LocalDateTime.now();
    }

}

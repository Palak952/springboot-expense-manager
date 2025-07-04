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
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_Id")
    private Long id;

    @NotNull(message = "Please Enter Amount ")
    @Positive(message = "Amount must be positive")
    @Column(name = "amount", nullable = false)
    private Double amount;

    @ManyToOne
    @NotNull(message = "Category must not be null")
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @NotBlank(message = "Please Enter type (Income/Expense/Transfer)")
    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "note")
    private String note;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    public void updateCreatedAt() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void updateUpdatedAt() {
        this.updatedAt = LocalDateTime.now();
    }

}

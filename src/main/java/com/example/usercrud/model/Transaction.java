package com.example.usercrud.model;

import jakarta.persistence.*;
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
    private Long id;

    private Double amount;
    private String category;
    private String Type;
    private String note;
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

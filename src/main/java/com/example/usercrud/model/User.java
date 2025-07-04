package com.example.usercrud.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users ")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long id;

    @NotBlank(message = "Name is required")
    @Pattern(regexp = "^[A-Za-z]+$", message = "name must be contain only letters")
    @Column(name = "full name")
    private String name;

    @Email(message = "please enter a valid email")
    @NotBlank(message = "Email is required")
    @Column(name = "user_email", nullable = false)
    private String email;

    @Column(name = "user_password", nullable = false)
    @NotBlank(message = "Password is required")
    private String password;

    @Column(name = "contact_number", nullable = false)
    @Pattern(regexp = "^[0-9]{10}$", message = "Contact number must be 10 digits")
    private String contactNumber;


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








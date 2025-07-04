package com.example.usercrud.repository;

import com.example.usercrud.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT t FROM Transaction t WHERE t.createdAt >= :startDate AND t.createdAt <= :endDate AND t.category.name = :category")
    List<Transaction> findByCreatedAtBetweenAndCategory_Name(
            @Param("startDate") LocalDateTime start,
            @Param("endDate") LocalDateTime end,
            @Param("category") String categoryName
    );

}



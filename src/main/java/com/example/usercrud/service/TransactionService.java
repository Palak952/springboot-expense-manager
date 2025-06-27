package com.example.usercrud.service;

import com.example.usercrud.exception.UserNotFound;
import com.example.usercrud.model.Transaction;
import com.example.usercrud.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    public Transaction addTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public List<Transaction> getAllTransaction() {
        return transactionRepository.findAll();
    }

    public Transaction gettransactionById(Long id) throws UserNotFound {
        return transactionRepository.findById(id).orElseThrow(() -> new UserNotFound("Transaction not found with ID:" + id));
    }

    public void deleteTransaction(Long id) throws UserNotFound {
        if (!transactionRepository.existsById(id)) {
            throw new UserNotFound("Transaction not found with ID: " + id);
        }
        transactionRepository.deleteById(id);
    }

}

package com.example.usercrud.service;

import com.example.usercrud.dto.TransferResponse;
import com.example.usercrud.exception.UserNotFound;
import com.example.usercrud.model.Account;
import com.example.usercrud.model.Category;
import com.example.usercrud.model.Transaction;
import com.example.usercrud.repository.AccountRepository;
import com.example.usercrud.repository.CategoryRepository;
import com.example.usercrud.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private AccountRepository accountRepository;

    public Transaction addTransaction(Transaction transaction) {

        Account account = accountRepository.findById(transaction.getAccount().getId())
                .orElseThrow(() -> new RuntimeException("Account not found"));
        Category category = categoryRepository.findById(transaction.getCategory().getId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        transaction.setAccount(account);
        transaction.setCategory(category);


        if ("Expense".equalsIgnoreCase(transaction.getType())) {
            if (account.getBalance() < transaction.getAmount()) {
                throw new RuntimeException("Insufficient funds in account");
            }

            account.setBalance(account.getBalance() - transaction.getAmount());
        } else if ("Income".equalsIgnoreCase(transaction.getType())) {
            account.setBalance(account.getBalance() + transaction.getAmount());
        }

        accountRepository.save(account);


        return transactionRepository.save(transaction);
    }

    public TransferResponse transfer(Long sourceAccountId, Long destinationAccountId, Double amount, Long categoryId) {
        Account source = accountRepository.findById(sourceAccountId)
                .orElseThrow(() -> new RuntimeException("Source account not found"));
        Account destination = accountRepository.findById(destinationAccountId)
                .orElseThrow(() -> new RuntimeException("Destination account not found"));
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        if (source.getBalance() < amount) {
            throw new RuntimeException("Insufficient funds in source account");
        }

        Double sourceOld = source.getBalance();
        Double destOld = destination.getBalance();

        source.setBalance(sourceOld - amount);
        destination.setBalance(destOld + amount);

        accountRepository.save(source);
        accountRepository.save(destination);

        Transaction debit = new Transaction();
        debit.setAccount(source);
        debit.setCategory(category);
        debit.setType("Expense");
        debit.setAmount(amount);
        debit.setCreatedAt(LocalDateTime.now());
        transactionRepository.save(debit);

        Transaction credit = new Transaction();
        credit.setAccount(destination);
        credit.setCategory(category);
        credit.setType("Income");
        credit.setAmount(amount);
        credit.setCreatedAt(LocalDateTime.now());
        transactionRepository.save(credit);

        TransferResponse response = new TransferResponse();
        response.setMessage("Transfer Successful");

        TransferResponse.TransferDetails details = new TransferResponse.TransferDetails();
        details.setAmountTransferred(amount);
        details.setTimestamp(LocalDateTime.now());
        response.setTransferDetails(details);

        TransferResponse.AccountInfo sourceInfo = new TransferResponse.AccountInfo();
        sourceInfo.setId(source.getId());
        sourceInfo.setName(source.getName());
        sourceInfo.setOldBalance(sourceOld);
        sourceInfo.setNewBalance(source.getBalance());

        TransferResponse.AccountInfo destInfo = new TransferResponse.AccountInfo();
        destInfo.setId(destination.getId());
        destInfo.setName(destination.getName());
        destInfo.setOldBalance(destOld);
        destInfo.setNewBalance(destination.getBalance());
        
        response.setSourceAccount(sourceInfo);
        response.setDestinationAccount(destInfo);

        return response;

    }


    public List<Transaction> getAllTransaction() {
        return transactionRepository.findAll();
    }


    public Transaction getTransactionById(Long id) throws UserNotFound {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new UserNotFound("Transaction not found with ID: " + id));
    }


    public void deleteTransaction(Long id) throws UserNotFound {
        if (!transactionRepository.existsById(id)) {
            throw new UserNotFound("Transaction not found with ID: " + id);
        }
        transactionRepository.deleteById(id);
    }

    public List<Transaction> getFilteredTransactions(LocalDateTime startDate, LocalDateTime endDate, String category) {
        return transactionRepository.findByCreatedAtBetweenAndCategory_Name(startDate, endDate, category);
    }

}


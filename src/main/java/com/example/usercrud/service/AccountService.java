package com.example.usercrud.service;

import com.example.usercrud.exception.UserNotFound;
import com.example.usercrud.model.Account;
import com.example.usercrud.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;


    public Account addAccount(Account account) {
        return accountRepository.save(account);
    }


    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }


    public Account getAccountById(Long id) throws UserNotFound {
        return accountRepository.findById(id)
                .orElseThrow(() -> new UserNotFound("Account not found with ID: " + id));
    }


    public Account updateAccount(Long id, Account updatedAccount) throws UserNotFound {
        Account account = getAccountById(id);
        account.setName(updatedAccount.getName());
        account.setType(updatedAccount.getType());
        account.setBalance(updatedAccount.getBalance());
        account.setDescription(updatedAccount.getDescription());
        return accountRepository.save(account);
    }

    public void deleteAccount(Long id) throws UserNotFound {
        if (!accountRepository.existsById(id)) {
            throw new UserNotFound("Account not found with ID: " + id);
        }
        accountRepository.deleteById(id);
    }
}

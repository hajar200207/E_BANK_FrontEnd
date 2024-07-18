package com.bank.solutions.service;



import com.bank.solutions.model.Account;
import com.bank.solutions.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Optional<Account> getAccountById(Long id) {
        return accountRepository.findById(id);
    }

    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    public Account updateAccount(Long id, Account updatedAccount) {
        if (accountRepository.existsById(id)) {
            updatedAccount.setAccountId(id);
            return accountRepository.save(updatedAccount);
        }
        return null;
    }

    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }

    public Account closeAccount(Long accountId, String reason) {
        Optional<Account> optionalAccount = accountRepository.findById(accountId);
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            account.closeAccount(reason);
            return accountRepository.save(account);
        }
        throw new RuntimeException("Account not found");
    }
}

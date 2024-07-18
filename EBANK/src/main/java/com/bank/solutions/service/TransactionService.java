package com.bank.solutions.service;

import com.bank.solutions.model.Account;
import com.bank.solutions.model.Transaction;
import com.bank.solutions.model.TransactionType;
import com.bank.solutions.repository.AccountRepository;
import com.bank.solutions.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    public Transaction createTransaction(Long accountId, Double amount, TransactionType type, String description) {
        Optional<Account> accountOptional = accountRepository.findById(accountId);
        if (!accountOptional.isPresent()) {
            throw new RuntimeException("Account not found");
        }

        Account account = accountOptional.get();

        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setAmount(amount);
        transaction.setType(type);
        transaction.setDescription(description);
        transaction.setDate(new Date());

        account.getTransactions().add(transaction);
        account.setBalance(account.getBalance() + (type == TransactionType.CREDIT ? amount : -amount));
        transactionRepository.save(transaction);
        return transaction;
    }

    public List<Transaction> getAllTransactions(Long accountId) {
        return transactionRepository.findByAccount_AccountId(accountId);
    }
}

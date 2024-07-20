package com.bank.solutions.repository;
import com.bank.solutions.model.Account;
import com.bank.solutions.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByAccount_AccountId(Long accountId);
}

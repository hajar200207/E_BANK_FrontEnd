package com.bank.solutions.model;
import  com.bank.solutions.model.Transaction;
import com.bank.solutions.model.Transaction;
import com.bank.solutions.model.User;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.mapping.List;

import java.util.ArrayList;
import java.util.Date;


@Data
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;
    private String type;
    private Double balance;
    @Temporal(TemporalType.DATE)
    private java.util.Date creationDate;
    private boolean closed = false;
    private String closureReason;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private java.util.List<Transaction> transactions;

    public Double getBalance() {
        return balance;
    }



    public void debit(Double amount, String description) {
        Transaction transaction = new Transaction();
        transaction.setAccount(this);
        transaction.setAmount(amount);
        transaction.setType(TransactionType.DEBIT);
        transaction.setDescription(description);
        transaction.setDate(new Date());
        transactions.add(transaction);
        balance -= amount;
    }

    public void credit(Double amount, String description) {
        Transaction transaction = new Transaction();
        transaction.setAccount(this);
        transaction.setAmount(amount);
        transaction.setType(TransactionType.CREDIT);
        transaction.setDescription(description);
        transaction.setDate(new Date());
        transactions.add(transaction);
        balance += amount;
    }
    public void closeAccount(String reason) {
        this.closed = true;
        this.closureReason = reason;
    }
}

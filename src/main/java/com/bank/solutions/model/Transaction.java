package com.bank.solutions.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    private Date date;

    private Double amount;
    @Enumerated(EnumType.STRING)
    private TransactionType type;

    private String description;
}

package com.bank.solutions.model;

import com.bank.solutions.model.BankCard;
import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
public class CreditCard extends BankCard {

    private String cardType;
    private double creditLimit;


}

package com.bank.solutions.model;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
public class DebitCard extends BankCard {
    public void specificDebitMethod() {

    }
}

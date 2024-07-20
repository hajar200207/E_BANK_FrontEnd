package com.bank.solutions.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
//@MappedSuperclass
@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class BankCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cardNumber;
    private Date expirationDate;
    private String type;
    private Boolean isActive;
    private Boolean isBlocked;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    public void activate() {
        this.isActive = true;
    }

    public void deactivate() {
        this.isActive = false;
    }

    public void block(String reason) {
        this.isActive = false;
        this.isBlocked = true;
    }

    public void unblock() {
        this.isActive = true;
        this.isBlocked = false;
    }
}

package com.bank.solutions.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Beneficiary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String phone;
    private Double money;

    @OneToOne
    private Account account;

    @ManyToOne
    private User user;
    @OneToOne
    private ExternalAccountDetails externalAccountDetails;
    public Beneficiary(String name, String email, String phone, Double money) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.money=money;
    }
}

package com.bank.solutions.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.assertj.core.api.recursive.assertion.RecursiveAssertionConfiguration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import lombok.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Builder
@AllArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String username;
    private String email;
    private String password;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Beneficiary> beneficiaries = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Account> accounts = new ArrayList<>();
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BankCard> bankCards;

    public Account createAccount(String type, Double initialBalance) {
        Account account = new Account();
        account.setType(type);
        account.setBalance(initialBalance);
        account.setCreationDate(new Date());
        this.accounts.add(account);
        account.setUser(this);
        return account;
    }

    public void addBeneficiary(Beneficiary beneficiary) {
        this.beneficiaries.add(beneficiary);
        beneficiary.setUser(this);
    }

    public void removeBeneficiary(Long beneficiaryId) {
        Beneficiary beneficiary = findBeneficiaryById(beneficiaryId);
        if (beneficiary != null) {
            this.beneficiaries.remove(beneficiary);
            beneficiary.setUser(null);
        }
    }

    public void transferMoney(Double amount, Long fromAccountId, Long toAccountId, String description) {
        Account fromAccount = findAccountById(fromAccountId);
        Account toAccount = findAccountById(toAccountId);

        if (fromAccount != null && toAccount != null) {
            fromAccount.debit(amount, description);
            toAccount.credit(amount, description);
        } else {
            throw new IllegalArgumentException("One or both accounts not found");
        }
    }

    public void transferMoneyExternal(Double amount, Long fromAccountId, ExternalAccountDetails toAccountDetails, String description) {
        Account fromAccount = findAccountById(fromAccountId);

        if (fromAccount != null) {
            // Implement external transfer logic here
            // This could involve validations and external API calls
        } else {
            throw new IllegalArgumentException("From account not found");
        }
    }

    private Account findAccountById(Long accountId) {
        return this.accounts.stream()
                .filter(account -> account.getAccountId().equals(accountId))
                .findFirst()
                .orElse(null);
    }

    private Beneficiary findBeneficiaryById(Long beneficiaryId) {
        return this.beneficiaries.stream()
                .filter(beneficiary -> beneficiary.getId().equals(beneficiaryId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

}

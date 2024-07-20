package com.bank.solutions.controller;
import com.bank.solutions.dto.AccountDTO;
import com.bank.solutions.dto.AccountMapper;
import com.bank.solutions.model.Account;
import com.bank.solutions.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping
    public List<Account> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable Long id) {
        Optional<Account> account = accountService.getAccountById(id);
        return account.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        Account createdAccount = accountService.createAccount(account);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAccount);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Account> updateAccount(@PathVariable Long id, @RequestBody Account updatedAccount) {
        Account account = accountService.updateAccount(id, updatedAccount);
        return account != null ? ResponseEntity.ok(account) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/create")
    public ResponseEntity<AccountDTO> createAccount(@RequestBody AccountDTO accountDTO) {
        Account account = AccountMapper.INSTANCE.accountDTOToAccount(accountDTO);
        Account createdAccount = accountService.createAccount(account);
        return new ResponseEntity<>(AccountMapper.INSTANCE.accountToAccountDTO(createdAccount), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<AccountDTO> updateAccount(@PathVariable Long id, @RequestBody AccountDTO accountDTO) {
        Account account = AccountMapper.INSTANCE.accountDTOToAccount(accountDTO);
        Account updatedAccount = accountService.updateAccount(id, account);
        return new ResponseEntity<>(AccountMapper.INSTANCE.accountToAccountDTO(updatedAccount), HttpStatus.OK);
    }

    @PostMapping("/close/{accountId}")
    public Account closeAccount(@PathVariable Long accountId, @RequestParam String reason) {
        return accountService.closeAccount(accountId, reason);
    }
}


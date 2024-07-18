package com.bank.solutions.controller;

import com.bank.solutions.Security.JwtAuth;
import com.bank.solutions.model.Account;
import com.bank.solutions.model.Beneficiary;
import com.bank.solutions.model.ExternalAccountDetails;
import com.bank.solutions.model.User;
import com.bank.solutions.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.findById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/user")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            User createdUser = userService.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating user: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        Optional<User> user = userService.findById(id);
        if (user.isPresent()) {
            User updatedUser = user.get();
            updatedUser.setUsername(userDetails.getUsername());
            updatedUser.setEmail(userDetails.getEmail());
            updatedUser.setPassword(userDetails.getPassword());
            userService.save(updatedUser);
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (userService.findById(id).isPresent()) {
            userService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{userId}/accounts")
    public ResponseEntity<Account> createAccountForUser(@PathVariable Long userId, @RequestBody Account accountRequest) {
        Account account = userService.createAccountForUser(userId, accountRequest.getType(), accountRequest.getBalance());
        if (account != null) {
            return ResponseEntity.ok(account);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{userId}/accounts")
    public ResponseEntity<List<Account>> getUserAccounts(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        List<Account> accounts = user.getAccounts();
        return ResponseEntity.ok(accounts);
    }

    @PostMapping("/{userId}/transfer")
    public ResponseEntity<String> transferMoney(
            @PathVariable Long userId,
            @RequestParam Double amount,
            @RequestParam Long fromAccountId,
            @RequestParam Long toAccountId,
            @RequestParam String description) {
        try {
            userService.transferMoney(userId, amount, fromAccountId, toAccountId, description);
            return ResponseEntity.ok("Money transferred successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/{userId}/transfer-external")
    public ResponseEntity<String> transferMoneyExternal(
            @PathVariable Long userId,
            @RequestParam Double amount,
            @RequestParam Long fromAccountId,
            @RequestBody ExternalAccountDetails toAccountDetails,
            @RequestParam String description) {
        try {
            userService.transferMoneyExternal(userId, amount, fromAccountId, toAccountDetails, description);
            return ResponseEntity.ok("Money transferred externally successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/{userId}/beneficiaries")
    public ResponseEntity<String> addBeneficiary(
            @PathVariable Long userId,
            @RequestBody Beneficiary beneficiary) {
        try {
            userService.addBeneficiary(userId, beneficiary);
            return ResponseEntity.ok("Beneficiary added successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{userId}/beneficiaries/{beneficiaryId}")
    public ResponseEntity<String> removeBeneficiary(
            @PathVariable Long userId,
            @PathVariable Long beneficiaryId) {
        try {
            userService.removeBeneficiary(userId, beneficiaryId);
            return ResponseEntity.ok("Beneficiary removed successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }
    @PostMapping("/{userId}/transfer-to-beneficiary")
    public ResponseEntity<String> transferMoneyToBeneficiary(
            @PathVariable Long userId,
            @RequestParam Double amount,
            @RequestParam Long fromAccountId,
            @RequestParam Long beneficiaryId,
            @RequestParam String description) {
        try {
            userService.transferMoneyToBeneficiary(userId, amount, fromAccountId, beneficiaryId, description);
            return ResponseEntity.ok("Money transferred to beneficiary successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
            );

            String token = JwtAuth.generateToken(user.getUsername());

            return ResponseEntity.ok(token);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username/password supplied");
        }
    }




}

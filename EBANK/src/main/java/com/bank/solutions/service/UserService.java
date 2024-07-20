package com.bank.solutions.service;




import com.bank.solutions.model.*;
import com.bank.solutions.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.bank.solutions.repository.AccountRepository;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    private  AccountRepository accountRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public User save(User user) {
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        return userRepository.save(user);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public Account createAccountForUser(Long userId, String type, Double initialBalance) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            Account account = user.createAccount(type, initialBalance);
            userRepository.save(user);
            return account;
        }
        return null;
    }
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public void transferMoney(Long userId, Double amount, Long fromAccountId, Long toAccountId, String description) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Account fromAccount = findAccountById(user, fromAccountId);
        Account toAccount = findAccountById(user, toAccountId);

        if (fromAccount != null && toAccount != null) {
            fromAccount.debit(amount, description);
            toAccount.credit(amount, description);
        } else {
            throw new IllegalArgumentException("One or both accounts not found");
        }
    }

    @Transactional
    public void transferMoneyExternal(Long userId, Double amount, Long fromAccountId, ExternalAccountDetails toAccountDetails, String description) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Account fromAccount = findAccountById(user, fromAccountId);

        if (fromAccount != null) {

        } else {
            throw new IllegalArgumentException("From account not found");
        }
    }

    @Transactional
    public void addBeneficiary(Long userId, Beneficiary beneficiary) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        user.addBeneficiary(beneficiary);
        userRepository.save(user);
    }

    @Transactional
    public void removeBeneficiary(Long userId, Long beneficiaryId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        user.removeBeneficiary(beneficiaryId);
        userRepository.save(user);
    }

    private Account findAccountById(User user, Long accountId) {
        return user.getAccounts().stream()
                .filter(account -> account.getAccountId().equals(accountId))
                .findFirst()
                .orElse(null);
    }
    @Transactional
    public void transferMoneyToBeneficiary(Long userId, Double amount, Long fromAccountId, Long beneficiaryId, String description) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Account fromAccount = findAccountById(user, fromAccountId);
        Beneficiary beneficiary = findBeneficiaryById(user, beneficiaryId);

        if (fromAccount != null && beneficiary != null) {
            fromAccount.debit(amount, description);
            beneficiary.getAccount().credit(amount, description);
        } else {
            throw new IllegalArgumentException("Account or Beneficiary not found");
        }
    }

    private Beneficiary findBeneficiaryById(User user, Long beneficiaryId) {
        return user.getBeneficiaries().stream()
                .filter(beneficiary -> beneficiary.getId().equals(beneficiaryId))
                .findFirst()
                .orElse(null);
    }

}

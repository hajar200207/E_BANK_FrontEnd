package com.bank.solutions.service;

import com.bank.solutions.model.BankCard;
import com.bank.solutions.model.CreditCard;
import com.bank.solutions.model.User;
import com.bank.solutions.repository.BankCardRepository;
import com.bank.solutions.repository.CreditCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BankCardService {

    @Autowired
    private BankCardRepository bankCardRepository;
@Autowired
private UserService userService;
    @Autowired
    private CreditCardRepository creditCardRepository;
    public BankCard findById(Long id) {
        Optional<BankCard> bankCard = bankCardRepository.findById(id);
        return bankCard.orElse(null);
    }

    public List<BankCard> findAll() {
        return bankCardRepository.findAll();
    }

    public BankCard save(BankCard bankCard) {
        return bankCardRepository.save(bankCard);
    }

    public void deleteById(Long id) {
        bankCardRepository.deleteById(id);
    }

    public void activateCard(Long id) {
        BankCard bankCard = findById(id);
        if (bankCard != null) {
            bankCard.activate();
            bankCardRepository.save(bankCard);
        }
    }

    public void deactivateCard(Long id) {
        BankCard bankCard = findById(id);
        if (bankCard != null) {
            bankCard.deactivate();
            bankCardRepository.save(bankCard);
        }
    }

    public void blockCard(Long id, String reason) {
        BankCard bankCard = findById(id);
        if (bankCard != null) {
            bankCard.block(reason);
            bankCardRepository.save(bankCard);
        }
    }

    public void unblockCard(Long id) {
        BankCard bankCard = findById(id);
        if (bankCard != null) {
            bankCard.unblock();
            bankCardRepository.save(bankCard);
        }
    }


    public BankCard saveBankCardWithUser(BankCard bankCard, Long userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User with id " + userId + " not found.");
        }


        bankCard.setUser(user);

        return bankCardRepository.save(bankCard);
    }

    public CreditCard saveCreditCard(CreditCard creditCard) {
        return creditCardRepository.save(creditCard);
    }
}

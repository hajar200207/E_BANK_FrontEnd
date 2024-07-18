package com.bank.solutions.service;

import com.bank.solutions.model.CreditCard;
import com.bank.solutions.repository.CreditCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CreditCardService {

    @Autowired
    private CreditCardRepository creditCardRepository;

    public CreditCard findById(Long id) {
        Optional<CreditCard> creditCard = creditCardRepository.findById(id);
        return creditCard.orElse(null);
    }

    public List<CreditCard> findAll() {
        return creditCardRepository.findAll();
    }

    public CreditCard save(CreditCard creditCard) {
        return creditCardRepository.save(creditCard);
    }

    public void deleteById(Long id) {
        creditCardRepository.deleteById(id);
    }

    public void activateCard(Long id) {
        CreditCard creditCard = findById(id);
        if (creditCard != null) {
            creditCard.activate();
            creditCardRepository.save(creditCard);
        }
    }

    public void deactivateCard(Long id) {
        CreditCard creditCard = findById(id);
        if (creditCard != null) {
            creditCard.deactivate();
            creditCardRepository.save(creditCard);
        }
    }

    public void blockCard(Long id, String reason) {
        CreditCard creditCard = findById(id);
        if (creditCard != null) {
            creditCard.block(reason);
            creditCardRepository.save(creditCard);
        }
    }

    public void unblockCard(Long id) {
        CreditCard creditCard = findById(id);
        if (creditCard != null) {
            creditCard.unblock();
            creditCardRepository.save(creditCard);
        }
    }
}

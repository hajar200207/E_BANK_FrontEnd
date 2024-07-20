package com.bank.solutions.service;

import com.bank.solutions.model.DebitCard;
import com.bank.solutions.repository.DebitCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DebitCardService {

    @Autowired
    private DebitCardRepository debitCardRepository;

    public DebitCard findById(Long id) {
        Optional<DebitCard> debitCard = debitCardRepository.findById(id);
        return debitCard.orElse(null);
    }

    public List<DebitCard> findAll() {
        return debitCardRepository.findAll();
    }

    public DebitCard save(DebitCard debitCard) {
        return debitCardRepository.save(debitCard);
    }

    public void deleteById(Long id) {
        debitCardRepository.deleteById(id);
    }

    public void activateCard(Long id) {
        DebitCard debitCard = findById(id);
        if (debitCard != null) {
            debitCard.activate();
            debitCardRepository.save(debitCard);
        }
    }

    public void deactivateCard(Long id) {
        DebitCard debitCard = findById(id);
        if (debitCard != null) {
            debitCard.deactivate();
            debitCardRepository.save(debitCard);
        }
    }

    public void blockCard(Long id, String reason) {
        DebitCard debitCard = findById(id);
        if (debitCard != null) {
            debitCard.block(reason);
            debitCardRepository.save(debitCard);
        }
    }

    public void unblockCard(Long id) {
        DebitCard debitCard = findById(id);
        if (debitCard != null) {
            debitCard.unblock();
            debitCardRepository.save(debitCard);
        }
    }
}

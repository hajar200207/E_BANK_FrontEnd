package com.bank.solutions.controller;

import com.bank.solutions.model.CreditCard;
import com.bank.solutions.service.CreditCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/credit-cards")
public class CreditCardController {

    @Autowired
    private CreditCardService creditCardService;

    @GetMapping("/{id}")
    public ResponseEntity<CreditCard> getCreditCard(@PathVariable Long id) {
        CreditCard creditCard = creditCardService.findById(id);
        return creditCard != null ? ResponseEntity.ok(creditCard) : ResponseEntity.notFound().build();
    }

    @GetMapping
    public List<CreditCard> getAllCreditCards() {
        return creditCardService.findAll();
    }

    @PostMapping
    public CreditCard createCreditCard(@RequestBody CreditCard creditCard) {
        return creditCardService.save(creditCard);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCreditCard(@PathVariable Long id) {
        creditCardService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/activate")
    public ResponseEntity<Void> activateCreditCard(@PathVariable Long id) {
        creditCardService.activateCard(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivateCreditCard(@PathVariable Long id) {
        creditCardService.deactivateCard(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/block")
    public ResponseEntity<Void> blockCreditCard(@PathVariable Long id, @RequestParam String reason) {
        creditCardService.blockCard(id, reason);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/unblock")
    public ResponseEntity<Void> unblockCreditCard(@PathVariable Long id) {
        creditCardService.unblockCard(id);
        return ResponseEntity.ok().build();
    }
}

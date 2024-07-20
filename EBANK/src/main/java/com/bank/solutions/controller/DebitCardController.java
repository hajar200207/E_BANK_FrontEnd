package com.bank.solutions.controller;

import com.bank.solutions.model.DebitCard;
import com.bank.solutions.service.DebitCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/debit-cards")
public class DebitCardController {

    @Autowired
    private DebitCardService debitCardService;

    @GetMapping("/{id}")
    public ResponseEntity<DebitCard> getDebitCard(@PathVariable Long id) {
        DebitCard debitCard = debitCardService.findById(id);
        return debitCard != null ? ResponseEntity.ok(debitCard) : ResponseEntity.notFound().build();
    }

    @GetMapping
    public List<DebitCard> getAllDebitCards() {
        return debitCardService.findAll();
    }

    @PostMapping
    public DebitCard createDebitCard(@RequestBody DebitCard debitCard) {
        return debitCardService.save(debitCard);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDebitCard(@PathVariable Long id) {
        debitCardService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/activate")
    public ResponseEntity<Void> activateDebitCard(@PathVariable Long id) {
        debitCardService.activateCard(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivateDebitCard(@PathVariable Long id) {
        debitCardService.deactivateCard(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/block")
    public ResponseEntity<Void> blockDebitCard(@PathVariable Long id, @RequestParam String reason) {
        debitCardService.blockCard(id, reason);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/unblock")
    public ResponseEntity<Void> unblockDebitCard(@PathVariable Long id) {
        debitCardService.unblockCard(id);
        return ResponseEntity.ok().build();
    }
}

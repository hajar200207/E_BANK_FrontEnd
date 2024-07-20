package com.bank.solutions.controller;

import com.bank.solutions.model.BankCard;
import com.bank.solutions.model.CreditCard;
import com.bank.solutions.model.DebitCard;
import com.bank.solutions.service.BankCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bank-cards")
public class BankCardController {

    @Autowired
    private BankCardService bankCardService;

    @GetMapping("/{id}")
    public ResponseEntity<BankCard> getBankCard(@PathVariable Long id) {
        BankCard bankCard = bankCardService.findById(id);
        return bankCard != null ? ResponseEntity.ok(bankCard) : ResponseEntity.notFound().build();
    }

    @GetMapping
    public List<BankCard> getAllBankCards() {
        return bankCardService.findAll();
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBankCard(@PathVariable Long id) {
        bankCardService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/activate")
    public ResponseEntity<Void> activateBankCard(@PathVariable Long id) {
        bankCardService.activateCard(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivateBankCard(@PathVariable Long id) {
        bankCardService.deactivateCard(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/block")
    public ResponseEntity<Void> blockBankCard(@PathVariable Long id, @RequestParam String reason) {
        bankCardService.blockCard(id, reason);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/unblock")
    public ResponseEntity<Void> unblockBankCard(@PathVariable Long id) {
        bankCardService.unblockCard(id);
        return ResponseEntity.ok().build();
    }
//    @PostMapping
//    public ResponseEntity<?> createBankCard(@RequestBody BankCard bankCard) {
//        BankCard savedCard = bankCardService.save(bankCard);
//
//        if (savedCard instanceof CreditCard) {
//            CreditCard savedCreditCard = (CreditCard) savedCard;
//            return ResponseEntity.ok(savedCreditCard);
//        } else if (savedCard instanceof DebitCard) {
//            DebitCard savedDebitCard = (DebitCard) savedCard;
//            return ResponseEntity.ok(savedDebitCard);
//        } else {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unknown card type");
//        }
//    }

}

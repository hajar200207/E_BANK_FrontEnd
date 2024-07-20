package com.bank.solutions.controller;

import com.bank.solutions.model.Beneficiary;
import com.bank.solutions.service.BeneficiaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/beneficiaries")
public class BeneficiaryController {

    private final BeneficiaryService beneficiaryService;

    @Autowired
    public BeneficiaryController(BeneficiaryService beneficiaryService) {
        this.beneficiaryService = beneficiaryService;
    }

    @GetMapping("/{beneficiaryId}")
    public ResponseEntity<Beneficiary> getBeneficiary(@PathVariable Long beneficiaryId) {
        Beneficiary beneficiary = beneficiaryService.getBeneficiaryById(beneficiaryId);
        if (beneficiary != null) {
            return ResponseEntity.ok(beneficiary);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}

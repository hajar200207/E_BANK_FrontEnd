package com.bank.solutions.service;

import com.bank.solutions.model.Beneficiary;
import com.bank.solutions.repository.BeneficiaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BeneficiaryService {

    @Autowired
    private BeneficiaryRepository beneficiaryRepository;

    public Beneficiary getBeneficiaryById(Long beneficiaryId) {
        return beneficiaryRepository.findById(beneficiaryId).orElse(null);
    }

    public Beneficiary saveBeneficiary(Beneficiary beneficiary) {
        return beneficiaryRepository.save(beneficiary);
    }

    public void deleteBeneficiary(Long beneficiaryId) {
        beneficiaryRepository.deleteById(beneficiaryId);
    }


}

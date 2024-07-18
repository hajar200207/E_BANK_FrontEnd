package com.bank.solutions.repository;

import com.bank.solutions.model.DebitCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DebitCardRepository extends JpaRepository<DebitCard, Long> {
}
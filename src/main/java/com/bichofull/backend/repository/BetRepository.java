package com.bichofull.backend.repository;

import com.bichofull.backend.model.Bet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BetRepository extends JpaRepository<Bet, Integer> {
}
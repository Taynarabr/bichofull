package com.bichofull.backend.repository;

import com.bichofull.backend.model.Bet;
import com.bichofull.backend.model.Bet.BetStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BetRepository extends JpaRepository<Bet, Long> {
    
    List<Bet> findByUserId(Long userId);  
    
    List<Bet> findByStatus(BetStatus status); 

    List<Bet> findByDrawId(Long drawId); 
    
    List<Bet> findByUserIdAndStatus(Long userId, BetStatus status);
    
    long countByStatus(BetStatus status);
}
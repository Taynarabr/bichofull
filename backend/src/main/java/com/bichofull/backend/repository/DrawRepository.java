package com.bichofull.backend.repository;

import com.bichofull.backend.model.Draw;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface DrawRepository extends JpaRepository<Draw, Long> {
    
    Optional<Draw> findFirstByOrderByDrawDateDesc(); 
    
    List<Draw> findByDrawDateBetween(LocalDateTime start, LocalDateTime end);  
    
    boolean existsByDrawDateBetween(LocalDateTime start, LocalDateTime end);
    
    List<Draw> findByDrawDateBetweenOrderByDrawDateDesc(LocalDateTime start, LocalDateTime end);
}
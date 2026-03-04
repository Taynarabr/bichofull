package com.bichofull.backend.repository;

import com.bichofull.backend.model.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Integer> { 
    Optional<Animal> findByNome(String nome);
    
}
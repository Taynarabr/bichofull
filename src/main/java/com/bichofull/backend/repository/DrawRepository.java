package com.bichofull.backend.repository;

import com.bichofull.backend.model.Draw;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DrawRepository extends JpaRepository<Draw, Integer> {
}
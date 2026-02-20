package com.bichofull.backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "draws")
public class Draw {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    // getters e setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
}
package com.bichofull.backend.model;

import jakarta.persistence.*;
import java.math.BigDecimal;


@Entity
@Table(name = "bets")
public class Bet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    // getters e setters (pode deixar vazio por enquanto)
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
}
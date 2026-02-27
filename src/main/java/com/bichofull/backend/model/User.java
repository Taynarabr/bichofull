package com.bichofull.backend.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

@Entity
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "name", nullable = false, length = 100)  // ← MAPEADO para coluna "name"
    private String nome;
    
    @Column(name = "email", nullable = false, unique = true, length = 100)  // ← MAPEADO para coluna "email"
    private String email;
    
    @Column(name = "password", nullable = false, length = 255)  // ← MAPEADO para coluna "password"
    private String senha;
    
    @Column(name = "balance", nullable = false, precision = 10, scale = 2)  // ← MAPEADO para coluna "balance"
    private BigDecimal saldo = new BigDecimal("1000.00");
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bet> bets = new ArrayList<>();
    
    // Construtor padrão
    public User() {}
    
    // Construtor com parâmetros
    public User(String nome, String email, String senha) {
        setNome(nome);
        setEmail(email);
        setSenha(senha);
        this.saldo = new BigDecimal("1000.00");
    }
    
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
    
    // ENCAPSULAMENTO - Métodos de negócio
    public boolean temSaldoSuficiente(BigDecimal valorAposta) {
        return this.saldo.compareTo(valorAposta) >= 0;
    }
    
    public void debitarSaldo(BigDecimal valor) {
        if (!temSaldoSuficiente(valor)) {
            throw new IllegalStateException(
                String.format("Saldo insuficiente. Disponível: R$ %.2f, Necessário: R$ %.2f", 
                saldo, valor)
            );
        }
        this.saldo = this.saldo.subtract(valor);
    }
    
    public void creditarSaldo(BigDecimal valor) {
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor para crédito deve ser positivo");
        }
        this.saldo = this.saldo.add(valor);
    }
    
    public void adicionarBet(Bet bet) {
        bets.add(bet);
        bet.setUser(this);
    }
    
    public void removerBet(Bet bet) {
        bets.remove(bet);
        bet.setUser(null);
    }
    
    // Getters e Setters com validação
    public Long getId() { return id; }
    
    public String getNome() { return nome; }
    
    public void setNome(String nome) {
        if (nome == null || nome.trim().length() < 3) {
            throw new IllegalArgumentException("Nome deve ter pelo menos 3 caracteres");
        }
        this.nome = nome.trim();
    }
    
    public String getEmail() { return email; }
    
    public void setEmail(String email) {
        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("Email inválido");
        }
        this.email = email.toLowerCase();
    }
    
    public String getSenha() { return senha; }
    
    public void setSenha(String senha) {
        if (senha == null || senha.length() < 6) {
            throw new IllegalArgumentException("Senha deve ter pelo menos 6 caracteres");
        }
        this.senha = senha;
    }
    
    public BigDecimal getSaldo() { return saldo; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    
    public List<Bet> getBets() { 
        return Collections.unmodifiableList(bets); 
    }
}
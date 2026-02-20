package com.bichofull.backend.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private BigDecimal balance;

    // 1. CONSTRUTOR PADRÃO
    protected User() {}

    // 2. CONSTRUTOR DE CRIAÇÃO
    public User(String name, String email, String password) {
        this.setName(name);
        this.setEmail(email);
        this.setPassword(password);
        this.balance = new BigDecimal("1000.00");
    }

    // GETTERS
    public Integer getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public BigDecimal getBalance() { return balance; }

    // SETTERS COM VALIDAÇÃO 
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome é obrigatório.");
        }
        this.name = name;
    }

    public void setEmail(String email) {
        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("E-mail inválido.");
        }
        this.email = email;
    }

    public void setPassword(String password) {
        // No futuro, aqui chamaremos o codificador de senhas
        if (password == null || password.length() < 6) {
            throw new IllegalArgumentException("A senha deve ter no mínimo 6 caracteres.");
        }
        this.password = password;
    }

    // MÉTODOS DE DOMÍNIO
    public void sacar(BigDecimal valor) {
        if (valor.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("Valor inválido.");
        if (this.balance.compareTo(valor) < 0) throw new RuntimeException("Saldo insuficiente.");
        this.balance = this.balance.subtract(valor);
    }

    public void depositar(BigDecimal valor) {
        if (valor.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("Valor inválido.");
        this.balance = this.balance.add(valor);
    }
}
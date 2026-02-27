package com.bichofull.backend.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;

public class UserResponseDTO {
    
    private Long id;
    private String nome;
    private String email;
    private BigDecimal saldo;
    
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime createdAt;
    
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime updatedAt;
    
    // Construtores
    public UserResponseDTO() {}
    
    public UserResponseDTO(Long id, String nome, String email, BigDecimal saldo, 
                          LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.saldo = saldo;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    
    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public BigDecimal getSaldo() { return saldo; }
    public void setSaldo(BigDecimal saldo) { this.saldo = saldo; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
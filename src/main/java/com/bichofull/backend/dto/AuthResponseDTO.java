package com.bichofull.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthResponseDTO {
    
    @JsonProperty("access_token")
    private String accessToken;
    
    @JsonProperty("token_type")
    private String tokenType = "Bearer";
    
    private Long id;
    private String nome;
    private String email;
    
    // Construtores
    public AuthResponseDTO() {}
    
    public AuthResponseDTO(String accessToken, Long id, String nome, String email) {
        this.accessToken = accessToken;
        this.id = id;
        this.nome = nome;
        this.email = email;
    }
    
    // Getters e Setters
    public String getAccessToken() { return accessToken; }
    public void setAccessToken(String accessToken) { this.accessToken = accessToken; }
    
    public String getTokenType() { return tokenType; }
    public void setTokenType(String tokenType) { this.tokenType = tokenType; }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
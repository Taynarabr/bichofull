package com.bichofull.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthResponseDTO {
    
    @JsonProperty("access_token")
    private String accessToken;
    
    @JsonProperty("token_type")
    private String tokenType = "Bearer";
    
    private Long id;
    private String name;  
    private String email;
    
    // Construtores
    public AuthResponseDTO() {}
    
    public AuthResponseDTO(String accessToken, Long id, String name, String email) {
        this.accessToken = accessToken;
        this.id = id;
        this.name = name;
        this.email = email;
    }
    
    // Getters e Setters
    public String getAccessToken() { return accessToken; }
    public void setAccessToken(String accessToken) { this.accessToken = accessToken; }
    
    public String getTokenType() { return tokenType; }
    public void setTokenType(String tokenType) { this.tokenType = tokenType; }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; } // Traduzido: getNome -> getName
    public void setName(String name) { this.name = name; } // Traduzido: setNome -> setName
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
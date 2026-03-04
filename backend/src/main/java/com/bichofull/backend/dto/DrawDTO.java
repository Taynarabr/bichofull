package com.bichofull.backend.dto;

import java.time.LocalDateTime;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DrawDTO {
    
    private Long id;
    
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime drawDate;
    
    @JsonProperty("milhar_1")
    private String milhar1;
    
    @JsonProperty("milhar_2")
    private String milhar2;
    
    @JsonProperty("milhar_3")
    private String milhar3;
    
    @JsonProperty("milhar_4")
    private String milhar4;
    
    @JsonProperty("milhar_5")
    private String milhar5;
    
    private List<String> allMilhares;
    private List<String> dezenas;
    
    // Construtores
    public DrawDTO() {}
    
    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public LocalDateTime getDrawDate() { return drawDate; }
    public void setDrawDate(LocalDateTime drawDate) { this.drawDate = drawDate; }
    
    public String getMilhar1() { return milhar1; }
    public void setMilhar1(String milhar1) { this.milhar1 = milhar1; }
    
    public String getMilhar2() { return milhar2; }
    public void setMilhar2(String milhar2) { this.milhar2 = milhar2; }
    
    public String getMilhar3() { return milhar3; }
    public void setMilhar3(String milhar3) { this.milhar3 = milhar3; }
    
    public String getMilhar4() { return milhar4; }
    public void setMilhar4(String milhar4) { this.milhar4 = milhar4; }
    
    public String getMilhar5() { return milhar5; }
    public void setMilhar5(String milhar5) { this.milhar5 = milhar5; }
    
    public List<String> getAllMilhares() { 
        return List.of(milhar1, milhar2, milhar3, milhar4, milhar5); 
    }
    
    public List<String> getDezenas() {
        return List.of(
            milhar1.substring(2, 4),
            milhar2.substring(2, 4),
            milhar3.substring(2, 4),
            milhar4.substring(2, 4),
            milhar5.substring(2, 4)
        );
    }
}
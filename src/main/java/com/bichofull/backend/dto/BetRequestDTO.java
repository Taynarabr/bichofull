package com.bichofull.backend.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Pattern;
import java.math.BigDecimal;

public class BetRequestDTO {
    
    @NotNull(message = "Tipo de aposta é obrigatório")
    @Pattern(regexp = "GRUPO|DEZENA|MILHAR", message = "Tipo deve ser GRUPO, DEZENA ou MILHAR")
    private String type;
    
    @NotNull(message = "Valor é obrigatório")
    @Positive(message = "Valor deve ser positivo")
    private BigDecimal value;
    
    @NotNull(message = "Escolha é obrigatória")
    private String choice;
    
    private Integer animalGroup; // Para aposta de GRUPO (1 a 25)
    
    // Construtores
    public BetRequestDTO() {}
    
    public BetRequestDTO(String type, BigDecimal value, String choice, Integer animalGroup) {
        this.type = type;
        this.value = value;
        this.choice = choice;
        this.animalGroup = animalGroup;
    }
    
    // Validação personalizada
    public boolean isValid() {
        if (type == null) return false;
        
        return switch (type) {
            case "GRUPO" -> 
                choice != null && choice.matches("\\d{1,2}") && 
                Integer.parseInt(choice) >= 1 && Integer.parseInt(choice) <= 25;
            case "DEZENA" -> 
                choice != null && choice.matches("\\d{2}");
            case "MILHAR" -> 
                choice != null && choice.matches("\\d{4}");
            default -> false;
        };
    }
    
    // Getters e Setters
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    
    public BigDecimal getValue() { return value; }
    public void setValue(BigDecimal value) { this.value = value; }
    
    public String getChoice() { return choice; }
    public void setChoice(String choice) { this.choice = choice; }
    
    public Integer getAnimalGroup() { return animalGroup; }
    public void setAnimalGroup(Integer animalGroup) { this.animalGroup = animalGroup; }
}
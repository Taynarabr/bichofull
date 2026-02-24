package com.bichofull.backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "draws")
public class Draw {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "draw_date", nullable = false)
    private LocalDateTime drawDate;
    
    @Column(name = "milhar_1", nullable = false, length = 4)
    private String milhar1;
    
    @Column(name = "milhar_2", nullable = false, length = 4)
    private String milhar2;
    
    @Column(name = "milhar_3", nullable = false, length = 4)
    private String milhar3;
    
    @Column(name = "milhar_4", nullable = false, length = 4)
    private String milhar4;
    
    @Column(name = "milhar_5", nullable = false, length = 4)
    private String milhar5;
    
    @OneToMany(mappedBy = "draw")
    private List<Bet> bets = new ArrayList<>();
    
    // Construtor padrão (JPA)
    protected Draw() {}
    
    // Construtor principal
    public Draw(String milhar1, String milhar2, String milhar3, String milhar4, String milhar5) {
        setMilhar1(milhar1);
        setMilhar2(milhar2);
        setMilhar3(milhar3);
        setMilhar4(milhar4);
        setMilhar5(milhar5);
        this.drawDate = LocalDateTime.now();
    }
    
    // ========== MÉTODOS DE NEGÓCIO ==========
    
    /**
     * Verifica se uma milhar foi premiada em determinada posição
     * @param milhar Milhar a verificar
     * @param position Posição (1 a 5)
     * @return true se a milhar foi sorteada na posição
     */
    public boolean isWinningMilhar(String milhar, int position) {
        if (position < 1 || position > 5) {
            throw new IllegalArgumentException("Posição deve ser entre 1 e 5");
        }
        
        String drawnMilhar = switch(position) {
            case 1 -> milhar1;
            case 2 -> milhar2;
            case 3 -> milhar3;
            case 4 -> milhar4;
            case 5 -> milhar5;
            default -> throw new IllegalArgumentException("Posição inválida");
        };
        
        return drawnMilhar.equals(milhar);
    }
    
    /**
     * Retorna a dezena (2 últimos dígitos) de uma posição específica
     * @param position Posição (1 a 5)
     * @return Dezena (ex: "34" da milhar "1234")
     */
    public String getDezenaByPosition(int position) {
        String milhar = switch(position) {
            case 1 -> milhar1;
            case 2 -> milhar2;
            case 3 -> milhar3;
            case 4 -> milhar4;
            case 5 -> milhar5;
            default -> throw new IllegalArgumentException("Posição inválida");
        };
        return milhar.substring(2, 4);
    }
    
    /**
     * Retorna todas as milhares sorteadas
     */
    public List<String> getAllMilhares() {
        return List.of(milhar1, milhar2, milhar3, milhar4, milhar5);
    }
    
    // ========== GETTERS E SETTERS ==========
    
    public Long getId() {
        return id;
    }
    
    public LocalDateTime getDrawDate() {
        return drawDate;
    }
    
    public String getMilhar1() {
        return milhar1;
    }
    
    public void setMilhar1(String milhar1) {
        validateMilhar(milhar1);
        this.milhar1 = milhar1;
    }
    
    public String getMilhar2() {
        return milhar2;
    }
    
    public void setMilhar2(String milhar2) {
        validateMilhar(milhar2);
        this.milhar2 = milhar2;
    }
    
    public String getMilhar3() {
        return milhar3;
    }
    
    public void setMilhar3(String milhar3) {
        validateMilhar(milhar3);
        this.milhar3 = milhar3;
    }
    
    public String getMilhar4() {
        return milhar4;
    }
    
    public void setMilhar4(String milhar4) {
        validateMilhar(milhar4);
        this.milhar4 = milhar4;
    }
    
    public String getMilhar5() {
        return milhar5;
    }
    
    public void setMilhar5(String milhar5) {
        validateMilhar(milhar5);
        this.milhar5 = milhar5;
    }
    
    public List<Bet> getBets() {
        return bets;
    }
    
    // Método privado de validação
    private void validateMilhar(String milhar) {
        if (milhar == null || !milhar.matches("\\d{4}")) {
            throw new IllegalArgumentException("Milhar deve ter exatamente 4 dígitos");
        }
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Draw draw = (Draw) o;
        return Objects.equals(id, draw.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
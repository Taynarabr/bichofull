package com.bichofull.backend.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "bets")
public class Bet {
    
    public enum BetType {
        GRUPO(18),
        DEZENA(60),
        MILHAR(4000);
        
        private final int multiplier;
        
        BetType(int multiplier) {
            this.multiplier = multiplier;
        }
        
        public int getMultiplier() {
            return multiplier;
        }
    }
    
    public enum BetStatus {
        PENDENTE,
        GANHOU,
        PERDEU
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "draw_id")
    private Draw draw;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "animal_id")
    private Animal animal;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private BetType type;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private BetStatus status = BetStatus.PENDENTE;
    
    @Column(name = "value", nullable = false, precision = 10, scale = 2)
    private BigDecimal value;
    
    @Column(name = "choice", nullable = false, length = 4)
    private String choice;
    
    @Column(name = "prize", precision = 10, scale = 2)
    private BigDecimal prize;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    public Bet() {
        this.status = BetStatus.PENDENTE;
    }
    
    public Bet(User user, BetType type, BigDecimal value, String choice) {
        setUser(user);
        setType(type);
        setValue(value);
        setChoice(choice);
        this.status = BetStatus.PENDENTE;
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
    
    // ========== MÉTODOS DE NEGÓCIO ==========
    
    public boolean processResult(Draw draw) {
        if (this.draw != null) {
            throw new IllegalStateException("Esta aposta já foi processada");
        }
        
        this.draw = draw;
        boolean won = checkVictory(draw);
        
        if (won) {
            this.status = BetStatus.GANHOU;
            this.prize = calculatePrize();
            this.user.creditarSaldo(this.prize); 
        } else {
            this.status = BetStatus.PERDEU;
            this.prize = BigDecimal.ZERO;
        }
        
        return won;
    }
    
    private boolean checkVictory(Draw draw) {
        return switch (this.type) {
            case GRUPO -> checkGrupo(draw);
            case DEZENA -> checkDezena(draw);
            case MILHAR -> checkMilhar(draw);
        };
    }
    
    private boolean checkGrupo(Draw draw) {
        if (animal == null) return false;
        String firstPrizeDezena = draw.getDezenaByPosition(1);
        return animal.contemDezena(firstPrizeDezena); 
    }
    
    private boolean checkDezena(Draw draw) {
        for (int i = 1; i <= 5; i++) {
            if (draw.getDezenaByPosition(i).equals(this.choice)) {
                return true;
            }
        }
        return false;
    }
    
    private boolean checkMilhar(Draw draw) {
        return draw.isWinningMilhar(this.choice, 1);
    }
    
    private BigDecimal calculatePrize() {
        return this.value.multiply(new BigDecimal(this.type.getMultiplier()));
    }
    
    public void cancel() {
        if (this.status != BetStatus.PENDENTE) {
            throw new IllegalStateException("Apenas apostas pendentes podem ser canceladas");
        }
        this.status = BetStatus.PERDEU;
        this.user.creditarSaldo(this.value); 
    }
    
    // ========== GETTERS E SETTERS ==========
    
    public Long getId() { return id; }
    
    public User getUser() { return user; }
    
    public void setUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("Usuário é obrigatório");
        }
        this.user = user;
    }
    
    public Draw getDraw() { return draw; }
    
    public void setDraw(Draw draw) {
        if (this.draw != null) {
            throw new IllegalStateException("Sorteio já vinculado a esta aposta");
        }
        this.draw = draw;
    }
    
    public Animal getAnimal() { return animal; }
    
    public void setAnimal(Animal animal) {
        if (type == BetType.GRUPO && animal == null) {
            throw new IllegalArgumentException("Aposta de grupo requer um animal");
        }
        this.animal = animal;
        if (animal != null) {
            this.choice = animal.getGrupoFormatado();
        }
    }
    
    public BetType getType() { return type; }
    
    public void setType(BetType type) {
        if (type == null) {
            throw new IllegalArgumentException("Tipo de aposta é obrigatório");
        }
        this.type = type;
    }
    
    public BetStatus getStatus() { return status; }
    
    public BigDecimal getValue() { return value; }
    
    public void setValue(BigDecimal value) {
        if (value == null || value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor da aposta deve ser positivo");
        }
        this.value = value;
    }
    
    public String getChoice() { return choice; }
    
    public void setChoice(String choice) {
        if (choice == null) {
            throw new IllegalArgumentException("Escolha é obrigatória");
        }
        
        if (type != null) {
            switch (type) {
                case GRUPO:
                    if (!choice.matches("\\d{1,2}")) {
                        throw new IllegalArgumentException("Grupo deve ser um número de 1 a 25");
                    }
                    break;
                case DEZENA:
                    if (!choice.matches("\\d{2}")) {
                        throw new IllegalArgumentException("Dezena deve ter 2 dígitos");
                    }
                    break;
                case MILHAR:
                    if (!choice.matches("\\d{4}")) {
                        throw new IllegalArgumentException("Milhar deve ter 4 dígitos");
                    }
                    break;
            }
        }
        
        this.choice = choice;
    }
    
    public BigDecimal getPrize() { return prize; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bet bet = (Bet) o;
        return Objects.equals(id, bet.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void setStatus(BetStatus pendente) {
        throw new UnsupportedOperationException("Unimplemented method 'setStatus'");
    }

    public void setPrize(BigDecimal prize) {
    this.prize = prize;
    }

}
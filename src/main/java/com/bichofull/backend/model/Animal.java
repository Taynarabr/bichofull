package com.bichofull.backend.model;

import jakarta.persistence.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "animais")
public class Animal {
    
    @Id
    private Integer grupo; // 1 a 25 (chave primária)
    
    @Column(nullable = false, length = 50)
    private String nome;
    
    @Column(nullable = false, length = 20)
    private String dezenas; // "01,02,03,04"
    
    // Construtor padrão (JPA)
    protected Animal() {}
    
    // Construtor com parâmetros (ABSTRAÇÃO)
    public Animal(Integer grupo, String nome, String dezenas) {
        setGrupo(grupo);
        setNome(nome);
        setDezenas(dezenas);
    }
    
    // ENCAPSULAMENTO - Métodos de negócio
    public boolean contemDezena(String dezena) {
        validarDezena(dezena);
        return getListaDezenas().contains(dezena);
    }
    
    public boolean contemMilhar(String milhar) {
        if (milhar == null || milhar.length() != 4) return false;
        String dezena = milhar.substring(2, 4); // Últimos 2 dígitos
        return contemDezena(dezena);
    }
    
    public List<String> getListaDezenas() {
        return Arrays.asList(dezenas.split(","));
    }
    
    public String getPrimeiraDezena() {
        return dezenas.split(",")[0];
    }
    
    public String getUltimaDezena() {
        String[] array = dezenas.split(",");
        return array[array.length - 1];
    }
    
    public String getGrupoFormatado() {
        return String.format("%02d", grupo);
    }
    
    // Métodos privados de validação
    private void validarDezena(String dezena) {
        if (dezena == null || !dezena.matches("\\d{2}")) {
            throw new IllegalArgumentException("Dezena deve ter 2 dígitos");
        }
    }
    
    // Getters e Setters com validação
    public Integer getGrupo() { return grupo; }
    
    public void setGrupo(Integer grupo) {
        if (grupo == null || grupo < 1 || grupo > 25) {
            throw new IllegalArgumentException("Grupo deve estar entre 1 e 25");
        }
        this.grupo = grupo;
    }
    
    public String getNome() { return nome; }
    
    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do animal é obrigatório");
        }
        this.nome = nome.trim();
    }
    
    public String getDezenas() { return dezenas; }
    
    public void setDezenas(String dezenas) {
        if (dezenas == null || !dezenas.matches("\\d{2},\\d{2},\\d{2},\\d{2}")) {
            throw new IllegalArgumentException("Dezenas devem estar no formato: 01,02,03,04");
        }
        this.dezenas = dezenas;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return Objects.equals(grupo, animal.grupo);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(grupo);
    }
    
    @Override
    public String toString() {
        return String.format("Animal{grupo=%02d, nome='%s'}", grupo, nome);
    }
}
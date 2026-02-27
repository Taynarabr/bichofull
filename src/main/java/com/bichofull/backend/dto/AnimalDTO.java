package com.bichofull.backend.dto;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AnimalDTO {
    
    private Integer grupo;
    private String nome;
    private String dezenas;
    
    @JsonProperty("dezenas_list")
    private List<String> dezenasList;
    
    @JsonProperty("grupo_formatado")
    private String grupoFormatado;
    
    // Construtores
    public AnimalDTO() {}
    
    public AnimalDTO(Integer grupo, String nome, String dezenas) {
        this.grupo = grupo;
        this.nome = nome;
        this.dezenas = dezenas;
        this.grupoFormatado = String.format("%02d", grupo);
        this.dezenasList = List.of(dezenas.split(","));
    }
    
    // Getters e Setters
    public Integer getGrupo() { return grupo; }
    public void setGrupo(Integer grupo) { 
        this.grupo = grupo;
        if (grupo != null) {
            this.grupoFormatado = String.format("%02d", grupo);
        }
    }
    
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    public String getDezenas() { return dezenas; }
    public void setDezenas(String dezenas) { 
        this.dezenas = dezenas;
        if (dezenas != null) {
            this.dezenasList = List.of(dezenas.split(","));
        }
    }
    
    public List<String> getDezenasList() { 
        return dezenasList;
    }
    
    public String getGrupoFormatado() { 
        return grupoFormatado;
    }
}
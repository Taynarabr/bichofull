package com.bichofull.backend.service;

import com.bichofull.backend.dto.AnimalDTO;
import com.bichofull.backend.model.Animal;
import com.bichofull.backend.repository.AnimalRepository;
import com.bichofull.backend.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnimalService {
    
    private final AnimalRepository animalRepository;
    
    public AnimalService(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }
    
    public List<AnimalDTO> findAll() {
        return animalRepository.findAll().stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }
    
    public AnimalDTO findByGrupo(Integer grupo) {
        Animal animal = animalRepository.findById(grupo)
            .orElseThrow(() -> new ResourceNotFoundException("Animal não encontrado para o grupo: " + grupo));
        return toDTO(animal);
    }
    
    public AnimalDTO findByDezena(String dezena) {
        // Validar formato da dezena
        if (dezena == null || !dezena.matches("\\d{2}")) {
            throw new IllegalArgumentException("Dezena deve ter 2 dígitos");
        }
        
        // Busca todos animais e verifica qual contém a dezena
        Animal animal = animalRepository.findAll().stream()
            .filter(a -> a.contemDezena(dezena))
            .findFirst()
            .orElseThrow(() -> new ResourceNotFoundException("Nenhum animal encontrado para a dezena: " + dezena));
        
        return toDTO(animal);
    }
    
    public Animal findAnimalEntityByGrupo(Integer grupo) {
        return animalRepository.findById(grupo)
            .orElseThrow(() -> new ResourceNotFoundException("Animal não encontrado para o grupo: " + grupo));
    }
    
    public boolean isValidGrupo(Integer grupo) {
        return grupo != null && grupo >= 1 && grupo <= 25;
    }
    
    public boolean isValidDezena(String dezena) {
        if (dezena == null || !dezena.matches("\\d{2}")) {
            return false;
        }
        return animalRepository.findAll().stream()
            .anyMatch(a -> a.contemDezena(dezena));
    }
    
    private AnimalDTO toDTO(Animal animal) {
        return new AnimalDTO(
            animal.getGrupo(),
            animal.getNome(),
            animal.getDezenas()
        );
    }
}
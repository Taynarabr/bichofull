package com.bichofull.backend.controller;

import com.bichofull.backend.dto.AnimalDTO;
import com.bichofull.backend.service.AnimalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/animals")
@CrossOrigin(origins = "*")
public class AnimalController {

    private final AnimalService animalService;

    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }

    @GetMapping
    public ResponseEntity<List<AnimalDTO>> findAll() {
        List<AnimalDTO> animals = animalService.findAll();
        return ResponseEntity.ok(animals);
    }

    @GetMapping("/{grupo}")
    public ResponseEntity<AnimalDTO> findByGrupo(@PathVariable Integer grupo) {
        AnimalDTO animal = animalService.findByGrupo(grupo);
        return ResponseEntity.ok(animal);
    }

    @GetMapping("/dezena/{dezena}")
    public ResponseEntity<AnimalDTO> findByDezena(@PathVariable String dezena) {
        AnimalDTO animal = animalService.findByDezena(dezena);
        return ResponseEntity.ok(animal);
    }
}
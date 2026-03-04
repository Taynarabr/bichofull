package com.bichofull.backend.controller;

import com.bichofull.backend.dto.DrawDTO;
import com.bichofull.backend.service.DrawService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/draws")
@CrossOrigin(origins = "*")
public class DrawController {

    private final DrawService drawService;

    public DrawController(DrawService drawService) {
        this.drawService = drawService;
    }

    @PostMapping("/generate")
    public ResponseEntity<DrawDTO> generateDraw() {
        DrawDTO draw = drawService.generateDraw();
        return ResponseEntity.ok(draw);
    }

    @GetMapping
    public ResponseEntity<List<DrawDTO>> findAll() {
        List<DrawDTO> draws = drawService.findAll();
        return ResponseEntity.ok(draws);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DrawDTO> findById(@PathVariable Long id) {
        DrawDTO draw = drawService.findById(id);
        return ResponseEntity.ok(draw);
    }

    @GetMapping("/last")
    public ResponseEntity<DrawDTO> findLastDraw() {
        DrawDTO draw = drawService.findLastDraw();
        return ResponseEntity.ok(draw);
    }
}
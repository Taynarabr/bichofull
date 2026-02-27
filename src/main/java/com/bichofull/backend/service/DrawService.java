package com.bichofull.backend.service;

import com.bichofull.backend.dto.DrawDTO;
import com.bichofull.backend.model.Draw;
import com.bichofull.backend.repository.DrawRepository;
import com.bichofull.backend.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@Transactional
public class DrawService {
    
    private final DrawRepository drawRepository;
    private final Random random = new Random();
    
    public DrawService(DrawRepository drawRepository) {
        this.drawRepository = drawRepository;
    }
    
    public DrawDTO generateDraw() {
        Draw draw = new Draw(
            generateRandomMilhar(),
            generateRandomMilhar(),
            generateRandomMilhar(),
            generateRandomMilhar(),
            generateRandomMilhar()
        );
        
        Draw savedDraw = drawRepository.save(draw);
        return toDTO(savedDraw);
    }
    
    public DrawDTO findById(Long id) {
        Draw draw = drawRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Sorteio não encontrado com id: " + id));
        return toDTO(draw);
    }
    
    public Draw findDrawEntityById(Long id) {
        return drawRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Sorteio não encontrado com id: " + id));
    }
    
    public DrawDTO findLastDraw() {
        Draw draw = drawRepository.findFirstByOrderByDrawDateDesc()
            .orElseThrow(() -> new ResourceNotFoundException("Nenhum sorteio encontrado"));
        return toDTO(draw);
    }
    
    public List<DrawDTO> findAll() {
        return drawRepository.findAll().stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }
    
    public List<DrawDTO> findDrawsByDateRange(LocalDateTime start, LocalDateTime end) {
        return drawRepository.findByDrawDateBetween(start, end).stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }
    
    public boolean hasDrawToday() {
        LocalDateTime startOfDay = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
        LocalDateTime endOfDay = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59);
        return drawRepository.existsByDrawDateBetween(startOfDay, endOfDay);
    }
    
    private String generateRandomMilhar() {
        return String.format("%04d", random.nextInt(10000));
    }
    
    private DrawDTO toDTO(Draw draw) {
        DrawDTO dto = new DrawDTO();
        dto.setId(draw.getId());
        dto.setDrawDate(draw.getDrawDate());
        dto.setMilhar1(draw.getMilhar1());
        dto.setMilhar2(draw.getMilhar2());
        dto.setMilhar3(draw.getMilhar3());
        dto.setMilhar4(draw.getMilhar4());
        dto.setMilhar5(draw.getMilhar5());
        return dto;
    }
}
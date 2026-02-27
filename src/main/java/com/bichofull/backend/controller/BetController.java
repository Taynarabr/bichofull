package com.bichofull.backend.controller;

import com.bichofull.backend.dto.BetRequestDTO;
import com.bichofull.backend.dto.BetResponseDTO;
import com.bichofull.backend.service.BetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/bets")
@CrossOrigin(origins = "*")
public class BetController {

    private final BetService betService;

    public BetController(BetService betService) {
        this.betService = betService;
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<BetResponseDTO> placeBet(
            @PathVariable Long userId,
            @Valid @RequestBody BetRequestDTO request) {
        BetResponseDTO response = betService.placeBet(userId, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BetResponseDTO>> getUserBets(@PathVariable Long userId) {
        List<BetResponseDTO> bets = betService.getUserBets(userId);
        return ResponseEntity.ok(bets);
    }

    @GetMapping("/{betId}")
    public ResponseEntity<BetResponseDTO> findById(@PathVariable Long betId) {
        BetResponseDTO bet = betService.findById(betId);
        return ResponseEntity.ok(bet);
    }

    @PostMapping("/{betId}/process/{drawId}")
    public ResponseEntity<BetResponseDTO> processBet(
            @PathVariable Long betId,
            @PathVariable Long drawId) {
        BetResponseDTO response = betService.processBet(betId, drawId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<BetResponseDTO>> findByStatus(@PathVariable String status) {
        List<BetResponseDTO> bets = betService.findByStatus(status);
        return ResponseEntity.ok(bets);
    }
}
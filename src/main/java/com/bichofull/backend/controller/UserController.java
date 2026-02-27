package com.bichofull.backend.controller;

import com.bichofull.backend.dto.UserRequestDTO;
import com.bichofull.backend.dto.UserResponseDTO;
import com.bichofull.backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> findAll() {
        List<UserResponseDTO> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> findById(@PathVariable Long id) {
        UserResponseDTO user = userService.findById(id);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody UserRequestDTO request) {
        UserResponseDTO updated = userService.update(id, request);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/balance")
    public ResponseEntity<BigDecimal> getBalance(@PathVariable Long id) {
        BigDecimal balance = userService.getBalance(id);
        return ResponseEntity.ok(balance);
    }
}
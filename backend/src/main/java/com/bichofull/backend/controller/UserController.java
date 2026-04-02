package com.bichofull.backend.controller;

import com.bichofull.backend.dto.AuthResponseDTO;
import com.bichofull.backend.dto.UserLoginDTO;
import com.bichofull.backend.dto.UserRequestDTO;
import com.bichofull.backend.dto.UserResponseDTO;
import com.bichofull.backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> register(@Valid @RequestBody UserRequestDTO request) {
        UserResponseDTO created = userService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> findAll() {
        List<UserResponseDTO> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody UserLoginDTO loginDTO) {
        AuthResponseDTO response = userService.login(loginDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> findById(@PathVariable Long id) {
        UserResponseDTO user = userService.findById(id);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> update(
            @PathVariable Long id,
            @RequestBody UserRequestDTO request) {
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

    @PostMapping("/{id}/deposit")
    public ResponseEntity<UserResponseDTO> deposit(@PathVariable Long id, @RequestParam BigDecimal amount) {
        UserResponseDTO updatedUser = userService.deposit(id, amount);
        return ResponseEntity.ok(updatedUser);
    }

    @PostMapping("/{id}/withdraw")
    public ResponseEntity<UserResponseDTO> withdraw(@PathVariable Long id, @RequestParam BigDecimal amount) {
        UserResponseDTO updatedUser = userService.withdraw(id, amount);
        return ResponseEntity.ok(updatedUser);
    }

    // Rota de Reset de Senha corrigida para retornar JSON
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam String email, @RequestParam String newPassword) {
        try {
            userService.resetPassword(email, newPassword);
            // Retornamos um objeto JSON com a chave "message"
            return ResponseEntity.ok(Map.of("message", "Senha alterada com sucesso!"));
        } catch (Exception e) {
            // Retornamos um erro 400 com a mensagem da exceção em formato JSON
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body(Map.of("message", e.getMessage()));
        }
    }
}
package com.bichofull.backend.controller;

import com.bichofull.backend.dto.UserLoginDTO;
import com.bichofull.backend.dto.UserRequestDTO;
import com.bichofull.backend.dto.UserResponseDTO;
import com.bichofull.backend.dto.AuthResponseDTO;
import com.bichofull.backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*") // Permitir requisições do frontend
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@Valid @RequestBody UserRequestDTO request) {
        UserResponseDTO response = userService.register(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody UserLoginDTO loginDTO) {
        AuthResponseDTO response = userService.login(loginDTO);
        return ResponseEntity.ok(response);
    }
}
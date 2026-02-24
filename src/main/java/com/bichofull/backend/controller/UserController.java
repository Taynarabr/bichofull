package com.bichofull.backend.controller;

import com.bichofull.backend.dto.UserDTO;
import com.bichofull.backend.model.User;
import com.bichofull.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    // Método para CADASTRAR (POST)
    @PostMapping("/register")
    public ResponseEntity<User> cadastrar(@RequestBody UserDTO dados) {
        // O @RequestBody transforma o JSON que vem do Angular no seu UserDTO
        User novoUsuario = userService.cadastrar(dados);
        
        // Retorna o usuário criado com o status 200 (OK)
        return ResponseEntity.ok(novoUsuario);
    }
}
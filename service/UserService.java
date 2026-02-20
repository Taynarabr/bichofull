package com.bichofull.backend.service;

import com.bichofull.backend.model.User;
import com.bichofull.backend.dto.UserDTO;
import com.bichofull.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired 
    private UserRepository userRepository;

    public User cadastrar(UserDTO dados) {
        User novoUsuario = new User(dados.name(), dados.email(), dados.password());

        return userRepository.save(novoUsuario);
    }
}
package com.bichofull.backend.service;

import com.bichofull.backend.dto.UserRequestDTO;
import com.bichofull.backend.dto.UserResponseDTO;
import com.bichofull.backend.dto.UserLoginDTO;
import com.bichofull.backend.dto.AuthResponseDTO;
import com.bichofull.backend.model.User;
import com.bichofull.backend.repository.UserRepository;
import com.bichofull.backend.exception.InsufficientBalanceException;
import com.bichofull.backend.exception.ResourceNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    // ===== REGISTRO E AUTENTICAÇÃO =====
    public UserResponseDTO register(UserRequestDTO request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email já cadastrado");
        }
        
        User user = new User();
        user.setNome(request.getNome());
        user.setEmail(request.getEmail());
        user.setSenha(passwordEncoder.encode(request.getSenha()));
        
        User savedUser = userRepository.save(user);
        return toDTO(savedUser);
    }
    
    public AuthResponseDTO login(UserLoginDTO loginDTO) {
        User user = userRepository.findByEmail(loginDTO.getEmail())
            .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
        
        if (!passwordEncoder.matches(loginDTO.getSenha(), user.getSenha())) {
            throw new RuntimeException("Senha inválida");
        }
        
        // Versão simplificada - em produção use JWT de verdade
        String token = "simulated-jwt-token-" + user.getId() + "-" + System.currentTimeMillis();
        
        return new AuthResponseDTO(token, user.getId(), user.getNome(), user.getEmail());
    }
    
    // ===== OPERAÇÕES DE SALDO =====
    public void debitBalance(Long userId, BigDecimal amount) {
        User user = findUserById(userId);
        user.debitarSaldo(amount);
        userRepository.save(user);
    }
    
    public void creditBalance(Long userId, BigDecimal amount) {
        User user = findUserById(userId);
        user.creditarSaldo(amount);
        userRepository.save(user);
    }
    
    public boolean hasSufficientBalance(Long userId, BigDecimal amount) {
        User user = findUserById(userId);
        return user.temSaldoSuficiente(amount);
    }
    
    public BigDecimal getBalance(Long userId) {
        User user = findUserById(userId);
        return user.getSaldo();
    }
    
    // ===== CRUD BÁSICO =====
    public UserResponseDTO findById(Long id) {
        return toDTO(findUserById(id));
    }
    
    public List<UserResponseDTO> findAll() {
        return userRepository.findAll().stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }
    
    public UserResponseDTO update(Long id, UserRequestDTO request) {
        User user = findUserById(id);
        
        if (request.getNome() != null && !request.getNome().isEmpty()) {
            user.setNome(request.getNome());
        }
        if (request.getEmail() != null && !request.getEmail().isEmpty()) {
            if (!request.getEmail().equals(user.getEmail()) && 
                userRepository.existsByEmail(request.getEmail())) {
                throw new RuntimeException("Email já cadastrado");
            }
            user.setEmail(request.getEmail());
        }
        if (request.getSenha() != null && !request.getSenha().isEmpty()) {
            user.setSenha(passwordEncoder.encode(request.getSenha()));
        }
        
        User updatedUser = userRepository.save(user);
        return toDTO(updatedUser);
    }
    
    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("Usuário não encontrado");
        }
        userRepository.deleteById(id);
    }
    
    // ===== MÉTODOS AUXILIARES =====
    public User findUserById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
    }
    
    private UserResponseDTO toDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setNome(user.getNome());
        dto.setEmail(user.getEmail());
        dto.setSaldo(user.getSaldo());
        dto.setCreatedAt(user.getCreatedAt());
        return dto;
    }

}
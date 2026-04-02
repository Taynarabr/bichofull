package com.bichofull.backend.service;

import com.bichofull.backend.dto.UserRequestDTO;
import com.bichofull.backend.dto.UserResponseDTO;
import com.bichofull.backend.dto.UserLoginDTO;
import com.bichofull.backend.dto.AuthResponseDTO;
import com.bichofull.backend.model.User;
import com.bichofull.backend.repository.UserRepository;
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
    
    public UserResponseDTO register(UserRequestDTO request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email já cadastrado");
        }
        
        User user = new User();
        user.setNome(request.getName()); 
        user.setEmail(request.getEmail());
        user.setSenha(passwordEncoder.encode(request.getPassword())); 
        
        user.setRole("USER");
        
        User savedUser = userRepository.save(user);
        return toDTO(savedUser);
    }
    
    public AuthResponseDTO login(UserLoginDTO loginDTO) {
        User user = userRepository.findByEmail(loginDTO.getEmail())
            .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
        
        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getSenha())) {
            throw new RuntimeException("Senha inválida");
        }
        
        String token = "simulated-jwt-token-" + user.getId() + "-" + System.currentTimeMillis();
        
        return new AuthResponseDTO(token, user.getId(), user.getNome(), user.getEmail(), user.getRole());
    }


    public void resetPassword(String email, String newPassword) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new ResourceNotFoundException("E-mail não encontrado no sistema."));
        
        user.setSenha(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
    
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
    
    public UserResponseDTO findById(Long id) {
        return toDTO(findUserById(id));
    }
    
    public List<UserResponseDTO> findAll() {
        return userRepository.findAll().stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }
    
    @SuppressWarnings("null")
    public UserResponseDTO update(Long id, UserRequestDTO request) {
        User user = findUserById(id);
        
        if (request.getName() != null && !request.getName().isEmpty()) {
            user.setNome(request.getName());
        }
        if (request.getEmail() != null && !request.getEmail().isEmpty()) {
            if (!request.getEmail().equals(user.getEmail()) && 
                userRepository.existsByEmail(request.getEmail())) {
                throw new RuntimeException("Email já cadastrado");
            }
            user.setEmail(request.getEmail());
        }
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            user.setSenha(passwordEncoder.encode(request.getPassword()));
        }
        
        User updatedUser = userRepository.save(user);
        return toDTO(updatedUser);
    }
    
    @SuppressWarnings("null")
    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("Usuário não encontrado");
        }
        userRepository.deleteById(id);
    }
    
    public User findUserById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
    }
    
    private UserResponseDTO toDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setName(user.getNome()); 
        dto.setEmail(user.getEmail());
        dto.setBalance(user.getSaldo());
        dto.setRole(user.getRole());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());
        return dto;
    }

    public UserResponseDTO deposit(Long userId, BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor do depósito deve ser maior que zero.");
        }
        
        User user = findUserById(userId);
        user.creditarSaldo(amount); 
        User savedUser = userRepository.save(user); 
        
        return toDTO(savedUser); 
    }

    public UserResponseDTO withdraw(Long userId, BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor do saque deve ser maior que zero.");
        }
        
        User user = findUserById(userId);
        
        if (!user.temSaldoSuficiente(amount)) {
            throw new RuntimeException("Saldo insuficiente para realizar este saque.");
        }
        
        user.debitarSaldo(amount); 
        User savedUser = userRepository.save(user); 
        
        return toDTO(savedUser); 
    }
}
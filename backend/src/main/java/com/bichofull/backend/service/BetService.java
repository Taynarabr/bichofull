package com.bichofull.backend.service;

import com.bichofull.backend.dto.BetRequestDTO;
import com.bichofull.backend.dto.BetResponseDTO;
import com.bichofull.backend.model.*;
import com.bichofull.backend.repository.BetRepository;
import com.bichofull.backend.exception.ResourceNotFoundException;
import com.bichofull.backend.exception.InsufficientBalanceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BetService {
    
    private final BetRepository betRepository;
    private final UserService userService;
    private final DrawService drawService;
    private final AnimalService animalService;
    
    // Multiplicadores (RF07)
    private static final int GRUPO_MULTIPLIER = 18;
    private static final int DEZENA_MULTIPLIER = 60;
    private static final int MILHAR_MULTIPLIER = 4000;
    
    public BetService(
            BetRepository betRepository,
            UserService userService,
            DrawService drawService,
            AnimalService animalService) {
        this.betRepository = betRepository;
        this.userService = userService;
        this.drawService = drawService;
        this.animalService = animalService;
    }
    
    public BetResponseDTO placeBet(Long userId, BetRequestDTO request) {
        // Validar request
        if (!request.isValid()) {
            throw new IllegalArgumentException("Dados da aposta inválidos");
        }
        
        // Buscar usuário
        User user = userService.findUserById(userId);
        
        // Validar saldo
        if (!user.temSaldoSuficiente(request.getValue())) {
            throw new InsufficientBalanceException("Saldo insuficiente para realizar a aposta");
        }
        
        // Debita saldo
        user.debitarSaldo(request.getValue());
        
        // Cria aposta
        Bet bet = new Bet();
        bet.setUser(user);
        bet.setType(Bet.BetType.valueOf(request.getType()));
        bet.setValue(request.getValue());
        bet.setChoice(request.getChoice());
        bet.setStatus(Bet.BetStatus.PENDENTE);
        
        // Se for aposta de grupo, associa o animal
        if (bet.getType() == Bet.BetType.GRUPO) {
            Integer grupo = request.getAnimalGroup() != null ? 
                request.getAnimalGroup() : Integer.parseInt(request.getChoice());
            
            Animal animal = animalService.findAnimalEntityByGrupo(grupo);
            bet.setAnimal(animal);
        }
        
        Bet savedBet = betRepository.save(bet);
        return toDTO(savedBet);
    }
    
    public BetResponseDTO processBet(Long betId, Long drawId) {
        Bet bet = betRepository.findById(betId)
            .orElseThrow(() -> new ResourceNotFoundException("Aposta não encontrada"));
        
        Draw draw = drawService.findDrawEntityById(drawId);
        
        // Verificar se aposta já foi processada
        if (bet.getDraw() != null) {
            throw new IllegalStateException("Aposta já foi processada");
        }
        
        boolean won = checkVictory(bet, draw);
        
        if (won) {
            BigDecimal prize = calculatePrize(bet);
            userService.creditBalance(bet.getUser().getId(), prize);
            bet.setStatus(Bet.BetStatus.GANHOU);
            bet.setPrize(prize);
        } else {
            bet.setStatus(Bet.BetStatus.PERDEU);
            bet.setPrize(BigDecimal.ZERO);
        }
        
        bet.setDraw(draw);
        Bet processedBet = betRepository.save(bet);
        return toDTO(processedBet);
    }
    
    private boolean checkVictory(Bet bet, Draw draw) {
        return switch (bet.getType()) {
            case GRUPO -> checkGrupo(bet, draw);
            case DEZENA -> checkDezena(bet, draw);
            case MILHAR -> checkMilhar(bet, draw);
        };
    }
    
    private boolean checkGrupo(Bet bet, Draw draw) {
        if (bet.getAnimal() == null) return false;
        String primeiraDezena = draw.getDezenaByPosition(1);
        return bet.getAnimal().contemDezena(primeiraDezena);
    }
    
    private boolean checkDezena(Bet bet, Draw draw) {
        for (int i = 1; i <= 5; i++) {
            if (draw.getDezenaByPosition(i).equals(bet.getChoice())) {
                return true;
            }
        }
        return false;
    }
    
    private boolean checkMilhar(Bet bet, Draw draw) {
        return draw.isWinningMilhar(bet.getChoice(), 1);
    }
    
    private BigDecimal calculatePrize(Bet bet) {
        int multiplier = switch (bet.getType()) {
            case GRUPO -> GRUPO_MULTIPLIER;
            case DEZENA -> DEZENA_MULTIPLIER;
            case MILHAR -> MILHAR_MULTIPLIER;
        };
        return bet.getValue().multiply(new BigDecimal(multiplier));
    }
    
    public BetResponseDTO findById(Long betId) {
        Bet bet = betRepository.findById(betId)
            .orElseThrow(() -> new ResourceNotFoundException("Aposta não encontrada"));
        return toDTO(bet);
    }
    
    public List<BetResponseDTO> getUserBets(Long userId) {
        // Verificar se usuário existe
        userService.findUserById(userId);
        
        return betRepository.findByUserId(userId).stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }
    
    public List<BetResponseDTO> findByStatus(String status) {
        Bet.BetStatus betStatus = Bet.BetStatus.valueOf(status.toUpperCase());
        return betRepository.findByStatus(betStatus).stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }
    
    public List<BetResponseDTO> findByDraw(Long drawId) {
        return betRepository.findByDrawId(drawId).stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }
    
    private BetResponseDTO toDTO(Bet bet) {
        BetResponseDTO dto = new BetResponseDTO();
        dto.setId(bet.getId());
        dto.setType(bet.getType().toString());
        dto.setValue(bet.getValue());
        dto.setChoice(bet.getChoice());
        dto.setStatus(bet.getStatus().toString());
        dto.setPrize(bet.getPrize());
        dto.setUserId(bet.getUser().getId());
        dto.setUserName(bet.getUser().getNome());
        if (bet.getDraw() != null) {
            dto.setDrawId(bet.getDraw().getId());
        }
        if (bet.getAnimal() != null) {
            dto.setAnimalName(bet.getAnimal().getNome());
            dto.setAnimalGroup(bet.getAnimal().getGrupo());
        }
        dto.setCreatedAt(bet.getCreatedAt());
        dto.setUpdatedAt(bet.getUpdatedAt());
        return dto;
    }
}
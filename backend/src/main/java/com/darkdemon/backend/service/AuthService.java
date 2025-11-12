package com.darkdemon.backend.service;

import com.darkdemon.backend.dto.LoginDTO;
import com.darkdemon.backend.dto.TokenResponseDTO;
import com.darkdemon.backend.dto.UserDTO;
import com.darkdemon.backend.model.RefreshToken;
import com.darkdemon.backend.model.User;
import com.darkdemon.backend.repository.RefreshTokenRepository;
import com.darkdemon.backend.repository.UserRepository;
import com.darkdemon.backend.security.HashEncoder;
import com.darkdemon.backend.security.TokenUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final HashEncoder encoder;
    private final JwtService jwtService;
    private final TokenUtil tokenUtil;
    private final RefreshTokenRepository refreshTokenRepository;

    public AuthService(UserRepository userRepository, HashEncoder encoder, JwtService jwtService, TokenUtil tokenUtil, RefreshTokenRepository refreshTokenRepository){
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.jwtService = jwtService;
        this.tokenUtil = tokenUtil;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public List<User> getUser() {
        return userRepository.findAll().stream().toList();
    }

    @Transactional
    public ResponseEntity<?> signUp(UserDTO userdto) {
        if (userRepository.existsByEmail(userdto.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Email already registered").toString());
        }

        try {
            User user = new User();
            user.setName(userdto.getName());
            user.setEmail(userdto.getEmail());
            user.setPassword(encoder.encode(userdto.getPassword()));
            user.setBudget(userdto.getBudget());
            user.setBudgetPeriod(userdto.getBudgetPeriod());
            user.setBudgetStartDate(userdto.getBudgetStartDate());
            user.setBudgetEndDate(userdto.getBudgetEndDate());
            user.setCreatedAt(LocalDateTime.now());
            userRepository.save(user);

            String accessToken = jwtService.generateAccessToken(user.getId());
            String refreshToken = jwtService.generateRefreshToken(user.getId());
            storeRefreshToken(user, refreshToken);
            TokenResponseDTO tokenResponse = new TokenResponseDTO(accessToken, refreshToken);

            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "User registered successfully!","Keys", tokenResponse));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));

        }
    }

    @Transactional
    public ResponseEntity<?> signIn(LoginDTO loginDTO){
        Optional<User> userOpt = userRepository.findByEmail(loginDTO.getEmail());

        if(userOpt.isEmpty()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid Credentials"));
        }

        User user = userOpt.get();
        if(!encoder.matchP(loginDTO.getPassword(), user.getPassword())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error","Invalid Credentials"));
        }

        String accessToken = jwtService.generateAccessToken(user.getId());
        String refreshToken = jwtService.generateRefreshToken(user.getId());
        storeRefreshToken(user, refreshToken);
        TokenResponseDTO tokenResponse = new TokenResponseDTO(accessToken, refreshToken);

        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "User SignIn successfully!","Keys", tokenResponse));
    }

    @Transactional
    public void storeRefreshToken(User user, String rawRefreshToken){
        String hashedRefreshToken = tokenUtil.hashWithHmacSha256(rawRefreshToken);
        Instant refreshTokenExpiry = Instant.now().plus(Duration.ofMillis(jwtService.getREFRESHER_TOKEN_VALIDITY_MS()));

        RefreshToken rt = new RefreshToken();
        rt.setUser(user);
        rt.setHashedToken(hashedRefreshToken);
        rt.setExpiresAt(refreshTokenExpiry);
        refreshTokenRepository.save(rt);
    }

    @Transactional
    public ResponseEntity<?> refresh(String refreshToken){
        if(!jwtService.validateRefreshToken(refreshToken)){
            throw new IllegalArgumentException("Invalid or expired refresh token");
        }

        Long userId = jwtService.getUserIdFromToken(refreshToken);
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Invalid or expired refresh token"));

        String hashed = tokenUtil.hashWithHmacSha256(refreshToken);
        refreshTokenRepository.findByUserIdAndHashedToken(user.getId(), hashed).orElseThrow(() -> new IllegalArgumentException("User and token doesn't match"));

        refreshTokenRepository.deleteByUserIdAndHashedToken(userId, hashed);

        String accessToken = jwtService.generateAccessToken(user.getId());
        String refresh = jwtService.generateRefreshToken(user.getId());
        storeRefreshToken(user, tokenUtil.hashWithHmacSha256(refresh));

        TokenResponseDTO tokenResponse = new TokenResponseDTO(accessToken, refresh);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "User SignIn successfully!","Keys", tokenResponse));
    }
}

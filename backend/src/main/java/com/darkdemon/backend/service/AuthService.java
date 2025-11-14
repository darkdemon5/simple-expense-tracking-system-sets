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

    public ResponseEntity<?> getUser(String token) {
        Boolean doNotRun = jwtService.validateAccessToken(token);
        if(!doNotRun){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Token");
        }
        Long userid = jwtService.getUserIdFromToken(token);
        Optional<User> user = userRepository.findById(userid);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @Transactional
    public ResponseEntity<?> signUp(UserDTO userdto) {
        if (userRepository.existsByEmail(userdto.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Email already registered").toString());
        }

        try {
            jwtService.saveUser(userdto);
            User user = new User();
            refreshTokenRepository.deleteByUserId(user.getId());
            refreshTokenRepository.flush();

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

        refreshTokenRepository.deleteByUserId(user.getId());
        refreshTokenRepository.flush();

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

        refreshTokenRepository.deleteByUserId(userId);
        refreshTokenRepository.flush();

        String accessToken = jwtService.generateAccessToken(user.getId());
        String refresh = jwtService.generateRefreshToken(user.getId());
        storeRefreshToken(user, tokenUtil.hashWithHmacSha256(refresh));

        TokenResponseDTO tokenResponse = new TokenResponseDTO(accessToken, refresh);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "User SignIn successfully!","Keys", tokenResponse));
    }

    @Transactional
    public ResponseEntity<?> updateUser(String token ,UserDTO userdto){
        if(!jwtService.validateAccessToken(token)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Invalid Token"));
        }
        try {
            jwtService.saveUser(userdto);
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "User Updated Successfully"));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Update Failed"));
        }
    }

    @Transactional
    public ResponseEntity<?> deleteUser(String token) {
        if(!jwtService.validateAccessToken(token)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Invalid Token"));
        }
        try {
            userRepository.deleteById(jwtService.getUserIdFromToken(token));
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "User Deleted Successfully"));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Error while deleting user"));
        }

    }
}

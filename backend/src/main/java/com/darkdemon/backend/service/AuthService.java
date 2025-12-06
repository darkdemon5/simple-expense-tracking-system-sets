package com.darkdemon.backend.service;

import com.darkdemon.backend.dto.*;
import com.darkdemon.backend.exception.GlobalExceptionHandler;
import com.darkdemon.backend.model.RefreshToken;
import com.darkdemon.backend.model.User;
import com.darkdemon.backend.repository.RefreshTokenRepository;
import com.darkdemon.backend.repository.UserRepository;
import com.darkdemon.backend.security.HashEncoder;
import com.darkdemon.backend.security.TokenUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
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
    private final GlobalExceptionHandler globalExceptionHandler;

    public AuthService(UserRepository userRepository, HashEncoder encoder, JwtService jwtService, TokenUtil tokenUtil, RefreshTokenRepository refreshTokenRepository, GlobalExceptionHandler globalExceptionHandler) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.jwtService = jwtService;
        this.tokenUtil = tokenUtil;
        this.refreshTokenRepository = refreshTokenRepository;
        this.globalExceptionHandler = globalExceptionHandler;
    }

    @Transactional
    public ResponseEntity<?> signUp(UserDTO userdto, HttpServletResponse response) {
        if (userRepository.existsByEmail(userdto.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Email already registered"));
        }
        try {
            //taking info from userdto and storing it in user and repository
            User user = jwtService.saveUser(userdto);
            String accessToken = jwtService.generateAccessToken(user.getId());
            String refreshToken = jwtService.generateRefreshToken(user.getId());

            jwtService.setRefreshTokenCookie(response, refreshToken);
            storeRefreshToken(user, refreshToken);

            UserResponseDTO urDto = UserResponseDTO.addData(user);

            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "User registered successfully!", "accessToken", accessToken, "User", urDto));
        } catch (Exception e) {
            return globalExceptionHandler.handleGenericException(e);
        }
    }

    @Transactional
    public ResponseEntity<?> signIn(LoginDTO loginDTO, HttpServletResponse response) {
        Optional<User> userOpt = userRepository.findByEmail(loginDTO.getEmail());
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid Credentials"));
        }

        User user = userOpt.get();

        if (!encoder.matchP(loginDTO.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid Credentials"));
        }

        refreshTokenRepository.deleteByUserId(user.getId());
        refreshTokenRepository.flush();

        String accessToken = jwtService.generateAccessToken(user.getId());
        String refreshToken = jwtService.generateRefreshToken(user.getId());

        jwtService.setRefreshTokenCookie(response, refreshToken);
        storeRefreshToken(user, refreshToken);

//        TokenResponseDTO tokenResponse = new TokenResponseDTO(accessToken, refreshToken);

        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "User SignIn successfully!", "accessToken", accessToken));
    }

    @Transactional
    protected void storeRefreshToken(User user, String rawRefreshToken) {
        String hashedRefreshToken = tokenUtil.hashWithHmacSha256(rawRefreshToken);

        Instant refreshTokenExpiry = Instant.now().plus(Duration.ofMillis(jwtService.getREFRESHER_TOKEN_VALIDITY_MS()));

        RefreshToken rt = new RefreshToken();
        rt.setUser(user);
        rt.setHashedToken(hashedRefreshToken);
        rt.setExpiresAt(refreshTokenExpiry);

        refreshTokenRepository.save(rt);
    }

    @Transactional
    public ResponseEntity<?> refresh(String refreshToken, HttpServletResponse response) {
        if (!jwtService.validateRefreshToken(refreshToken)) {
            throw new IllegalArgumentException("Invalid or expired refresh token");
        }

        Long userId = jwtService.getUserIdFromToken(refreshToken);
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Invalid or expired refresh token"));

        refreshTokenRepository.deleteByUserId(userId);
        refreshTokenRepository.flush();

        String newAccessToken = jwtService.generateAccessToken(user.getId());
        String newRefreshToken = jwtService.generateRefreshToken(user.getId());

        storeRefreshToken(user, tokenUtil.hashWithHmacSha256(newRefreshToken));
        jwtService.setRefreshTokenCookie(response, newRefreshToken);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "User SignIn successfully!", "accessToken", newAccessToken));
    }


    public ResponseEntity<?> logoutUser(HttpServletResponse response) {
        Cookie cookie = new Cookie("refreshToken", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return ResponseEntity.ok("Logged out successfully");
    }
}

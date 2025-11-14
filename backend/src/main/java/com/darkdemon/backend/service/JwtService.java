package com.darkdemon.backend.service;

import com.darkdemon.backend.dto.UserDTO;
import com.darkdemon.backend.model.User;
import com.darkdemon.backend.repository.UserRepository;
import com.darkdemon.backend.security.HashEncoder;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Date;

@Service
public class JwtService {

    @Getter
    private final SecretKey secretKey;
    private final long ACCESS_TOKEN_VALIDITY_MS = Duration.ofMinutes(15).toMillis();
    @Getter
    private final long REFRESHER_TOKEN_VALIDITY_MS = Duration.ofDays(30).toMillis();
    UserRepository userRepository;
    private final HashEncoder encoder;

    public JwtService(@Value("${SECRET_KEY_BASE64}") String jwtSecret, UserRepository userRepository, HashEncoder encoder) {
        byte[] decodedKey = Base64.getDecoder().decode(jwtSecret);
        this.secretKey = Keys.hmacShaKeyFor(decodedKey);
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    //Token Generation
    private String generateToken(Long userId, String type, long expiry) {

        return Jwts.builder()
                .subject(userId.toString())
                .claim("type", type)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiry))
                .signWith(secretKey, Jwts.SIG.HS256)
                .compact();
    }

    public String generateAccessToken(Long userId) {
        return generateToken(userId, "access", ACCESS_TOKEN_VALIDITY_MS);
    }

    public String generateRefreshToken(Long userId) {
        return generateToken(userId, "refresh", REFRESHER_TOKEN_VALIDITY_MS);
    }

    //Token Validation
    public Boolean validateAccessToken(String token) {
        String tokenType = extractType(token);
        if(tokenType.equals("false")){
            return false;
        }
        return "access".equals(tokenType);
    }

    public Boolean validateRefreshToken(String token) {
        String tokenType = extractType(token);
        if(tokenType.equals("false")){
            return false;
        }
        return "refresh".equals(tokenType);
    }

    public Long getUserIdFromToken(String token) {

        Claims claims = parseAllClaims(token);
        if (claims == null) {
            throw new IllegalArgumentException("Invalid Token!!!");
        }
        return Long.parseLong(claims.getSubject());
    }

    private Claims parseAllClaims(String token) {
        try {
        String rawToken = token.startsWith("Bearer ") ? token.substring(7) : token;
            return Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(rawToken)
                    .getPayload();
        } catch (Exception e) {
            System.err.println("❌ Error: " + e.getClass().getName() + " - " + e.getMessage());
//            e.printStackTrace();
            return null;
        }

    }

    private Boolean validateUser(String token){
        return userRepository.existsById(getUserIdFromToken(token));
    }

    private String extractType(String token){
        Claims claims = parseAllClaims(token);
        if (claims == null) {
            return "false";
        }
        Object tokenTypeObj = claims.get("type");
        if (tokenTypeObj == null) {
            return "false";
        }
        if (!validateUser(token)){
            return "false";
        }
        return tokenTypeObj.toString();
    }

    public void saveUser(UserDTO userdto){
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

    }


}

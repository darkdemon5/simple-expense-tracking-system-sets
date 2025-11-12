package com.darkdemon.backend.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;

@Service
public class JwtService {

    @Getter
    private final SecretKey secretKey;
    private final long ACCESS_TOKEN_VALIDITY_MS = Duration.ofMinutes(15).toMillis();
    @Getter
    private final long REFRESHER_TOKEN_VALIDITY_MS = Duration.ofDays(30).toMillis();

    public JwtService(@Value("${SECRET_KEY_BASE64}") String jwtSecret) {
        byte[] decodedKey = Base64.getDecoder().decode(jwtSecret);
        this.secretKey = Keys.hmacShaKeyFor(decodedKey);
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
        Claims claims = parseAllClaims(token);
        if (claims == null) {
            return false;
        }
        Object tokenTypeObj = claims.get("type");
        if (tokenTypeObj == null) {
            return false;
        }
        String tokenType = tokenTypeObj.toString();
        return "access".equals(tokenType);
    }

    public Boolean validateRefreshToken(String token) {
        Claims claims = parseAllClaims(token);
        if (claims == null) {
            return false;
        }
        Object tokenTypeObj = claims.get("type");
        if (tokenTypeObj == null) {
            return false;
        }
        String tokenType = tokenTypeObj.toString();
        return "refresh".equals(tokenType);
    }

    public Long getUserIdFromToken(String token){

        Claims claims = parseAllClaims(token);
        if(claims == null){
            throw new IllegalArgumentException("Invalid Token!!!");
        }
        return Long.parseLong(claims.getSubject());
    }

    private Claims parseAllClaims(String token) {
        String rawToken= token.startsWith("Bearer ") ? token.substring(7) : token;
        try {
            return Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(rawToken)
                    .getPayload();
        } catch (Exception e) {
            return null;
        }

    }


}

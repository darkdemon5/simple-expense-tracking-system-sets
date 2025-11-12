package com.darkdemon.backend.repository;

import com.darkdemon.backend.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByUserIdAndHashedToken(Long userId, String hashedToken);

    void deleteByUserIdAndHashedToken(Long userId, String hashedToken);
}

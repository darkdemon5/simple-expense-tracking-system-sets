package com.darkdemon.backend.repository;

import com.darkdemon.backend.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
//    Optional<RefreshToken> findByUserIdAndHashedToken(Long userId, String hashedToken);
//    void deleteByUserIdAndHashedToken(Long userId, String hashedToken);
    @Modifying
    @Transactional
    @Query("DELETE FROM RefreshToken rt WHERE rt.user.id = :userId")
    void deleteByUserId(Long userId);
}

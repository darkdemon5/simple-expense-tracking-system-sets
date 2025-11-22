package com.darkdemon.backend.service;

import com.darkdemon.backend.dto.UpdateEmailDTO;
import com.darkdemon.backend.dto.UpdatePasswordDTO;
import com.darkdemon.backend.dto.UserUpdateDTO;
import com.darkdemon.backend.model.User;
import com.darkdemon.backend.repository.UserRepository;
import com.darkdemon.backend.security.HashEncoder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
public class UpdateService {
    private final UserRepository userRepository;
    private final HashEncoder hashEncoder;

    public UpdateService(UserRepository userRepository, HashEncoder hashEncoder) {
        this.userRepository = userRepository;
        this.hashEncoder = hashEncoder;
    }

    @Transactional
    public ResponseEntity<?> updateUser(Long userId, UserUpdateDTO userUpdateDTO) {
        try {
            User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User Not Found"));

            if (userUpdateDTO.getName() != null && !userUpdateDTO.getName().isBlank()) {
                user.setName(userUpdateDTO.getName());
            }
            if (userUpdateDTO.getBudget() != null) {
                user.setBudget(userUpdateDTO.getBudget());
            }
            if (userUpdateDTO.getBudgetPeriod() != null) {
                user.setBudgetPeriod(userUpdateDTO.getBudgetPeriod());
            }
            if (userUpdateDTO.getBudgetStartDate() != null) {
                user.setBudgetStartDate(userUpdateDTO.getBudgetStartDate());
            }
            if (userUpdateDTO.getBudgetEndDate() != null) {
                user.setBudgetEndDate(userUpdateDTO.getBudgetEndDate());
            }
            userRepository.save(user);

            return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "User Updated Successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", e.getMessage()));
        }
    }

    @Transactional
    public ResponseEntity<?> updateEmail(Long id, UpdateEmailDTO updateEmailDTO) {
        try {
            User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User Not Found"));

            if (!user.getEmail().equals(updateEmailDTO.getCurrentEmail())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Current currentEmail is incorrect"));
            }

            if (!hashEncoder.matchP(updateEmailDTO.getPassword(), user.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Password Doesn't match"));
            }

            if (userRepository.existsByEmail(updateEmailDTO.getNewEmail())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Email already Exists"));
            }

            if (user.getEmail().equals(updateEmailDTO.getNewEmail())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "New currentEmail is same as old currentEmail"));
            }

            user.setEmail(updateEmailDTO.getNewEmail());
            userRepository.save(user);

            return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Email updated successfully", "New currentEmail", user.getEmail()));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }

    @Transactional
    public ResponseEntity<?> updatePassword(Long id, UpdatePasswordDTO updatePasswordDTO) {
        try {
            User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User Not Found!"));

            if (!user.getEmail().equals(updatePasswordDTO.getEmail())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Current currentEmail is incorrect!"));
            }
            if (!hashEncoder.matchP(updatePasswordDTO.getCurrentPassword(), user.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Password Doesn't match"));
            }
            if (hashEncoder.matchP(updatePasswordDTO.getCurrentPassword(), user.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "New Password is same as old password"));
            }
            if (updatePasswordDTO.getNewPassword().length() < 6 || updatePasswordDTO.getNewPassword().length() > 18) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("error", "Password must be between 6 and 18 characters"));
            }
            user.setPassword(hashEncoder.encode(updatePasswordDTO.getNewPassword()));
            userRepository.save(user);

            return ResponseEntity.status(HttpStatus.OK).body(Map.of("Message", "Password Updated Successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }
}

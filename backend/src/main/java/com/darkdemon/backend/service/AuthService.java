package com.darkdemon.backend.service;

import com.darkdemon.backend.dto.UserDTO;
import com.darkdemon.backend.model.User;
import com.darkdemon.backend.repository.UserRepository;
import com.darkdemon.backend.security.HashEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;
//    @Autowired
    HashEncoder encoder;
//    private PasswordEncoder passwordEncoder;

    public List<User> getUser() {
        return userRepository.findAll().stream().toList();
    }


    public ResponseEntity<String> signUp(UserDTO userdto) {
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

            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "User registered successfully!", "userId", user.getId()).toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()).toString());

        }
    }
}

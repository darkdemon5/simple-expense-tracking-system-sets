package com.darkdemon.backend.controller;

import com.darkdemon.backend.dto.UserDTO;
import com.darkdemon.backend.model.User;
import com.darkdemon.backend.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class AuthController {

    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/")
private String getUser(){
    return "Fuck You!!!";
}

@PostMapping("/signup")
private ResponseEntity<String> postUser(@RequestBody UserDTO userdto){
    if(userRepository.existsByEmail(userdto.getEmail())){
        return ResponseEntity.badRequest().body("Email Already Exists");
    }

    User user = new User();
    user.setName(userdto.getName());
    user.setEmail(userdto.getEmail());
    user.setPassword(userdto.getPassword());
    user.setBudget(userdto.getBudget());
    user.setBudgetPeriod(userdto.getBudgetPeriod());
    user.setBudgetStartDate(userdto.getBudgetStartDate());
    user.setBudgetEndDate(userdto.getBudgetEndDate());
    user.setCreatedAt(LocalDateTime.now());
    userRepository.save(user);

    return ResponseEntity.ok("User Registered Successfully");
}
}

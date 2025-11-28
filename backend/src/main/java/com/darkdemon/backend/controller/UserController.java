package com.darkdemon.backend.controller;

import com.darkdemon.backend.dto.UserExtraDataDTO;
import com.darkdemon.backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/me")
    private ResponseEntity<?> getUser(@RequestHeader("Authorization") String token) {
        return userService.getUser(token);
    }

    @PostMapping("/data")
    private ResponseEntity<?> postExtraData(@RequestHeader("Authorization") String token, UserExtraDataDTO userExtraDataDTO){
        return userService.postExtraData(token, userExtraDataDTO);
    }

}

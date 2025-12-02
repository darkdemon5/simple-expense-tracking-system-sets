package com.darkdemon.backend.service;

import com.darkdemon.backend.dto.UserExtraDataDTO;
import com.darkdemon.backend.dto.UserResponseDTO;
import com.darkdemon.backend.exception.GlobalExceptionHandler;
import com.darkdemon.backend.model.User;
import com.darkdemon.backend.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
public class UserService {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final GlobalExceptionHandler globalExceptionHandler;

    public UserService(JwtService jwtService, UserRepository userRepository, GlobalExceptionHandler globalExceptionHandler){
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.globalExceptionHandler = globalExceptionHandler;
    }

    public ResponseEntity<?> getUser(String token) {
        Boolean doNotRun = jwtService.validateAccessToken(token);
        if (!doNotRun) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Token");
        }
        Long userid = jwtService.getUserIdFromToken(token);
        User user = userRepository.findById(userid).orElseThrow(() -> new IllegalArgumentException("Invalid Credentials"));
        UserResponseDTO userResponseDTO = UserResponseDTO.addData(user);
        return ResponseEntity.status(HttpStatus.OK).body(userResponseDTO);
    }

    @Transactional
    public ResponseEntity<?> postExtraData(String token, UserExtraDataDTO userExtraDataDTO) {
        try {
            User user = userRepository.findById(jwtService.getUserIdFromToken(token)).orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));
            user.setBudget(userExtraDataDTO.getBudget());
            user.setBudgetPeriod(userExtraDataDTO.getBudgetPeriod());
            user.setBudgetStartDate(userExtraDataDTO.getBudgetStartDate());
            user.setBudgetEndDate(userExtraDataDTO.getBudgetEndDate());
            userRepository.save(user);

            UserExtraDataDTO userExtraDataDTO1 = UserExtraDataDTO.dataFrom(user);

            return ResponseEntity.status(HttpStatus.OK).body(Map.of("Message","User Data posted Successfully", "Data", userExtraDataDTO1));
        }
        catch (Exception e){
            return globalExceptionHandler.handleGenericException(e);
        }
    }

    @Transactional
    public ResponseEntity<?> deleteUser(String token) {
        try {
            Long id = jwtService.getUserIdFromToken(token);

            User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User Not Found!!"));

            userRepository.delete(user);

            return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "User Deleted Successfully"));
        } catch (Exception e) {
            return globalExceptionHandler.handleGenericException(e);
        }

    }

}

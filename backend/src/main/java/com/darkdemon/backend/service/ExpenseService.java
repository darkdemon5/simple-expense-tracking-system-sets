package com.darkdemon.backend.service;

import com.darkdemon.backend.repository.ExpenseRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final JwtService jwtService;

    public ExpenseService(ExpenseRepository expenseRepository, JwtService jwtService) {
        this.expenseRepository = expenseRepository;
        this.jwtService = jwtService;
    }

    public ResponseEntity<?> getExpenses(String token) {
        if(!jwtService.validateAccessToken(token)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Invalid Token"));
        }
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("Expenses", expenseRepository.getExpenseByUser_Id(jwtService.getUserIdFromToken(token))));
    }

//    public ResponseEntity<?> createExpense(String token ,ExpenseDTO expenseDTO){
//        if(!jwtService.validateAccessToken(token)){
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Invalid Token"));
//        }
//
//    }
}

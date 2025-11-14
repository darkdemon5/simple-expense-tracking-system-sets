package com.darkdemon.backend.service;

import com.darkdemon.backend.dto.ExpenseDTO;
import com.darkdemon.backend.model.Expense;
import com.darkdemon.backend.repository.ExpenseRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final JwtService jwtService;

    public ExpenseService(ExpenseRepository expenseRepository, JwtService jwtService) {
        this.expenseRepository = expenseRepository;
        this.jwtService = jwtService;
    }

    @Transactional
    public ResponseEntity<?> getExpenses(String token) {
        if(!jwtService.validateAccessToken(token)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Invalid Token"));
        }
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("Expenses", expenseRepository.getExpenseByUser_Id(jwtService.getUserIdFromToken(token))));
    }

    @Transactional
    public ResponseEntity<?> getExpense(String token, Long id) {
        if(!jwtService.validateAccessToken(token)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Invalid Token"));
        }
        try{
            return ResponseEntity.status(HttpStatus.OK).body(expenseRepository.getExpenseById(id));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("an error occurred", e.getMessage()));
        }
    }

    @Transactional
    public ResponseEntity<?> createExpense(String token ,ExpenseDTO expenseDTO){
        if(!jwtService.validateAccessToken(token)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Invalid Token"));
        }

        return getResponseEntity(expenseDTO);
    }

    @Transactional
    public ResponseEntity<?> updateExpense(String token, ExpenseDTO expenseDTO) {
        if(!jwtService.validateAccessToken(token)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Invalid Token!"));
        }
        return getResponseEntity(expenseDTO);

    }

    @Transactional
    protected ResponseEntity<?> getResponseEntity(ExpenseDTO expenseDTO) {
        try{
            Expense expense = new Expense();
            expense.setTitle(expenseDTO.getTitle());
            expense.setDescription(expenseDTO.getDescription());
            expense.setCategory(expenseDTO.getCategory());
            expense.setExpenseAmount(expenseDTO.getExpenseAmount());
            expense.setCreatedDate(expenseDTO.getCreatedDate());
            expense.setUpdatedDate(expenseDTO.getUpdatedDate());
            expense.setExpenseDate(expenseDTO.getExpenseDate());
            expense.setPaymentMethod(expenseDTO.getPaymentMethod());
            expense.setIsDeleted(expenseDTO.getIsDeleted());
            expenseRepository.save(expense);

            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("Message", "Expense added Successfully", "Expense Data", expense));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("an error occurred", e.getMessage()));
        }
    }

    @Transactional
    public ResponseEntity<?> deleteExpense(String token, Long id) {
        if(!jwtService.validateAccessToken(token)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Invalid Token!!"));
        }
        expenseRepository.deleteById(id);

        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Expense Deleted Successfully"));
    }
}

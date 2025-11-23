package com.darkdemon.backend.service;

import com.darkdemon.backend.dto.ExpenseDTO;
import com.darkdemon.backend.dto.UpdateExpenseDTO;
import com.darkdemon.backend.model.Expense;
import com.darkdemon.backend.model.User;
import com.darkdemon.backend.repository.ExpenseRepository;
import com.darkdemon.backend.repository.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public ExpenseService(ExpenseRepository expenseRepository, JwtService jwtService, UserRepository userRepository) {
        this.expenseRepository = expenseRepository;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Transactional
    public ResponseEntity<?> getExpenses(String token) {
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("Expenses", expenseRepository.getExpenseByUser_Id(jwtService.getUserIdFromToken(token))));
    }

    @Transactional
    public ResponseEntity<?> getExpense(String token, Long id) {
        Expense expense = expenseRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No expense found"));
        if(!Objects.equals(expense.getUser().getId(), jwtService.getUserIdFromToken(token))){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access Denied!");
        }
        try {
            return ResponseEntity.status(HttpStatus.OK).body(expenseRepository.getExpenseById(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("an error occurred", e.getMessage()));
        }
    }

    @Transactional
    public ResponseEntity<?> createExpense(String token, ExpenseDTO expenseDTO) {
        try {
            Long userId = jwtService.getUserIdFromToken(token);
            User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
            Expense expense = getExpense(expenseDTO, user);
            expenseRepository.save(expense);

            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("Message", "Expense added Successfully", "Expense Data", expense));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("an error occurred", e.getMessage()));
        }
    }

    private static @NotNull Expense getExpense(ExpenseDTO expenseDTO, User user) {
        Expense expense = new Expense();
        expense.setUser(user);
        expense.setTitle(expenseDTO.getTitle());
        expense.setDescription(expenseDTO.getDescription());
        expense.setCategory(expenseDTO.getCategory());
        expense.setExpenseAmount(expenseDTO.getExpenseAmount());
        expense.setCreatedDate(expenseDTO.getCreatedDate());
        expense.setExpenseDate(expenseDTO.getExpenseDate());
        expense.setPaymentMethod(expenseDTO.getPaymentMethod());
        expense.setIsDeleted(expenseDTO.getIsDeleted());
        return expense;
    }

    @Transactional
    public ResponseEntity<?> updateExpense(String token, Long id, UpdateExpenseDTO updateExpenseDTO) {
        try {
            Expense expense = expenseRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No expense found"));
            if(!expense.getUser().getId().equals(jwtService.getUserIdFromToken(token))){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access Denied!");
            }
            if (updateExpenseDTO.getTitle() != null && !updateExpenseDTO.getTitle().isBlank()) {
                expense.setTitle(updateExpenseDTO.getTitle());
            }
            if (updateExpenseDTO.getDescription() != null && !updateExpenseDTO.getDescription().isBlank()) {
                expense.setDescription(updateExpenseDTO.getDescription());
            }
            if (updateExpenseDTO.getCategory() != null && !updateExpenseDTO.getCategory().isBlank()) {
                expense.setCategory(updateExpenseDTO.getCategory());
            }
            if (updateExpenseDTO.getExpenseAmount() != null) {
                expense.setExpenseAmount(updateExpenseDTO.getExpenseAmount());
            }
            expense.setUpdatedDate(LocalDateTime.now());
            if (updateExpenseDTO.getExpenseDate() != null) {
                expense.setExpenseDate(updateExpenseDTO.getExpenseDate());
            }
            if (updateExpenseDTO.getPaymentMethod() != null) {
                expense.setPaymentMethod(updateExpenseDTO.getPaymentMethod());
            }
            expenseRepository.save(expense);
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Expense Updated Successfully", "Updated Expense", expense));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("Error", e.getMessage()));
        }
    }

    @Transactional
    public ResponseEntity<?> deleteExpense(String token, Long id) {
        Expense expense = expenseRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No expense found"));
        if(!Objects.equals(expense.getUser().getId(), jwtService.getUserIdFromToken(token))){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access Denied!");
        }
        expenseRepository.deleteById(id);

        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Expense Deleted Successfully"));
    }
}

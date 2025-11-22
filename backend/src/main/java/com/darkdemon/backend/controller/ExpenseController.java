package com.darkdemon.backend.controller;

import com.darkdemon.backend.dto.ExpenseDTO;
import com.darkdemon.backend.dto.UpdateExpenseDTO;
import com.darkdemon.backend.service.ExpenseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/Expense")
public class ExpenseController {

    ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping("/")
    private ResponseEntity<?> getExpenses(@RequestHeader("Authorization") String token) {
        return expenseService.getExpenses(token);
    }

    @GetMapping("/{id}")
    private ResponseEntity<?> getExpense(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        if(id == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Id is null"));
        }
        return expenseService.getExpense(token, id);
    }

    @PostMapping("/Create")
    private ResponseEntity<?> createExpense(@RequestHeader("Authorization") String token, @RequestBody ExpenseDTO expenseDTO) {
        return expenseService.createExpense(token, expenseDTO);
    }

    @PutMapping("/Update/{id}")
    private ResponseEntity<?> updateExpense(@RequestHeader("Authorization") String token, @PathVariable Long id,@RequestBody UpdateExpenseDTO updateExpenseDTO) {
        if(id != null && updateExpenseDTO != null){
            return expenseService.updateExpense(token, id, updateExpenseDTO);
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Id and update data is null"));
        }
    }

    @DeleteMapping("/Delete/{id}")
    private ResponseEntity<?> deleteExpense(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        if(id == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Id is null"));
        }
        return expenseService.deleteExpense(token, id);
    }
}

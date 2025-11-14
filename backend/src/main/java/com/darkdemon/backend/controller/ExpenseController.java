package com.darkdemon.backend.controller;

import com.darkdemon.backend.dto.ExpenseDTO;
import com.darkdemon.backend.service.ExpenseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Expenses")
public class ExpenseController {

    ExpenseService expenseService;
    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping("/")
    private ResponseEntity<?> getExpenses(@RequestHeader("Authorization") String token) {
        return expenseService.getExpenses(token);
    }

    @GetMapping("/Expense/{id}")
    private ResponseEntity<?> getExpense(@RequestHeader("Authorization") String token,@PathVariable Long id){
        return expenseService.getExpense(token, id);
    }

    @PostMapping("/Create")
    private ResponseEntity<?> createExpense(@RequestHeader("Authorization") String token, @RequestBody ExpenseDTO expenseDTO){
        return expenseService.createExpense(token, expenseDTO);
    }

    @PutMapping("/Update")
    private ResponseEntity<?> updateExpense(@RequestHeader("Authorization") String token, @RequestBody ExpenseDTO expenseDTO){
        return expenseService.updateExpense(token, expenseDTO);
    }
}

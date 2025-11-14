package com.darkdemon.backend.controller;

import com.darkdemon.backend.service.ExpenseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExpenseController {

    ExpenseService expenseService;
    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping("/Expenses")
    public ResponseEntity<?> getExpense(@RequestHeader("Authorization") String token) {
        return expenseService.getExpenses(token);
    }
}

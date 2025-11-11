package com.darkdemon.backend.controller;

import com.darkdemon.backend.model.Expense;
import com.darkdemon.backend.service.ExpesneService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ExpenseController {

    ExpesneService expesneService;

    @GetMapping("/Expenses")
    public List<Expense> getExpense() {
        return expesneService.getExpense();
    }
}

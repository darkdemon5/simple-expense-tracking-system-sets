package com.darkdemon.backend.service;

import com.darkdemon.backend.model.Expense;
import com.darkdemon.backend.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpesneService {

    @Autowired
    private ExpenseRepository expenseRepository;

    public List<Expense> getExpense() {

        return expenseRepository.findAll().stream().toList();
    }
}

package com.darkdemon.backend.service;

import com.darkdemon.backend.model.Expense;
import com.darkdemon.backend.repository.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpesneService {

    private final ExpenseRepository expenseRepository;

    public ExpesneService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public List<Expense> getExpense() {

        return expenseRepository.findAll().stream().toList();
    }
}

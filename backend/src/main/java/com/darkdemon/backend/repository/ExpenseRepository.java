package com.darkdemon.backend.repository;

import com.darkdemon.backend.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> getExpenseByUser_Id(Long userId);
}

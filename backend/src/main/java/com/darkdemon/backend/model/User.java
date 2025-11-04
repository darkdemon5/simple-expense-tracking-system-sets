package com.darkdemon.backend.model;

import com.darkdemon.backend.enums.BudgetPeriodEnum;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String email;
    private String password;
    private BigDecimal budget;
    private BudgetPeriodEnum budget_period;
    private Date budget_start_date;
    private Date budget_end_date;
    private Date created_at;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Expense> expenseList;
}

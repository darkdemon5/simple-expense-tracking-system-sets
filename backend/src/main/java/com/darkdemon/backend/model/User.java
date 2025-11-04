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
    @Enumerated(EnumType.STRING)
    @Column(name = "budget_period",columnDefinition = "budget_period_enum")
    private BudgetPeriodEnum budgetPeriod;
    @Column(name = "budget_start_date")
    private Date budgetStartDate;
    @Column(name = "budget_end_date")
    private Date budgetEndDate;
    @Column(name = "created_at")
    private Date createdAt;
    @OneToMany(mappedBy = "user_id", cascade = CascadeType.ALL)
    private List<Expense> expenseList;
}

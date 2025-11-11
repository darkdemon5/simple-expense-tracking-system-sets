package com.darkdemon.backend.model;

import com.darkdemon.backend.enums.BudgetPeriodEnum;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;
    private BigDecimal budget;
    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    @Column(name = "budget_period", columnDefinition = "budget_period_enum")
    private BudgetPeriodEnum budgetPeriod;
    @Column(name = "budget_start_date")
    private LocalDate budgetStartDate;
    @Column(name = "budget_end_date")
    private LocalDate budgetEndDate;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @OneToMany(mappedBy = "user_id", cascade = CascadeType.ALL)
    private List<Expense> expenseList;
}

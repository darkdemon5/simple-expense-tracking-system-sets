package com.darkdemon.backend.dto;

import com.darkdemon.backend.enums.BudgetPeriodEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String name;
    private String email;
    private String password;
    private BigDecimal budget;
    private BudgetPeriodEnum budgetPeriod;
    private Date budgetStartDate;
    private Date budgetEndDate;
    private LocalDateTime createdAt;
}

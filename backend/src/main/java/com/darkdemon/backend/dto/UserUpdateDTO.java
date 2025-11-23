package com.darkdemon.backend.dto;

import com.darkdemon.backend.enums.BudgetPeriodEnum;
import com.darkdemon.backend.model.User;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateDTO {

    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name must not exceeds 100 characters")
    private String name;

    @NotNull(message = "Budget is required")
    @Min(value = 0, message = "Budget must be greater than 0")
    private BigDecimal budget;

    @NotNull(message = "Budget period it required")
    private BudgetPeriodEnum budgetPeriod;

    @NotNull(message = "Budget start date is required")
    private LocalDate budgetStartDate;

    @NotNull(message = "Budget end date is required")
    private LocalDate budgetEndDate;

    public UserUpdateDTO(User user){
        this.setName(user.getName());
        this.setBudget(user.getBudget());
        this.setBudgetPeriod(user.getBudgetPeriod());
        this.setBudgetStartDate(user.getBudgetStartDate());
        this.setBudgetEndDate(user.getBudgetEndDate());
    }
}

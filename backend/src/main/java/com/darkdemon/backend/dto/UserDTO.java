package com.darkdemon.backend.dto;

import com.darkdemon.backend.enums.BudgetPeriodEnum;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name must not exceeds 100 characters")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 18, message = "Password must be between 6 and 18")
    private String password;

    @NotNull(message = "Budget is required")
    @Positive(message = "Budget must be greater than 0")
    private BigDecimal budget;

    @NotNull(message = "Budget period it requirec")
    private BudgetPeriodEnum budgetPeriod;

    @NotNull(message = "Budget start date is required")
    private LocalDate budgetStartDate;

    @NotNull(message = "Budget end date is required")
    private LocalDate budgetEndDate;

    private LocalDateTime createdAt;

    @AssertTrue(message = "Budget end date must be after start date")
    private boolean isEndDateValid() {
        if (budgetStartDate == null || budgetEndDate == null) {
            return true;
        }
        return budgetEndDate.isAfter(budgetStartDate);
    }

}

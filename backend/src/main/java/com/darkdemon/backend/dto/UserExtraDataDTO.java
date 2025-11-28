package com.darkdemon.backend.dto;

import com.darkdemon.backend.enums.BudgetPeriodEnum;
import com.darkdemon.backend.model.User;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserExtraDataDTO {
    @NotNull(message = "Budget is required")
    @Min(value = 0, message = "Budget must be greater than 0")
    private BigDecimal budget;

    @NotNull(message = "Budget period it required")
    private BudgetPeriodEnum budgetPeriod;

    @NotNull(message = "Budget start date is required")
    private LocalDate budgetStartDate;

    @NotNull(message = "Budget end date is required")
    private LocalDate budgetEndDate;

    @AssertTrue(message = "Budget end date must be after start date")
    private boolean isEndDateValid() {
        if (budgetStartDate == null || budgetEndDate == null) {
            return true;
        }
        return budgetEndDate.isAfter(budgetStartDate);
    }

    public static UserExtraDataDTO dataFrom(User user){
        UserExtraDataDTO dto = new UserExtraDataDTO();
        dto.setBudget(user.getBudget());
        dto.setBudgetPeriod(user.getBudgetPeriod());
        dto.setBudgetStartDate(user.getBudgetStartDate());
        dto.setBudgetEndDate(user.getBudgetEndDate());
        return dto;
    }
}

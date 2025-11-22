package com.darkdemon.backend.dto;

import com.darkdemon.backend.enums.BudgetPeriodEnum;
import com.darkdemon.backend.model.User;
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
public class UserResponseDTO {

    private Long id;

    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name must not exceeds 100 characters")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;

    @NotNull(message = "Budget is required")
    @Min(value = 0, message = "Budget must be greater than 0")
    private BigDecimal budget;

    @NotNull(message = "Budget period it required")
    private BudgetPeriodEnum budgetPeriod;

    @NotNull(message = "Budget start date is required")
    private LocalDate budgetStartDate;

    @NotNull(message = "Budget end date is required")
    private LocalDate budgetEndDate;

    private LocalDateTime createdAt;

//    @AssertTrue(message = "Budget end date must be after start date")
//    private boolean isEndDateValid() {
//        if (budgetStartDate == null || budgetEndDate == null) {
//            return true;
//        }
//        return budgetEndDate.isAfter(budgetStartDate);
//    }

    public UserResponseDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.budget = user.getBudget();
        this.budgetPeriod = user.getBudgetPeriod();
        this.budgetStartDate = user.getBudgetStartDate();
        this.budgetEndDate = user.getBudgetEndDate();
        this.createdAt = user.getCreatedAt();
    }

}

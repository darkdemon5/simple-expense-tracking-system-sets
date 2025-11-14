package com.darkdemon.backend.dto;

import com.darkdemon.backend.enums.PaymentMethodEnum;
import com.darkdemon.backend.model.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseDTO {

    private User user_id;
    @NotBlank(message = "Title cannot be empty")
    @Size(max = 200, message = "Title must not exceeds 200 characters")
    private String title;
    private String description;
    @NotBlank(message = "Category cannot be empty")
    private String category;
    @NotNull(message = "Expense amount cannot be empty")
    private BigDecimal expenseAmount;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    @NotNull(message = "Expense Date cannot be empty")
    private LocalDate expenseDate;
    @NotNull(message = "Payment Method cannot be empty")
    private PaymentMethodEnum paymentMethod;
    private Boolean isDeleted;
}

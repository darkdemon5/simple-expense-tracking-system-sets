package com.darkdemon.backend.model;

import com.darkdemon.backend.enums.PaymentMethodEnum;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@Table(name = "expenses")
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user_id;
    private String title;
    private String description;
    private String category;
    private BigDecimal amount;
    @Column(name = "created_date")
    private Date createdDate;
    @Column(name = "expense_date")
    private Date expenseDate;
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", columnDefinition = "payment_method_enum")
    private PaymentMethodEnum paymentMethod;
    @Column(name = "is_deleted")
    private Boolean isDeleted;
}

package com.darkdemon.backend.model;

import com.darkdemon.backend.enums.PaymentMethodEnum;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "expenses")
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user_id;
    private String title;
    private String description;
    private String category;
    private BigDecimal amount;
    @Column(name = "created_date")
    private LocalDateTime createdDate;
    @Column(name = "expense_date")
    private LocalDate expenseDate;
    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    @Column(name = "payment_method", columnDefinition = "payment_method_enum")
    private PaymentMethodEnum paymentMethod;
    @Column(name = "is_deleted")
    private Boolean isDeleted;
}

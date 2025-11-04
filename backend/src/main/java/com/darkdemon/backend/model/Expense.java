package com.darkdemon.backend.model;

import com.darkdemon.backend.enums.PaymentMethodEnum;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

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
    private String desc;
    private String category;
    private BigDecimal amount;
    private Date created_date;
    private Date expense_date;
    private PaymentMethodEnum payment_method;
    private Boolean is_deleted;
}

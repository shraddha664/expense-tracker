package com.saru.expensetracker.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "expenses")
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long expenseId;

    @Column(name = "amount")
    private Double  expenseAmount;

    @Column(name = "title")
    private String expenseTitle;

    @Column(name = "description")
    private String expenseDescription;

    @Column(name = "expense_date")
    private String expenseAddedDate;

    private Long userId;
}

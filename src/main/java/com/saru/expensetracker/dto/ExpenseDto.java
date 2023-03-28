package com.saru.expensetracker.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExpenseDto {
    private Double expenseAmount;
    private String expenseTitle;
    private String description;
}

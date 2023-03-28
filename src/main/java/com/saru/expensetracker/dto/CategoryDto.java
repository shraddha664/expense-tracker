package com.saru.expensetracker.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
public class CategoryDto {
    @NotNull(message = "Category name must not be null")
    @NotEmpty(message = "Category name not be empty")
    @NotBlank(message = "Category name not be blank ")
    private String categoryName;

}

package com.saru.expensetracker.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDto {

    @NotNull(message = "Account number must not be null")
    @Min(value = 1000, message = "Account number must have exactly 4 digits")
    @Max(value = 9999, message = "Account number must have exactly 4 digits")
    private Integer accountNumber;

    @NotNull(message = "Account type must not be null")
    @NotEmpty(message = "Account type must not be empty")
    @NotBlank(message = "Account type should not be blank ")
    @Pattern(regexp = "^[a-zA-Z]*$",message = "Account type should only contain alphabets")
    private String accountType;

    @NotNull(message = "Enter some amount")
    private Double balance;

}

package com.saru.expensetracker.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
public class LoginDto {
    @NotNull(message = "Username must not be null")
    @NotEmpty(message = "Username must not be empty")
    @NotBlank(message = "Username must not be blank ")
    private String username;

    @NotNull(message = "Password must not be null")
    @NotEmpty(message = "Password must not be empty")
    @NotBlank(message = "Password must not be blank ")
    private String password;
}

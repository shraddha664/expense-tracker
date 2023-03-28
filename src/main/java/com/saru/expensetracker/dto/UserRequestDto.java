package com.saru.expensetracker.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDto {
    @NotNull(message = "First name must not be null")
    @NotEmpty(message = "First name must not be empty")
    @NotBlank(message = "First name should not be blank ")
    @Pattern(regexp = "^[a-zA-Z]*$",message = "First name should only contain alphabets")
    private String firstName;

    @NotNull(message = "Last name must not be null")
    @NotEmpty(message = "Last name must not be empty")
    @NotBlank(message = "Last name should not be blank ")
    @Pattern(regexp = "^[a-zA-Z]*$",message = "Last name should only contain alphabets")
    private String lastName;

    @NotNull(message = "Username must not be null")
    @NotEmpty(message = "Username must not be empty")
    @NotBlank(message = "Username should not be blank ")
    @Pattern(regexp = "^[ A-Za-z0-9_@]*$",message = "Username cab be the alphabets,numbers,@ and _")
    private String username;

    @NotNull(message = "Account type must not be null")
    @NotEmpty(message = "Account type must not be empty")
    @NotBlank(message = "Account type should not be blank ")
    @Pattern(regexp = "^[ A-Za-z0-9_@./#&+-]*$",message = "Account type should only contain alphabets")
    private String password;

}

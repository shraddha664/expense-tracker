package com.saru.expensetracker.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDto {
    @NotNull(message = "First name must not be null")
    @NotEmpty(message = "First name must not be empty")
    @NotBlank(message = "First name should not be blank ")
    @Pattern(regexp = "^[a-zA-Z]*$",message = "First name should only contain alphabets")
    @Size(min = 2,max = 20)
    private String firstName;

    @NotNull(message = "Last name must not be null")
    @NotEmpty(message = "Last name must not be empty")
    @NotBlank(message = "Last name should not be blank ")
    @Pattern(regexp = "^[a-zA-Z]*$",message = "Last name should only contain alphabets")
    @Size(min = 2,max = 20)
    private String lastName;

    @NotNull(message = "Username must not be null")
    @NotEmpty(message = "Username must not be empty")
    @NotBlank(message = "Username should not be blank ")
    @Pattern(regexp = "^[ A-Za-z0-9_@]*$",message = "Username cab be the alphabets,numbers,@ and _")
    @Size(min = 2,max = 20)
    private String username;

    @NotNull(message = "Account type must not be null")
    @NotEmpty(message = "Account type must not be empty")
    @NotBlank(message = "Account type should not be blank ")
    @Pattern(regexp = "^[ A-Za-z0-9_@./#&+-]*$",message = "Account type should only contain alphabets")
    @Size(min = 8,max = 20)
    private String password;

}

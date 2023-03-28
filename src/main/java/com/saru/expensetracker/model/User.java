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
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name ="f_name")
    @NotEmpty
    @NotNull
    @Size(min = 2,max = 30)
    private String firstName;

    @Column(name = "l_name")
    @NotEmpty
    @NotNull
    @Size(min = 2,max = 30)
    private String lastName;

    @Column(name = "user_name")
    @NotEmpty
    @NotNull
    @Size(min = 2,max = 30)
    private String username;

    @Column(name = "password")
    @NotEmpty
    @NotNull
    @Size(min = 2)
    private String password;


}

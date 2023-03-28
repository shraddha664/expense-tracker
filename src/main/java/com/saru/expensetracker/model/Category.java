package com.saru.expensetracker.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    @Column(name = "category_name")
    @NotEmpty
    @NotNull
    @Size(min = 2,max = 500)
    private String categoryName;

    @OneToMany(fetch = FetchType.LAZY,targetEntity = Expense.class,cascade = CascadeType.ALL)
    @JoinColumn(name = "FK_category_id",referencedColumnName = "categoryId")
    private List<Expense> expenses;


}

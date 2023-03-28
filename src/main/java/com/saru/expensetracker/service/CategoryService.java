package com.saru.expensetracker.service;

import com.saru.expensetracker.dto.CategoryDto;
import com.saru.expensetracker.dto.ExpenseDto;
import com.saru.expensetracker.exceptions.ExpenseTrackerExceptions;
import com.saru.expensetracker.model.Category;
import com.saru.expensetracker.model.Expense;
import com.saru.expensetracker.repository.CategoryRepository;
import com.saru.expensetracker.repository.ExpenseRepository;
import com.saru.expensetracker.validators.Violations;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ExpenseRepository expenseRepository;
    private final Violations validationConfig;

    @Transactional
    public void addCategory(Long userId, CategoryDto categoryDto) {
        validationConfig.violationsMessage(categoryDto);
        categoryRepository.save(Category.builder()
                .categoryName(categoryDto.getCategoryName())
                .build());
    }

    @Transactional
    public void addExpenseInCategory(Long userId,Long categoryId, ExpenseDto expenseDto) {
        Category category= categoryRepository.findById(categoryId).orElseThrow(()->new ExpenseTrackerExceptions("No such category exists"));

        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        List<Expense> expense=category.getExpenses();
        expense.add(Expense.builder()
                .userId(userId)
                .expenseTitle(expenseDto.getExpenseTitle())
                .expenseAmount(expenseDto.getExpenseAmount())
                .expenseDescription(expenseDto.getDescription())
                .expenseAddedDate(dateFormat.format(new Date(System.currentTimeMillis())).toString())
                .build());
        category.setExpenses(expense);
        categoryRepository.save(category);
        log.info(String.format("date->%s",dateFormat.format(new Date(System.currentTimeMillis())).toString()));

    }

    @Transactional
    public void deleteExpense( Long expenseId) {
        log.info("Inside deleteExpense of service");
        Expense expense=expenseRepository.findById(expenseId).orElseThrow(()->new ExpenseTrackerExceptions("No such expense exists"));
        expenseRepository.delete(expense);

    }

    @Transactional
    public void updateExpense(ExpenseDto expenseDto, Long expenseId) {
        Expense expense=  expenseRepository.findById(expenseId).orElseThrow(()->new ExpenseTrackerExceptions("No such expense exists"));
        log.info(String.format("expense->%s",expense));
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");

        expenseRepository.save(Expense.builder()
                .expenseTitle(expenseDto.getExpenseTitle())
                .expenseAddedDate(dateFormat.format(System.currentTimeMillis()))
                .expenseAmount(expenseDto.getExpenseAmount())
                .expenseDescription(expenseDto.getDescription()).build());

        log.info(String.format("updatedExpense->%s",expense));

    }

    @Transactional(readOnly = true)
    public List<Category> fetchAllCategories() {

        return categoryRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Category fetchSpecificCategory(Long categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(()->new ExpenseTrackerExceptions("No expenses found in this category"));
    }
}
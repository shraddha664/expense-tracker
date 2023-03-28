package com.saru.expensetracker.controller;

import com.saru.expensetracker.dto.CategoryDto;
import com.saru.expensetracker.dto.ExpenseDto;
import com.saru.expensetracker.model.Category;
import com.saru.expensetracker.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("/user/{userId}/category")
    @Operation(summary = "Add category by  userId")
    public ResponseEntity<String> addCategory(@PathVariable("userId") Long userId, @RequestBody CategoryDto categoryDto){

        log.info("inside addCategory of controller ");
        categoryService.addCategory(userId,categoryDto);
        return  ResponseEntity.ok("Category saved successfully");
    }

    @PostMapping("/user/{userId}/category/{categoryId}/expense")
    @Operation(summary = "Add Expense in any category")
    public ResponseEntity<String> addExpenseInCategory(@PathVariable("userId") Long userId,@PathVariable("categoryId") Long categoryId,@RequestBody ExpenseDto expenseDto){
        categoryService.addExpenseInCategory(userId,categoryId,expenseDto);
        return  ResponseEntity.ok("Expense added successfully");
    }

    @DeleteMapping("/user/expense/{expenseId}")
    @Operation(summary = "Delete expense by  expenseId")
    public ResponseEntity<String> deleteExpense(@PathVariable("expenseId") Long expenseId){
        log.info("Inside deleteExpense of controller");
        categoryService.deleteExpense(expenseId);
        return  ResponseEntity.ok("Expense deleted successfully");
    }

    @PutMapping("/user/expense/{expenseId}")
    @Operation(summary = "Update expense by  expenseId")
    public ResponseEntity<String> updateExpense(@RequestBody ExpenseDto expenseDto,@PathVariable("expenseId") Long expenseId){
        categoryService.updateExpense(expenseDto,expenseId);
        return  ResponseEntity.ok("Expense Updated successfully");
    }

    @GetMapping
    @Operation(summary = "Get all categories")
    public List<Category> fetchAllCategories(){
        return categoryService.fetchAllCategories();
    }


    @GetMapping("/user/category/{categoryId}")
    @Operation(summary = "Get a category with all expenses in it using categoryId")
    public Category fetchSpecificCategory(@PathVariable("categoryId") Long categoryId){
        return categoryService.fetchSpecificCategory(categoryId);
    }

}

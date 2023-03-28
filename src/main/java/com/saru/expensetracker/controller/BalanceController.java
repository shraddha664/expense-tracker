package com.saru.expensetracker.controller;

import com.saru.expensetracker.dto.AccountDto;
import com.saru.expensetracker.model.Account;
import com.saru.expensetracker.service.AccountService;
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
public class BalanceController {
    private final AccountService accountService;


    @PostMapping("/user/{userId}/account")
    @Operation(summary = "Add account using userId")
    public ResponseEntity<String> addAccount(@RequestBody AccountDto balanceDto, @PathVariable("userId") Long userId){
        log.info("Inside addAccount of user controller");
        accountService.saveAccount(balanceDto, userId);
        return  ResponseEntity.ok("Account added successfully");
    }

    @PatchMapping("/user/{userId}/account/{accountId}")
    @Operation(summary = "Update account using accountId")
    public ResponseEntity<AccountDto> updateAccount(@PathVariable("userId") Long userId, @PathVariable("accountId") Long accountId, @RequestBody AccountDto accountDto){
        return ResponseEntity.ok(accountService.updateAccount(userId,accountId,accountDto)) ;
    }

    @DeleteMapping("/user/{userId}/account/{accountId}")
    @Operation(summary = "Delete account using accountId")
    public ResponseEntity<String> deleteAccount(@PathVariable("userId") Long userId, @PathVariable("accountId") Long accountId){
        accountService.deleteAccount(userId,accountId);
        return ResponseEntity.ok("Account deleted successfully") ;
    }

    @GetMapping("/user/{userId}/account")
    @Operation(summary = "Get accounts detail using accountId")
    public ResponseEntity<List<AccountDto>> getAllAccount(@PathVariable("userId") Long userId){
        return ResponseEntity.ok(accountService.getAllAccount(userId));
    }

    @GetMapping("/user/{userId}/balance")
    @Operation(summary = "Get the total balance in accounts and show the remaining balance by userId")
    public ResponseEntity<String> getRemainingBalance(@PathVariable("userId") Long userId){
        return ResponseEntity.ok(accountService.getRemainingBalance(userId));
    }

    @GetMapping("/category/{categoryId}")
    @Operation(summary = "Get the total expense in a specific category by categoryId")
    public ResponseEntity<String>  getTotalExpenseByExpense(@PathVariable("categoryId") Long categoryId){
        return ResponseEntity.ok(accountService.getTotalExpenseByExpense(categoryId));
    }

}

package com.saru.expensetracker.controller;

import com.saru.expensetracker.dto.*;
import com.saru.expensetracker.exceptions.ExpenseTrackerExceptions;
import com.saru.expensetracker.model.Account;
import com.saru.expensetracker.model.Category;
import com.saru.expensetracker.model.User;
import com.saru.expensetracker.repository.ExpenseRepository;
import com.saru.expensetracker.service.AccountService;
import com.saru.expensetracker.service.CategoryService;
import com.saru.expensetracker.service.JwtService;
import com.saru.expensetracker.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;


    @Operation(summary = "Save user")
    @PostMapping("/user")
    public ResponseEntity<String> saveUser(@RequestBody UserRequestDto userRequestDto){
        log.info("inside controller");
        userService.saveUser(userRequestDto);
        return  ResponseEntity.ok("User saved successfully");
    }

    @PostMapping("/user/login")
    @Operation(summary = "Extract login information of user")
    public ResponseEntity<String> loginUser(@RequestBody LoginDto loginDto) throws ExpenseTrackerExceptions {
        User user= userService.findUser(loginDto);
        log.info("inside  loginUser controller");
        Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(),loginDto.getPassword()));
        if (authentication.isAuthenticated()) {
            log.info(String.format("user->%s",user.toString()));
            return ResponseEntity.ok("Successfully logged in.Your token is :"+jwtService.generateToken(loginDto.getUsername()));
        }else {
            throw new ExpenseTrackerExceptions("User not found");
        }
    }

}





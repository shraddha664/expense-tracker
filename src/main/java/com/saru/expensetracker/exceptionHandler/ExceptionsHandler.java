package com.saru.expensetracker.exceptionHandler;

import com.saru.expensetracker.exceptions.ErrorResponse;
import com.saru.expensetracker.exceptions.ExpenseTrackerExceptions;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.Date;

@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionsHandler {


    @ExceptionHandler(ExpenseTrackerExceptions.class)
    @ResponseStatus(value=HttpStatus.BAD_REQUEST)
    public ErrorResponse badRequestCredentialsExceptionHandler(ExpenseTrackerExceptions exception, HttpServletRequest servletRequest){
        return ErrorResponse.builder()
                .message(exception.getMessage())
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .timestamp(new Date(System.currentTimeMillis()))
                .path(servletRequest.getRequestURI())
                .build();
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(value=HttpStatus.BAD_REQUEST)
    public ErrorResponse messageNotReadableValidationExceptionHandler(HttpMessageNotReadableException exception, HttpServletRequest servletRequest){
        return ErrorResponse.builder()
                .message("Account number,balance and account type should not be empty")
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .timestamp(new Date(System.currentTimeMillis()))
                .path(servletRequest.getRequestURI())
                .build();
    }

}

package com.saru.expensetracker.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ErrorResponse {
    private int statusCode;
    private String message;
    private Date timestamp;
    private String path;

}

package com.example.librarymanagementsystem.dto;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ApiErrorResponse {
    private HttpStatus status;

    private String message;

    private List<ApiError> errors;

    @Override
    public String toString() {
        return "Status: " + status + ", Message: " + message + ", Errors: " + errors;
     }
}

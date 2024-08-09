package com.example.librarymanagementsystem.controller.exceptionHandler;

import com.example.librarymanagementsystem.dto.ApiError;
import com.example.librarymanagementsystem.dto.ApiErrorResponse;
import com.example.librarymanagementsystem.exception.ErrorTextProvider;
import com.example.librarymanagementsystem.exception.ValidationBaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import jakarta.validation.ConstraintViolation;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class CommonValidationExceptionHandler {

    protected ResponseEntity<Object> prepareBadRequestResponseEntity(String logMessage, ValidationBaseException ex) {
        List<ApiError> errors = ConstructErrorsList(ex);

        ApiErrorResponse apiError = new ApiErrorResponse(HttpStatus.BAD_REQUEST, ex.getErrorText(), errors);

        log.error(logMessage, apiError);
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }
    
    private List<ApiError> ConstructErrorsList(ValidationBaseException ex) {
        if (ex.getErrors() == null) {
            return List.of(new ApiError(ex.getErrorId(), new ErrorTextProvider().getMessage(ex.getErrorId(), ex.getParams())));
        }
        return ex.getErrors().stream()
                .map(constraintViolation -> constructErrorMessage(ex, constraintViolation))
                .collect(Collectors.toList());
    }

    private ApiError constructErrorMessage(ValidationBaseException ex, ConstraintViolation<?> constraintViolation) {
        String message = constraintViolation.getConstraintDescriptor().getAttributes().get("message").toString().replace("{", "").replace("}", "");
        if(message.isEmpty()) {
            message = constraintViolation.getMessage();
        }
        if (message.isEmpty()) {
            return new ApiError(constraintViolation.getMessage(), ex.getErrorText(constraintViolation.getMessage()));
        }

        return new ApiError( message, new ErrorTextProvider().getMessage(message));
    }
}

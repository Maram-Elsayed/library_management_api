package com.example.librarymanagementsystem.controller.exceptionHandler;

import com.example.librarymanagementsystem.exception.BaseValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class BaseValidationExceptionHandler extends CommonValidationExceptionHandler {

    @ExceptionHandler(BaseValidationException.class)
    protected ResponseEntity<Object> handleCustomException(BaseValidationException exception) {
        return prepareBadRequestResponseEntity("BaseValidationException:: Validation Error", exception);
    }
}

package com.example.librarymanagementsystem.exception;

import jakarta.validation.ConstraintViolation;
import java.util.List;

public interface ValidationBaseException {

    String getErrorText(String key, String... parameter);

    List<ConstraintViolation<?>> getErrors();

    String getErrorText();

    String getErrorId();

    String[] getParams();
}
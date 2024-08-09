package com.example.librarymanagementsystem.exception;

import jakarta.validation.ConstraintViolation;

import java.util.Collections;
import java.util.List;

public class BaseValidationException extends Exception implements ValidationBaseException {
    protected List<ConstraintViolation<?>> errors;
    protected String[] params;

    protected String errorId;
    protected static final ErrorTextProvider ERROR_TEXT_PROVIDER = new ErrorTextProvider();

    public BaseValidationException(String errorId, String... parameters) {
        super(ERROR_TEXT_PROVIDER.getMessage(errorId, parameters));
        this.errorId = errorId;
        this.params = parameters;
    }

    public BaseValidationException(final String errorId, final  List<ConstraintViolation<?>> violations) {
        super(errorId);
        errors = Collections.unmodifiableList(violations);
    }

    @Override
    public String getErrorId() {
        return errorId;
    }

    @Override
    public String[] getParams() {
        return params;
    }

    @Override
    public String getErrorText(String errorId, String... parameter) {
        return ERROR_TEXT_PROVIDER.getMessage(errorId, parameter);
    }

    @Override
    public List<ConstraintViolation<?>> getErrors() {
        return errors;
    }

    @Override
    public String getErrorText() {
        return super.getMessage();
    }

}

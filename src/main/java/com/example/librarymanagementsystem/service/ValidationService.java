package com.example.librarymanagementsystem.service;

import org.springframework.beans.factory.annotation.Autowired;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ValidationService {

    @Autowired
    private Validator validator;

    public List<ConstraintViolation<?>> validateNotNull(Object... objects) {
        return Arrays.stream(objects).filter(Objects::nonNull).map(object -> validator.validate(object))
                .flatMap(Collection::stream).collect(Collectors.toList());
    }

    public boolean isValidateAttributeLength(String attribute, int maxLength) {
        if(attribute.length() <= maxLength) {
            return true;
        }
        return false;
    }
}
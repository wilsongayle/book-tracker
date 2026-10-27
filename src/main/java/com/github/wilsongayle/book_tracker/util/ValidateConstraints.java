package com.github.wilsongayle.book_tracker.util;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;

import java.util.Set;

public class ValidateConstraints {
    public static<T> void validateOrThrow(Validator validator, T entity) {
        Set<ConstraintViolation<T>> violations = validator.validate(entity);
        if(!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}

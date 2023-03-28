package com.saru.expensetracker.validators;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ObjectValidator {
    private final ValidatorFactory factory= Validation.buildDefaultValidatorFactory();
    private final Validator validator= factory.getValidator();

    public Set<String>validate(Object object) {
        Set<ConstraintViolation<Object>> violation = validator.validate(object);
        if (!violation.isEmpty()) {
            return violation.stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.toSet());
        }
        return Collections.emptySet();
    }

}

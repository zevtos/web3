package ru.zevtos.webserver.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Set;

/**
 * Валидатор для проверки допустимых значений радиуса R.
 */
public class ValidRValidator implements ConstraintValidator<ValidR, Double> {
    private final Set<Double> validRValues = Set.of(1.0, 2.0, 3.0, 4.0, 5.0);

    @Override
    public boolean isValid(Double value, ConstraintValidatorContext context) {
        return value != null && validRValues.contains(value);
    }
}

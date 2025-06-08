package ru.yandex.practicum.filmorate.validate;

import jakarta.validation.Constraint;
import jakarta.validation.constraints.Past;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MinimumDateValidator.class)
@Past
public @interface MinimumDate {
    String message() default "Дата должна быть больше {value}";
    Class<?>[] groups() default {};
    Class<?>[] payload() default {};
    String value();
}

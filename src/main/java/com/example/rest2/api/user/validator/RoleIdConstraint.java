package com.example.rest2.api.user.validator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = RoleIdConstraintValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.FIELD})
public @interface RoleIdConstraint {
    String message() default "RoleId is not exist";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default {};
}

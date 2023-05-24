package com.example.rest2.api.user.validator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = PasswordMatchConstrainValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordMatch {

    String message() default "Password is not strong";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default {};

    String password();
    String confirmedPassword();

    @Target({ TYPE })
    @Retention(RUNTIME)
    public @interface List {
        PasswordMatch[] value();
    }
}

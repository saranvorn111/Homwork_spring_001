package com.example.rest2.api.user.validator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

public class PasswordMatchConstrainValidator implements ConstraintValidator<PasswordMatch, Object> {

    private String password;
    private String confirmPassword;
    private String message;
    @Override
    public void initialize(PasswordMatch constraintAnnotation) {
        this.password = constraintAnnotation.password();
        this.confirmPassword=constraintAnnotation.confirmedPassword();
        this.message=constraintAnnotation.message();
    }
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
       Object passwordValue = new BeanWrapperImpl(value).getPropertyValue(password);
       Object confirmedPasswordValue = new BeanWrapperImpl(value).getPropertyValue(confirmPassword);

       boolean isValid = false;

       if(passwordValue !=null){
           isValid = passwordValue.equals(confirmedPasswordValue);
       }
       if(!isValid){
           context.buildConstraintViolationWithTemplate(message)
                   .addPropertyNode(password)
                   .addConstraintViolation()
                   .disableDefaultConstraintViolation();

           context.buildConstraintViolationWithTemplate(message)
                   .addPropertyNode(confirmPassword)
                   .addConstraintViolation()
                   .disableDefaultConstraintViolation();
       }

       return isValid;
    }


}

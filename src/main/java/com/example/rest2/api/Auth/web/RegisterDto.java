package com.example.rest2.api.Auth.web;

import com.example.rest2.api.user.validator.EmailUnique;
import com.example.rest2.api.user.validator.Password;
import com.example.rest2.api.user.validator.PasswordMatch;
import com.example.rest2.api.user.validator.RoleIdConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;
@PasswordMatch( message = "Your password is not match", password ="password", confirmedPassword="confirmedPassword")
public record RegisterDto(
                        @NotBlank(message = "Email is required.!")
                        @EmailUnique
                        @Email
                        String email,
                        @Password
                        @NotBlank(message = "Password is required.!")
                        String password,
                        @Password
                        @NotBlank(message = "Confirmed password is required.!")
                        String confirmedPassword,
                        @NotNull(message = "Roles are required!")
                        @RoleIdConstraint
                        List<Integer> roleIds) {
}

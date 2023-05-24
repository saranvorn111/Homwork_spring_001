package com.example.rest2.api.Auth.web;

import com.example.rest2.api.user.validator.Password;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LogInDto(@Email
                        @NotBlank
                        String email,
                       @NotBlank
                       @Password
                       String password) {
}

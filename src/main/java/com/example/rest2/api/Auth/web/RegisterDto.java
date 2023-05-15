package com.example.rest2.api.Auth.web;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterDto(
                        @NotBlank(message = "Email is required.!")
                        @Email
                        String email,
                        @NotBlank(message = "Password is required.!")
                        String password,
                        @NotBlank(message = "Confirmed password is required.!")
                        String confirmedPassword) {
}

package com.example.rest2.api.Auth.web;

public record AuthDto(String tokenType,
                      String accessToken,
                      String refreshToken) {
}

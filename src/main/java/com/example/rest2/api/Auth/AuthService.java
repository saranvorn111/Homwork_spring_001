package com.example.rest2.api.Auth;

import com.example.rest2.api.Auth.web.RegisterDto;

public interface AuthService {
    void register(RegisterDto registerDto);

    void verify(String email);
}

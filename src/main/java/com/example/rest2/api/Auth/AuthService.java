package com.example.rest2.api.Auth;

import com.example.rest2.api.Auth.web.AuthDto;
import com.example.rest2.api.Auth.web.LogInDto;
import com.example.rest2.api.Auth.web.RegisterDto;
import com.example.rest2.api.Auth.web.TokenDto;

public interface AuthService {

    AuthDto refreshToken(TokenDto tokenDto);

    AuthDto login(LogInDto loginDto);

    void register(RegisterDto registerDto);

    void verify(String email);

    void checkVerify(String email, String verifiedCode);
}

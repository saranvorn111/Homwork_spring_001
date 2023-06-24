package com.example.rest2.api.Auth;

import com.example.rest2.api.Auth.web.AuthDto;
import com.example.rest2.api.Auth.web.LogInDto;
import com.example.rest2.api.Auth.web.RegisterDto;
import com.example.rest2.api.Auth.web.TokenDto;
import com.example.rest2.base.BaseRest;
import com.fasterxml.jackson.databind.ser.Serializers;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthRestController {
    private final AuthService authService;


    @PostMapping("/refresh")
    public BaseRest<?> refreshToken(@RequestBody TokenDto tokenDto){
        AuthDto authDto = authService.refreshToken(tokenDto);
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Token have been refreshed")
                .timestamp(LocalDateTime.now())
                .date(authDto)
                .build();
    }
    @PostMapping("/login")
    public BaseRest<?> login(@Valid @RequestBody LogInDto logIngDto ){
        //call service
        AuthDto authDto = authService.login(logIngDto);
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("You have been Login successfully")
                .timestamp(LocalDateTime.now())
                .date(authDto)
                .build();
    }

    //call service
    @PostMapping("/register")
    public BaseRest<?> register(@Valid @RequestBody RegisterDto registerDto){

        //call service
        authService.register(registerDto);
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("You have been register successfully")
                .timestamp(LocalDateTime.now())
                .date(registerDto.email())
                .build();
    }

    @PostMapping("/verify")
    public BaseRest<?> verify(@RequestParam String email){
        authService.verify(email);
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("You have been send successfully")
                .timestamp(LocalDateTime.now())
                .date(email)
                .build();
    }
    @GetMapping("/check-verify")
    public BaseRest<?> checkVerify(@RequestParam String email,
                                   @RequestParam String verifiedCode ){

        authService.checkVerify(email,verifiedCode);
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("You have been verified successfully")
                .timestamp(LocalDateTime.now())
                .date(email)
                .build();

    }
}

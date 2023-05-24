package com.example.rest2.api.Auth;
import com.example.rest2.api.Auth.web.AuthDto;
import com.example.rest2.api.Auth.web.LogInDto;
import com.example.rest2.api.Auth.web.RegisterDto;
import com.example.rest2.api.user.User;
import com.example.rest2.api.user.UserMapStruct;
import com.example.rest2.util.MailUtil;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Base64;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImp implements  AuthService{
    private final AuthMapper authMapper;
    private final UserMapStruct userMapStruct;
    private final PasswordEncoder encoder;
    private final MailUtil mailUtil;
    private final DaoAuthenticationProvider daoAuthenticationProvider;
    @Value("${spring.mail.username}")
    private String appMail;

    @Override
    public AuthDto login(LogInDto loginDto) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(loginDto.email(),loginDto.password());

        authentication= daoAuthenticationProvider.authenticate(authentication);

        log.info("Authentication: {}",authentication);
        log.info("Authentication: {}",authentication.getName());
        log.info("Authentication: {}",authentication.getCredentials());

        //Login on basic authorization header
        String basicAuthFormat = authentication.getName() + ":"+ authentication.getCredentials();
        String encoding = Base64.getEncoder().encodeToString(basicAuthFormat.getBytes());

        log.info("Basic {}",encoding);
        return new AuthDto(String.format("Basic %s",encoding));
    }

    @Transactional
    @Override
    public void register(RegisterDto registerDto) {
        User user = userMapStruct.registerDtoToUser(registerDto);
        user.setIsVerified(false);
        user.setPassword(encoder.encode(user.getPassword()));

        log.info("User:{}",user.getEmail());
        if(authMapper.register(user)){
            //create user rol
            for(Integer role: registerDto.roleIds()){
                authMapper.createUserRole(user.getId(),role);


            }
        }
    }

    @Override
    public void verify(String email) {

        User user = authMapper.selectByEmail(email).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Email has not been found"));

        String verifiedCode =UUID.randomUUID().toString();
        if(authMapper.updateVerifiedCode(email,verifiedCode)){
            user.setVerifiedCode(verifiedCode);
        }else{
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "User can not be Verified");
        }


        MailUtil.Mata<?> meta = MailUtil.Mata.builder()
                .to(email)
                .from(appMail)
                .subject(" Account Verification")
                .templateUrl("auth/verify")
                .data(user)
                .build();
        try {
            mailUtil.send(meta);
        } catch (MessagingException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    e.getMessage());
        }

    }

    @Override
    public void checkVerify(String email, String verifiedCode) {
        System.out.println(email);
        System.out.println(verifiedCode);
        User user=authMapper.selectByEmailAndVerifiedCode(email,verifiedCode)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"User didn't existed in database"));
        System.out.println(user);
        if(!user.getIsVerified()){
            authMapper.verify(email,verifiedCode);
        }
    }
}

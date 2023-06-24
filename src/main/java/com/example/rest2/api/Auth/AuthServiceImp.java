package com.example.rest2.api.Auth;
import com.example.rest2.api.Auth.web.AuthDto;
import com.example.rest2.api.Auth.web.LogInDto;
import com.example.rest2.api.Auth.web.RegisterDto;
import com.example.rest2.api.Auth.web.TokenDto;
import com.example.rest2.api.user.User;
import com.example.rest2.api.user.UserMapStruct;
import com.example.rest2.util.MailUtil;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImp implements  AuthService{
    private final AuthMapper authMapper;
    private final UserMapStruct userMapStruct;
    private final PasswordEncoder encoder;
    private final MailUtil mailUtil;
    private final DaoAuthenticationProvider daoAuthenticationProvider;
    private final JwtEncoder jwtAccessTokenEncoder;
    private  JwtEncoder jwtRefreshTokenEncoder;

    @Autowired
    public void setJwtRefreshTokenEncoder(JwtEncoder jwtRefreshTokenEncoder){
        this.jwtRefreshTokenEncoder=jwtRefreshTokenEncoder;
    }
    @Value("${spring.mail.username}")
    private String appMail;

    @Override
    public AuthDto refreshToken(TokenDto tokenDto) {
        return null;
    }

    @Override
    public AuthDto login(LogInDto loginDto) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(loginDto.email(),loginDto.password());

        authentication= daoAuthenticationProvider.authenticate(authentication);

        //create time now
        Instant now = Instant.now();

        //Define Scope
        String scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .filter(auth -> !auth.startsWith("ROLE_"))
                .collect(Collectors.joining(" "));

//        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
//        authorities.add(new SimpleGrantedAuthority("WRITE") );
//        authorities.add(new SimpleGrantedAuthority("READ") );
//        authorities.add(new SimpleGrantedAuthority("DELETE") );
//        authorities.add(new SimpleGrantedAuthority("UPDATE") );
//        authorities.add(new SimpleGrantedAuthority("FULL_CONTROL") );
//
//        String scope = authorities.stream()
//                .map(SimpleGrantedAuthority::getAuthority)
//                .collect(Collectors.joining(" "));



        JwtClaimsSet jwtAccessTokenClaimsSet = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .subject(authentication.getName())
                .expiresAt(now.plus(1, ChronoUnit.SECONDS))
                .claim("scope",scope)
                .build();

        JwtClaimsSet jwtRefreshTokenClaimsSet = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .subject(authentication.getName())
                .expiresAt(now.plus(1, ChronoUnit.HOURS))
                .claim("scope",scope)
                .build();


        String accessToken = jwtAccessTokenEncoder.encode(JwtEncoderParameters.from(jwtAccessTokenClaimsSet)
                ).getTokenValue();

        String refreshToken = jwtRefreshTokenEncoder.encode(JwtEncoderParameters.from(jwtRefreshTokenClaimsSet)
        ).getTokenValue();




        return new AuthDto("Bearer",
                accessToken,
                refreshToken);
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

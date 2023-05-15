package com.example.rest2.api.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
   private final PasswordEncoder encoder;
    //Define in-memory user
    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();
        UserDetails admin = User.builder()
                .username("admin")
                .password(encoder.encode("123"))
                .roles("ADMIN")
                .build();
        UserDetails goldUser = User.builder()
                .username("gold")
                .password(encoder.encode("123"))
                .roles("ADMIN","ACCOUNT")
                .build();
        UserDetails user = User.builder()
                        .username("user")
                        .password(encoder.encode("123"))
                        .roles("USER")
                        .build();
        userDetailsManager.createUser(admin);
        userDetailsManager.createUser(goldUser);
        userDetailsManager.createUser(user);
        return userDetailsManager;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //Disable CSRF
        http.csrf(token->token.disable());

        //Authorize URL Mapping
        http.authorizeHttpRequests(request ->{
            request.requestMatchers("/api/v1/users/**").hasRole("ADMIN");
            request.requestMatchers("/api/v1/account-types/**","/api/v1/files/**").hasAnyRole("ACCOUNT","USER");
            request.anyRequest().permitAll();
        });



        //Security mechanism
        http.httpBasic();

        //Make security http STATELESS
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return http.build();
    }

}

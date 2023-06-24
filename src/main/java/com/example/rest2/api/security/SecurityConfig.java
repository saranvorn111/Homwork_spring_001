package com.example.rest2.api.security;

import com.example.rest2.util.KeyUtil;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.KeySourceException;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSelector;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.util.List;
import java.util.UUID;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    private final CustomAccessDeniedHandle customAccessDeniedHandle;

    private final KeyUtil keyUtil;
//   private final PasswordEncoder encoder;
    //Define in-memory user
//    @Bean
//    public InMemoryUserDetailsManager userDetailsService() {
//        InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password(encoder.encode("123"))
//                .roles("ADMIN")
//                .build();
//        UserDetails goldUser = User.builder()
//                .username("gold")
//                .password(encoder.encode("123"))
//                .roles("ADMIN","ACCOUNT")
//                .build();
//        UserDetails user = User.builder()
//                        .username("user")
//                        .password(encoder.encode("123"))
//                        .roles("USER")
//                        .build();
//        userDetailsManager.createUser(admin);
//        userDetailsManager.createUser(goldUser);
//        userDetailsManager.createUser(user);
//        return userDetailsManager;
//    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter(){
       // wtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
    //    grantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");

        //    jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
        return new JwtAuthenticationConverter();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();

        auth.setUserDetailsService(userDetailsService);
        auth.setPasswordEncoder(passwordEncoder);
        return auth;
    }

    @Bean("jwtRefreshTokenAuthProvider")
    public JwtAuthenticationProvider jwtRefreshTokenAuthProvider() throws NoSuchAlgorithmException,JOSEException {
        JwtAuthenticationProvider provider = new JwtAuthenticationProvider(jwtAccessTokenDecoder());
        provider.setJwtAuthenticationConverter(jwtAuthenticationConverter());
        return provider;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //Disable CSRF
        http.csrf(token->token.disable());

        //Authorize URL Mapping
//        http.authorizeHttpRequests(request ->{
//            request.requestMatchers("/api/v1/auth/**").permitAll();
//            request.requestMatchers("/api/v1/users/**").hasAnyRole("ADMIN","SYSTEM");
//            request.requestMatchers("/api/v1/users/**").hasRole("CUSTOMER");
//            request.requestMatchers("/api/v1/account-types/**","/api/v1/files/**").hasAnyRole("CUSTOMER","ADMIN");
//            request.anyRequest().authenticated();
//        });
        http.authorizeHttpRequests(auth -> {
            auth.requestMatchers("/api/v1/auth/**").permitAll();
            auth.requestMatchers(HttpMethod.GET,"/api/v1/users/**").hasAuthority("SCOPE_user:read");
            auth.requestMatchers(HttpMethod.POST,"/api/v1/users/**").hasAuthority("SCOPE_user:write");
            auth.requestMatchers(HttpMethod.DELETE,"/api/v1/users/**").hasAuthority("SCOPE_user:delete");
            auth.requestMatchers(HttpMethod.PUT,"/api/v1/users/**").hasAuthority("SCOPE_user:update");
//            auth.requestMatchers(HttpMethod.GET,"/api/v1/account-type/**").hasAuthority("SCOPE_account:read");
            auth.anyRequest().authenticated();

        });



        //Security mechanism
//        http.httpBasic();
        http.oauth2ResourceServer(oauth2 ->oauth2.jwt(
                jwt->jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())
        ));

        //Make security http STATELESS
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));


        http.exceptionHandling(ex
                ->ex.authenticationEntryPoint(customAuthenticationEntryPoint));
        return http.build();

    }
    @Bean
    @Primary
    public JwtDecoder jwtAccessTokenDecoder() {
        return NimbusJwtDecoder.withPublicKey(keyUtil.getAccessTokenPublicKey()).build();

    }
    @Bean(name="jwtRefreshTokenDecoder")
    public JwtDecoder jwtRefreshTokenDecoder() {
        return NimbusJwtDecoder.withPublicKey(keyUtil.getRefreshTokenPublicKey()).build();

    }
    @Bean
    @Primary
    public JwtEncoder jwtAccessTokenEncoder(){

        JWK jwk = new RSAKey
                .Builder(keyUtil.getAccessTokenPublicKey())
                .privateKey(keyUtil.getAccessTokenPrivateKey())
                .build();
        JWKSet jwkSet = new JWKSet(jwk);

        return new NimbusJwtEncoder(new JWKSource<SecurityContext>() {
            @Override
            public List<JWK> get(JWKSelector jwkSelector, SecurityContext context) throws KeySourceException {
                return jwkSelector.select(jwkSet);
            }
        }
        );
    }
    @Bean(name="jwtRefreshTokenEncoder")
    public JwtEncoder jwtRefreshTokenEncoder(){

        JWK jwk = new RSAKey
                .Builder(keyUtil.getRefreshTokenPublicKey())
                .privateKey(keyUtil.getRefreshTokenPrivateKey())
                .build();
        JWKSet jwkSet = new JWKSet(jwk);

        return new NimbusJwtEncoder(new JWKSource<SecurityContext>() {
            @Override
            public List<JWK> get(JWKSelector jwkSelector, SecurityContext context) throws KeySourceException {
                return jwkSelector.select(jwkSet);
            }
        }
        );
    }
//    @Bean
//    public KeyPair keyPair() throws NoSuchAlgorithmException {
//        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
//        keyPairGenerator.initialize(2048);
//        return keyPairGenerator.generateKeyPair();
//
//    }

//    @Bean
//    public RSAKey rsaKey(KeyPair keyPair){
//        return new RSAKey.Builder((RSAPublicKey) keyPair.getPublic())
//                .privateKey(keyPair.getPrivate())
//                .keyID(UUID.randomUUID().toString())
//                .build();
//    }

//    @Bean
//    public JWKSource<SecurityContext> jwkSource(RSAKey rsaKey){
//        JWKSet jwkSet = new JWKSet(rsaKey);
//        return new JWKSource<SecurityContext>() {
//            @Override
//            public List<JWK> get(JWKSelector jwkSelector, SecurityContext context) throws KeySourceException {
//                return jwkSelector.select(jwkSet);
//            }
//        };

    }




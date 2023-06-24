package com.example.rest2.api.security;
import com.example.rest2.api.Auth.AuthMapper;
import com.example.rest2.api.user.Role;
import com.example.rest2.api.user.User;
import com.example.rest2.api.user.web.Authority;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImp implements UserDetailsService {
    private final AuthMapper authMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = authMapper.loadUserByUsername(username).orElseThrow(()->
                new UsernameNotFoundException("User is not valid"));
        log.info("User:{}",user);

        for(Role role:user.getRoles()){
            for(Authority authority : role.getAuthorities()){
                System.out.println(authority.getName());
            }
        }

        CustomUserDetails customUserDetails = new CustomUserDetails();
        customUserDetails.setUser(user);

        log.info("Custom User Details: {}",customUserDetails);
        log.info("Custom User Details: {}",customUserDetails.getAuthorities());
        System.out.println(user);
        return customUserDetails;
    }
}

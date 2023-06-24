package com.example.rest2.api.security;

import com.example.rest2.api.user.Role;
import com.example.rest2.api.user.User;
import com.example.rest2.api.user.web.Authority;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomUserDetails implements UserDetails {

    private User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        for(Role role: user.getRoles()){
            authorities.add(new SimpleGrantedAuthority(role.getAuthority()));

            for (Authority authority : role.getAuthorities()){
               authorities.add(new SimpleGrantedAuthority(authority.getName()));

            }
        }
        System.out.println(authorities);
     return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

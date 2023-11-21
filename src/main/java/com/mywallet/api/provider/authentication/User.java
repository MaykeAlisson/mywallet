package com.mywallet.api.provider.authentication;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Objects;

@Getter
public class User implements UserDetails {

    private final Integer userId;
    private final String username;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;

    public User(final String username, final String password,
                final Collection<? extends GrantedAuthority> authorities, final  Integer userId) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.userId = userId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.username;
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

    public static User getUserContext() {
        final var context = SecurityContextHolder.getContext();
        if (Objects.nonNull(context) &&
                Objects.nonNull(context.getAuthentication()) &&
                context.getAuthentication().getPrincipal() instanceof User userAuth) return userAuth;
        return null;
    }
}

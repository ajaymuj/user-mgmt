package com.mycode.bms.usermgmt.model;

import com.mycode.bms.usermgmt.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class LoginUserDetail implements UserDetails {

    private String username;
    private String password;
    private List<GrantedAuthority> roles;
    private boolean active;

    public LoginUserDetail(){

    }

    public LoginUserDetail(User user) {
        this.username = user.getUserName();
        this.password = user.getPassword();
        this.active = user.getActive();
        this.roles = user.getRoles().stream().map(c -> c.getRoleName())
                .map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return active;
    }

    @Override
    public boolean isAccountNonLocked() {
        return active;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return active;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }
}

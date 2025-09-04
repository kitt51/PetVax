package com.kitt51.PetVax.User.Model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


public class SecurityUser implements UserDetails {

    private final AppUser appUser;

    public SecurityUser(AppUser appUser) {
        this.appUser = appUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
      String role = appUser.getRole();
      String prefixedRole = role.startsWith("ROLE_") ? role : "ROLE_" + role;
      return List.of(new SimpleGrantedAuthority(prefixedRole));
    }

    @Override
    public String getPassword() {
        return appUser.getPassword();
    }

    @Override
    public String getUsername() {
        return appUser.getEmail();
    }
    public Long getId() {
        return appUser.getId();
    }
}

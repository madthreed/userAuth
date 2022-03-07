package com.madthreed.userAuth.dto;

import com.madthreed.userAuth.domain.Role;
import com.madthreed.userAuth.domain.Token;

import java.util.Set;

public class UserDto {
    private Long id;

    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String email;
    private Token token;
    private boolean active;
    private Set<Role> roles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Token getVerificationToken() {
        return token;
    }

    public void setVerificationToken(Token token) {
        this.token = token;
    }
}

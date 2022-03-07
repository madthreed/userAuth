package com.madthreed.userAuth.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String token;
    private String newPassword;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    private Date expiryDate;

    public Token() {
    }

    public Token(String token, String newPassword, User user, Date expiryDate) {
        this.token = token;
        this.newPassword = newPassword;
        this.user = user;
        this.expiryDate = expiryDate;
    }

    public Token(Long id, String token, String newPassword, User user, Date expiryDate) {
        this.id = id;
        this.token = token;
        this.newPassword = newPassword;
        this.user = user;
        this.expiryDate = expiryDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }
}

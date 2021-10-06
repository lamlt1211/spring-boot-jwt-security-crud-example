package com.savvycom.payload.request;

import javax.validation.constraints.NotBlank;

/**
 * @author lam.le
 * @created 29/09/2021
 */
public class LoginRequest {
    @NotBlank
    private String username;

    @NotBlank
    private String password;

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
}

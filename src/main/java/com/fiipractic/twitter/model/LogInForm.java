package com.fiipractic.twitter.model;

public class LogInForm {
    private String authType;
    private String credential;
    private String password;

    // Getters and setters
    public String getAuthType() {
        return authType;
    }

    public void setAuthType(String authType) {
        this.authType = authType;
    }

    public String getCredential() {
        return credential;
    }

    public void setCredential(String credential) {
        this.credential = credential;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // toString method
    @Override
    public String toString() {
        return "LogInForm{" +
                "authType='" + authType + '\'' +
                ", credential='" + credential + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}


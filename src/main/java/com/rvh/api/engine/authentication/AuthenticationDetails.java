package com.rvh.api.engine.authentication;

public class AuthenticationDetails {

    private String userName;
    private String password;
    private String key;

    public AuthenticationDetails(String userName, String password, String key) {
        this.userName = userName;
        this.password = password;
        this.key = key;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


}

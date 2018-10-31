package com.mronestonyo.chatapp.pojo;

public class Users {
    private String Username;
    private String Password;

    public Users(String username, String password) {
        Username = username;
        Password = password;
    }

    public String getUsername() {
        return Username;
    }

    public String getPassword() {
        return Password;
    }
}

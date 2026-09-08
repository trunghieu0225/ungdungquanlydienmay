package com.example.dienmayapp.api;

public class UserRequest {

    private String username;
    private String password;
    private String fullname;
    private String role;

    public UserRequest() {
    }

    public UserRequest(String username,
                       String password,
                       String fullname,
                       String role) {

        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.role = role;

    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFullname() {
        return fullname;
    }

    public String getRole() {
        return role;
    }

}
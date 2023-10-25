package com.example.demo.data;

import com.example.demo.utils.constants.RoleType;

public class CurrentUser {
    private static CurrentUser instance;
    private String username;
    private RoleType role;

    public static CurrentUser getInstance() {
        if (instance == null) {
            instance = new CurrentUser();
        }
        return instance;
    }

    private CurrentUser() {
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public RoleType getRole() {
        return this.role;
    }

    public void setRole(RoleType role) {
        this.role = role;
    }
}

package com.example.eind_opdracht.model;

import com.example.eind_opdracht.model.Enums.Role;

import java.io.Serializable;

public class User implements Serializable {
    private String username;
    private String password;
    private Role role;

    public User(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public void setUsername(String username) {
        username = username;
    }

    public void setPassword(String password) {
        password = password;
    }

    public void setRole(com.example.eind_opdracht.model.Enums.Role role) {
        role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public com.example.eind_opdracht.model.Enums.Role getRole() {
        return role;
    }
}

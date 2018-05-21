package com.example.joel.myapplication;

import java.io.Serializable;

public class UserModel implements Serializable {
    private String birthday;
    private String email;
    private String id;
    private String lastname;
    private String mobile;
    private String password;
    private String status;
    private String username;

    public UserModel(String username, String lastname, String id, String birthday, String status, String mobile, String email, String password) {
        this.username = username;
        this.lastname = lastname;
        this.id = id;
        this.birthday = birthday;
        this.status = status;
        this.mobile = mobile;
        this.email = email;
        this.password = password;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLastname() {
        return this.lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBirthday() {
        return this.birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

package com.example.laowuguanli.bean;

import java.io.Serializable;

public class User implements Serializable {
    private int id;
    private String number;
    private String password;
    private String accountType;

    public User() {
    }

    public User(int id, String number, String password, String accountType) {
        this.id = id;
        this.number = number;
        this.password = password;
        this.accountType = accountType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", password='" + password + '\'' +
                ", accountType='" + accountType + '\'' +
                '}';
    }
}

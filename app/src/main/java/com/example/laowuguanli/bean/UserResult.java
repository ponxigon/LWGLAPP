package com.example.laowuguanli.bean;

//登录反馈
public class UserResult {
    private int zh;
    private int id;
    private String accountType;
    private String other;

    public UserResult() {
    }

    public UserResult(int zh, int id, String accountType, String other) {
        this.zh = zh;
        this.id = id;
        this.accountType = accountType;
        this.other = other;
    }

    @Override
    public String toString() {
        return "UserResult{" +
                "zh=" + zh +
                ", id=" + id +
                ", accountType='" + accountType + '\'' +
                ", other='" + other + '\'' +
                '}';
    }

    public String getOther() {
        return other;
    }

    public UserResult setOther(String other) {
        this.other = other;
        return this;
    }

    public int getZh() {
        return zh;
    }

    public void setZh(int zh) {
        this.zh = zh;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
}

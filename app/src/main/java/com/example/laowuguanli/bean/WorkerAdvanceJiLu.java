package com.example.laowuguanli.bean;

public class WorkerAdvanceJiLu {

    private Integer money;
    private String state;

    @Override
    public String toString() {
        return "WorkerAdvanceJiLu{" +
                "money=" + money +
                ", state='" + state + '\'' +
                '}';
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}

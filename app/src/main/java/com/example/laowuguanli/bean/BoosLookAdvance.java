package com.example.laowuguanli.bean;

public class BoosLookAdvance {
    private int advanceId;
    private int workerId;
    private String workerName;
    private int money;

    @Override
    public String toString() {
        return "BoosLookAdvance{" +
                "advanceId=" + advanceId +
                ", workerId=" + workerId +
                ", workerName='" + workerName + '\'' +
                ", money=" + money +
                '}';
    }

    public int getAdvanceId() {
        return advanceId;
    }

    public void setAdvanceId(int advanceId) {
        this.advanceId = advanceId;
    }

    public String getWorkerName() {
        return workerName;
    }

    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }

    public int getWorkerId() {
        return workerId;
    }

    public void setWorkerId(int workerId) {
        this.workerId = workerId;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }
}

package com.example.laowuguanli.bean;


public class LabourServiceBean {
    int boosId;
    int workerId;

    public int getBoosId() {
        return boosId;
    }

    public int getWorkerId() {
        return workerId;
    }

    @Override
    public String toString() {
        return "LabourServiceBean{" +
                "boosId=" + boosId +
                ", workerId=" + workerId +
                '}';
    }
}

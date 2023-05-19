package com.example.laowuguanli.bean;

public class WorkerLookVacateBean {
    private String vacatedate;
    private String state;

    @Override
    public String toString() {
        return "WorkerLookVacateBean{" +
                "vacatedate='" + vacatedate + '\'' +
                ", state='" + state + '\'' +
                '}';
    }

    public String getVacatedate() {
        return vacatedate;
    }

    public void setVacatedate(String vacatedate) {
        this.vacatedate = vacatedate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}

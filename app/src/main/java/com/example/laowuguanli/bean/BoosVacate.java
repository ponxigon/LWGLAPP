package com.example.laowuguanli.bean;

public class BoosVacate {
    private int vacateId;
    private int workerId;

    private String workerName;
    private String vacatedate;//请假日期
    private String returndate;//返岗日期
    private String vacatecause;//理由

    @Override
    public String toString() {
        return "BoosVacate{" +
                "vacateId=" + vacateId +
                ", workerId=" + workerId +
                ", workerName='" + workerName + '\'' +
                ", vacatedate='" + vacatedate + '\'' +
                ", returndate='" + returndate + '\'' +
                ", vacatecause='" + vacatecause + '\'' +
                '}';
    }

    public int getVacateId() {
        return vacateId;
    }

    public void setVacateId(int vacateId) {
        this.vacateId = vacateId;
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

    public String getVacatedate() {
        return vacatedate;
    }

    public void setVacatedate(String vacatedate) {
        this.vacatedate = vacatedate;
    }

    public String getReturndate() {
        return returndate;
    }

    public void setReturndate(String returndate) {
        this.returndate = returndate;
    }

    public String getVacatecause() {
        return vacatecause;
    }

    public void setVacatecause(String vacatecause) {
        this.vacatecause = vacatecause;
    }
}

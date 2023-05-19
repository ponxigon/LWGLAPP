package com.example.laowuguanli.bean;

public class WorkerMessageLabour {
    private Integer workerId;
    private String workerName;

    public int getWorkerId() {
        return workerId;
    }

    public String getWorkerName() {
        return workerName;
    }

    @Override
    public String toString() {
        return "WorkerMessageLabour{" +
                "workerId=" + workerId +
                ", workerName='" + workerName + '\'' +
                '}';
    }
}

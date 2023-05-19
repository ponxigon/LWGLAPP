package com.example.laowuguanli.bean;

import java.util.Arrays;

public class BoosLabourResponse {
    private WorkerMessageLabour[] workerMessageLabours;

    public WorkerMessageLabour[] getWorkerMessageLabours() {
        return workerMessageLabours;
    }

    public void setWorkerMessageLabours(WorkerMessageLabour[] workerMessageLabours) {
        this.workerMessageLabours = workerMessageLabours;
    }

    @Override
    public String toString() {
        return "BoosLabourResponse{" +
                "workerMessageLabours=" + Arrays.toString(workerMessageLabours) +
                '}';
    }
}

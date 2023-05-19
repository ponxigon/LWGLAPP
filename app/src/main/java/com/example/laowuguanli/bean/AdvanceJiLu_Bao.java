package com.example.laowuguanli.bean;

import java.util.ArrayList;

public class AdvanceJiLu_Bao {
    private ArrayList<WorkerAdvanceJiLu> advanceJiLus;

    public ArrayList<WorkerAdvanceJiLu> getAdvanceJiLus() {
        return advanceJiLus;
    }

    public void setAdvanceJiLus(ArrayList<WorkerAdvanceJiLu> advanceJiLus) {
        this.advanceJiLus = advanceJiLus;
    }

    @Override
    public String toString() {
        return "AdvanceJiLu_Bao{" +
                "advanceJiLus=" + advanceJiLus +
                '}';
    }
}

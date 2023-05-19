package com.example.laowuguanli.bean;

import java.util.ArrayList;
import java.util.HashMap;

//manage查看投诉信列表worker的信息
public class ManageComplaintWorkerBean {
    private ArrayList<HashMap<String,String>> workerNameList;

    @Override
    public String toString() {
        return "ManageComplaintWorkerBean{" +
                "workerNameList=" + workerNameList +
                '}';
    }

    public ArrayList<HashMap<String, String>> getWorkerNameList() {
        return workerNameList;
    }

    public void setWorkerNameList(ArrayList<HashMap<String, String>> workerNameList) {
        this.workerNameList = workerNameList;
    }
}

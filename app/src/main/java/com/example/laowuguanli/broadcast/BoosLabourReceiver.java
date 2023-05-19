package com.example.laowuguanli.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.HashMap;

public class BoosLabourReceiver extends BroadcastReceiver {
    ArrayList<HashMap<String, Object>> list = new ArrayList<>();

    public ArrayList<HashMap<String, Object>> getList() {
        return list;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle extras = intent.getExtras();
        ArrayList<HashMap<String, Object>> workerList = (ArrayList<HashMap<String, Object>>) extras.get("msg");
        list = workerList;
        System.out.println("广播内容"+workerList.toString());
    }
}

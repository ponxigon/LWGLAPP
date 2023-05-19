package com.example.laowuguanli.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MessageReceiver extends BroadcastReceiver {
    private static int id;
    private static String number;
    private static String company;
    private static String city;
    private static String form;

    public static String getForm() {
        return form;
    }

    public static String getNumber() {
        return number;
    }

    public static int getId() {
        return id;
    }

    public static String getCompany() {
        return company;
    }

    public static String getCity() {
        return city;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        id =intent.getIntExtra("id",-1);
        company = intent.getStringExtra("company");
        city = intent.getStringExtra("city");
        number = intent.getStringExtra("number");
        form = intent.getStringExtra("form");
    }
}

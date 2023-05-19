package com.example.laowuguanli.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class OtherReceiver extends BroadcastReceiver {

    private static String name;

    public static String getName() {
        return name;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        name = intent.getStringExtra("name");
    }
}

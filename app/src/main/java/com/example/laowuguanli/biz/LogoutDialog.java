package com.example.laowuguanli.biz;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;

import com.example.laowuguanli.ui.activity.MainActivity;

public class LogoutDialog {
    private final Context context;

    public LogoutDialog(Context context) {
        this.context = context;
    }

    public void logout(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("确定要退出登录吗？？？");
        builder.setPositiveButton("退出", (dialogInterface, i) -> {
            Intent intent = new Intent(context, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);//清除栈顶Activity
            context.startActivity(intent);
        });
        builder.setNegativeButton("取消", (dialogInterface, i) -> {
            dialogInterface.cancel();
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}

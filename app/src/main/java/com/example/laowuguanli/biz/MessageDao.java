package com.example.laowuguanli.biz;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

public class MessageDao {
    public SQLiteDatabase database;

    public MessageDao(Context context) {
        MyOpenHelper myOpenHelper = new MyOpenHelper(context);
        //获取可以操作的数据库对象
        database = myOpenHelper.getReadableDatabase();
    }
}

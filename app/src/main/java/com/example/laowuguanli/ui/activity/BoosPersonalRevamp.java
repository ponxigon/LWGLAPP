package com.example.laowuguanli.ui.activity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.laowuguanli.bean.Feedback;
import com.example.laowuguanli.biz.MessageDao;
import com.example.laowuguanli.biz.SqlLang;
import com.example.laowuguanli.broadcast.MessageReceiver;
import com.example.laowuguanli.databinding.ActivityBoosPersonalRevampBinding;
import com.example.laowuguanli.net.ConnectServer;
import com.example.laowuguanli.net.HttpBinService;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class BoosPersonalRevamp extends BaseActivity {
    private ActivityBoosPersonalRevampBinding binding;
    private int id;
    private MessageDao messageDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBoosPersonalRevampBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setUpToolBar("修改资料");
        id = MessageReceiver.getId();
        submitData();
    }

    @SuppressLint("Range")
    private void initData() {
        Cursor cursor = messageDao.database.query(SqlLang.CREATE_TABLE, null,
                "id like ?", new String[]{"" + id}, null, null, null);
        cursor.moveToNext();
        String name = cursor.getString(cursor.getColumnIndex("name"));
        String company = cursor.getString(cursor.getColumnIndex("company"));
        String city = cursor.getString(cursor.getColumnIndex("city"));
        binding.informationName2.setHint(name);
        binding.informationCompany2.setHint(company);
        binding.informationCity2.setHint(city);
    }

    @Override
    protected void onStart() {
        super.onStart();
        messageDao = new MessageDao(this);
        initData();
    }

    private void submitData() {
        binding.btnInformationChanges.setOnClickListener(view -> new Thread(this::intnet).start());
    }

    private void intnet() {
        String url = ConnectServer.url();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(url).build();
        HttpBinService httpBinService = retrofit.create(HttpBinService.class);
        String name = ifNull(binding.informationName2.getText().toString());
        String company = ifNull(binding.informationCompany2.getText().toString());
        String city = ifNull(binding.informationCity2.getText().toString());
        Call<ResponseBody> call = httpBinService.boosRevamp(id, name
                , company, city);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                Feedback feedback = null;
                try {
                    assert response.body() != null;
                    String string = response.body().string();
                    Gson gson = new Gson();
                    feedback = gson.fromJson(string, Feedback.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                assert feedback != null;
                if (feedback.isReminder()) {
                    Toast.makeText(BoosPersonalRevamp.this, "修改成功", Toast.LENGTH_SHORT).show();
                    ContentValues values = new ContentValues();
                    values.put("name", name);
                    values.put("company", company);
                    values.put("city", city);
                    messageDao.database.update(SqlLang.CREATE_TABLE, values, "id like ?", new String[]{String.valueOf(id)});
                } else {
                    Toast.makeText(BoosPersonalRevamp.this, "修改失败", Toast.LENGTH_SHORT).show();
                }
                messageDao.database.close();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(BoosPersonalRevamp.this, "服务器错误", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String ifNull(String text) {
        return text.equals("") ? null : text;
    }

}
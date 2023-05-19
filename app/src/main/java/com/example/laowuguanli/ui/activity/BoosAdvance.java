package com.example.laowuguanli.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.ListView;

import androidx.annotation.NonNull;

import com.example.laowuguanli.R;
import com.example.laowuguanli.bean.BoosLookAdvance;
import com.example.laowuguanli.bean.BoosLookAdvance_Bao;
import com.example.laowuguanli.biz.BoosAdvanceAdapter;
import com.example.laowuguanli.broadcast.MessageReceiver;
import com.example.laowuguanli.net.ConnectServer;
import com.example.laowuguanli.net.HttpBinService;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class BoosAdvance extends BaseActivity {
    private ListView listView;
    private int boosId;
    private ArrayList<BoosLookAdvance> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boos_advance);
        setUpToolBar("预支工资审核");
        initView();
        initData();
    }

    private void initData() {
        boosId = MessageReceiver.getId();
        new Thread(this::inter).start();
    }

    private void inter() {
        Call<ResponseBody> call = new Retrofit.Builder().baseUrl(ConnectServer.url()).build()
                .create(HttpBinService.class).boosAdvance(boosId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    assert response.body() != null;
                    String string = response.body().string();
                    arrayList = new Gson().fromJson(string, BoosLookAdvance_Bao.class).getBoosLookAdvance();

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                handler.sendMessage(new Message());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void initView() {
        listView = findViewById(R.id.lv_boos_vacate);
    }

    private final Handler handler = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            setViewData();
        }
    };

    private void setViewData() {
        BoosAdvanceAdapter adapter = new BoosAdvanceAdapter(this, arrayList);
        listView.setAdapter(adapter);
    }
}
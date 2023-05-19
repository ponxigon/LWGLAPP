package com.example.laowuguanli.ui.activity;

import androidx.annotation.NonNull;

import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

import com.example.laowuguanli.bean.BoosInformation;
import com.example.laowuguanli.broadcast.MessageReceiver;
import com.example.laowuguanli.databinding.ActivityBoosPersonalInformationBinding;
import com.example.laowuguanli.net.ConnectServer;
import com.example.laowuguanli.net.HttpBinService;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class BoosPersonalInformation extends BaseActivity {
    private ActivityBoosPersonalInformationBinding binding;
    private int id;
    private final Handler handler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            BoosInformation boosInformation = (BoosInformation) msg.obj;
            show(boosInformation);
        }
    };

    private void form(String form) {
        if (form.equals("企业")){
            id = MessageReceiver.getId();
        }else if (form.equals("管理部门")){
            id = getIntent().getIntExtra("id",-1);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBoosPersonalInformationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        form(MessageReceiver.getForm());
        new Thread(() -> inquireInformation(id)).start();
        setUpToolBar("企业账户信息");
    }

    private void show(BoosInformation boosInformation) {
        binding.informationBoosid2.setText(String.valueOf(boosInformation.getId()));
        binding.informationName2.setText(boosInformation.getName());
        binding.informationCompany2.setText(boosInformation.getCompany());
        binding.informationCity2.setText(boosInformation.getCity());
    }

    //联网
    private void inquireInformation(int id) {
        String url = ConnectServer.url();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(url).build();
        HttpBinService httpBinService = retrofit.create(HttpBinService.class);
        Call<ResponseBody> call = httpBinService.boosinfor(id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                try {
                    assert response.body() != null;
                    String string = response.body().string();
                    Gson gson = new Gson();
                    BoosInformation boosInformation = gson.fromJson(string, BoosInformation.class);
                    analysis(boosInformation);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(BoosPersonalInformation.this, "服务器错误", Toast.LENGTH_SHORT).show();
            }
        });

    }

    //解析response数据
    private void analysis(BoosInformation boosInformation) throws IOException {
        System.out.println(boosInformation);
        Message message = new Message();
        message.obj = boosInformation;
        handler.sendMessage(message);
    }
}
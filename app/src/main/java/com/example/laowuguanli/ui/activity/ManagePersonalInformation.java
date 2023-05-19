package com.example.laowuguanli.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.laowuguanli.bean.ManageInformation;
import com.example.laowuguanli.broadcast.MessageReceiver;
import com.example.laowuguanli.databinding.ActivityManagePersonalInformationBinding;
import com.example.laowuguanli.net.ConnectServer;
import com.example.laowuguanli.net.HttpBinService;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ManagePersonalInformation extends BaseActivity {
    private ActivityManagePersonalInformationBinding binding;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_manage_personal_information);
        binding = ActivityManagePersonalInformationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setUpToolBar("账户信息");
        id = MessageReceiver.getId();
        submitData();
    }

    private void submitData() {
        new Thread(() -> inquireInformation(id)).start();
    }

    private void inquireInformation(int id) {
        String url = ConnectServer.url();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(url).build();
        HttpBinService httpBinService = retrofit.create(HttpBinService.class);
        Call<ResponseBody> call = httpBinService.manageSelect(id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String string = null;
                try {
                    string = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Gson gson = new Gson();
                ManageInformation manageInformation = gson.fromJson(string, ManageInformation.class);
                Message message = new Message();
                message.obj = manageInformation;
                handler.sendMessage(message);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(ManagePersonalInformation.this, "服务器错误", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private final Handler handler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            ManageInformation manageInformation = (ManageInformation) msg.obj;
            show(manageInformation);
        }
    };

    private void show(ManageInformation manageInformation) {
        binding.informationManageid2.setText(String.valueOf(manageInformation.getId()));
        binding.informationName2.setText(manageInformation.getName());
        binding.informationCity2.setText(manageInformation.getCity());
    }
}
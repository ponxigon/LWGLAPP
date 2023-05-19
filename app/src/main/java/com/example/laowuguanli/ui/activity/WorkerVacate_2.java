package com.example.laowuguanli.ui.activity;

import androidx.annotation.NonNull;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.example.laowuguanli.bean.VacateBean;
import com.example.laowuguanli.bean.WorkerLookVacateBean;
import com.example.laowuguanli.biz.VacateAdapter;
import com.example.laowuguanli.broadcast.MessageReceiver;
import com.example.laowuguanli.databinding.ActivityWorkerVacate2Binding;
import com.example.laowuguanli.net.ConnectServer;
import com.example.laowuguanli.net.HttpBinService;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class WorkerVacate_2 extends BaseActivity {
    private ActivityWorkerVacate2Binding binding;
    private int workerId;
    private ArrayList<HashMap<String,String>> arrayList  = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWorkerVacate2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setUpToolBar("申请状态");
        initView();
    }

    private void initView() {
        workerId = MessageReceiver.getId();
        new Thread(this::inter).start();
    }

    private void inter() {
        Call<ResponseBody> call = new Retrofit.Builder().baseUrl(ConnectServer.url()).build()
                .create(HttpBinService.class).workerLookVacate(workerId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    assert response.body() != null;
                    String string = response.body().string();

                    VacateBean vacateBean = new Gson().fromJson(string, VacateBean.class);
                    for (WorkerLookVacateBean bean : vacateBean.getBeans()) {
                        HashMap<String, String> map = new HashMap<>();
                        map.put("vacatedate",bean.getVacatedate());
                        map.put("state",bean.getState());
                        arrayList.add(map);
                    }
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

    private final Handler handler = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            initData();
        }
    };

    private void initData() {
        VacateAdapter adapter = new VacateAdapter(arrayList,this);
        binding.lvVacate.setAdapter(adapter);
    }
}
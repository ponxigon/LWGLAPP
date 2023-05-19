package com.example.laowuguanli.ui.activity;


import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import com.example.laowuguanli.bean.ManageComplaintWorkerBean;
import com.example.laowuguanli.biz.ManageComplaintAdapter;
import com.example.laowuguanli.broadcast.MessageReceiver;
import com.example.laowuguanli.databinding.ActivityManageComplaintBinding;
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

public class ManageComplaint extends BaseActivity {

    private ActivityManageComplaintBinding binding;
    private int manageId;
    private final ArrayList<HashMap<String,String>> arrayList = new ArrayList<>();
    private final Handler handler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            initView(arrayList);
        }
    };

    private void initView(ArrayList<HashMap<String,String>> arrayList) {
        ManageComplaintAdapter adapter = new ManageComplaintAdapter(arrayList, this);
        binding.lvComplaint.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityManageComplaintBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setUpToolBar("投诉接收箱");
        initData();
    }

    private void initData() {
        manageId = MessageReceiver.getId();
        new Thread(this::inter).start();
    }

    private void inter() {
        Retrofit build = new Retrofit.Builder().baseUrl(ConnectServer.url()).build();
        Call<ResponseBody> call = build.create(HttpBinService.class).manageComplaint(manageId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    assert response.body() != null;
                    String string = response.body().string();
                    ArrayList<HashMap<String, String>> list;

                    list = new Gson().fromJson(string, ManageComplaintWorkerBean.class).getWorkerNameList();
                    for (int i = 0; i < list.size(); i++) {
                        HashMap<String, String> map = new HashMap<>();
                        String complaintId = list.get(i).get("complaintId");
                        map.put("complaintId",complaintId);
                        map.put("workerName", list.get(i).get("workerName"));
                        System.out.println("是否转换到int："+map);
                        arrayList.add(map);
                    }

                    System.out.println(list);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                Message message = new Message();
                handler.sendMessage(message);

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
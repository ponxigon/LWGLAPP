package com.example.laowuguanli.ui.activity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.laowuguanli.bean.Feedback;
import com.example.laowuguanli.broadcast.MessageReceiver;
import com.example.laowuguanli.databinding.ActivityWorkerLabourServiceBinding;
import com.example.laowuguanli.net.ConnectServer;
import com.example.laowuguanli.net.HttpBinService;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class WorkerLabourService extends BaseActivity {
    private ActivityWorkerLabourServiceBinding binding;
    private int id;
    private String companyName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWorkerLabourServiceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initData();
        initView();
        setUpToolBar("劳务关系");
    }

    private void initData() {
        id = MessageReceiver.getId();
    }

    private void initView() {
        binding.btnApplyFor.setOnClickListener(view -> ifNull());
    }

    private void intnet() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(ConnectServer.url()).build();
        HttpBinService httpBinService = retrofit.create(HttpBinService.class);
        Call<ResponseBody> call = httpBinService.labourService(id, companyName);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String string = response.body().string();
                    Feedback feedback = new Gson().fromJson(string, Feedback.class);
                    if (feedback.isReminder()) {
                        Toast.makeText(WorkerLabourService.this, "提交成功", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(WorkerLabourService.this, "该企业不存在", Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }

    private void ifNull() {
        companyName = binding.edApplyFor.getText().toString();
        if (companyName.equals("")){
            Toast.makeText(this, "请输入企业名称", Toast.LENGTH_SHORT).show();
        }else {
            new Thread(this::intnet).start();
        }
    }
}
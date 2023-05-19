package com.example.laowuguanli.ui.activity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.laowuguanli.bean.Feedback;
import com.example.laowuguanli.databinding.ActivityDispatchPageBinding;
import com.example.laowuguanli.net.ConnectServer;
import com.example.laowuguanli.net.HttpBinService;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DispatchPage extends BaseActivity {
    private ActivityDispatchPageBinding binding;
    private int workerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDispatchPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setUpToolBar("员工派遣管理");
        initView();
    }

    private void initView() {
        workerId = getIntent().getIntExtra("workerId", -1);
        binding.btnBtn.setOnClickListener(view -> new Thread(this::inter).start());
    }

    private void inter() {
        Retrofit build = new Retrofit.Builder().baseUrl(ConnectServer.url()).build();
        Call<ResponseBody> call = build.create(HttpBinService.class).workerDispatch(workerId, binding.etDispatch1.getText().toString()
                , binding.etDispatch2.getText().toString());
        System.out.println("派去的岗位="+binding.etDispatch2.getText().toString());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    assert response.body() != null;
                    String string = response.body().string();
                    Feedback feedback = new Gson().fromJson(string, Feedback.class);
                    if (feedback.isReminder()) {
                        Toast.makeText(DispatchPage.this, "修改成功", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(DispatchPage.this, "修改失败", Toast.LENGTH_SHORT).show();
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
}
package com.example.laowuguanli.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.laowuguanli.R;
import com.example.laowuguanli.bean.UserResult;
import com.example.laowuguanli.databinding.ActivityLoginBoosBinding;
import com.example.laowuguanli.databinding.ActivityLoginManageBinding;
import com.example.laowuguanli.net.ConnectServer;
import com.example.laowuguanli.net.HttpBinService;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Login_manage extends BaseActivity {
    ActivityLoginManageBinding binding;
    private HttpBinService httpBinService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginManageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//        setContentView(R.layout.activity_login_worker);
        initEvent();
        setUpToolBar("管理部门注册");

    }

    private void initEvent() {
        //注册
        binding.btnLoginManageLogin.setOnClickListener(view -> {
            new Thread(this::sendAccountData).start();
        });
        //验证码
        binding.btnLoginManageVerification.setOnClickListener(view -> {
            Toast.makeText(this, "无需输入验证码", Toast.LENGTH_SHORT).show();
        });
    }

    private void sendAccountData() {
        String url = ConnectServer.url();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(url).build();
        httpBinService = retrofit.create(HttpBinService.class);
        Call<ResponseBody> call = httpBinService.registerAccount(binding.etLoginManageNumber.getText().toString(),
                binding.etLoginManagePsd.getText().toString(), "管理部门");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String string = null;
                try {
                    assert response.body() != null;
                    string = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Gson gson = new Gson();
                UserResult userResult = gson.fromJson(string, UserResult.class);
                if (userResult.getZh() == 1) {
                    Toast.makeText(Login_manage.this, "注册成功", Toast.LENGTH_SHORT).show();
                } else if (userResult.getZh() == -1) {
                    Toast.makeText(Login_manage.this, "该号码已注册", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(Login_manage.this, "网络错误", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
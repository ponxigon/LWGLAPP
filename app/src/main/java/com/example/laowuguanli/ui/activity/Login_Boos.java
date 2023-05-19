package com.example.laowuguanli.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.laowuguanli.R;
import com.example.laowuguanli.bean.UserResult;
import com.example.laowuguanli.databinding.ActivityLoginBoosBinding;
import com.example.laowuguanli.databinding.ActivityLoginWorkerBinding;
import com.example.laowuguanli.net.ConnectServer;
import com.example.laowuguanli.net.HttpBinService;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Login_Boos extends BaseActivity {

    ActivityLoginBoosBinding binding;
    private HttpBinService httpBinService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBoosBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initEvent();
        setUpToolBar("企业注册");
    }

    private void initEvent() {
        //注册
        binding.btnLoginBoosLogin.setOnClickListener(view -> {
            new Thread(this::sendAccountData).start();
        });
        //验证码
        binding.btnLoginBoosVerification.setOnClickListener(view -> {
            Toast.makeText(this, "无需输入验证码", Toast.LENGTH_SHORT).show();
        });
    }

    private void sendAccountData() {
        String url = ConnectServer.url();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(url).build();
        httpBinService = retrofit.create(HttpBinService.class);
        Call<ResponseBody> call = httpBinService.registerAccount(binding.etLoginBoosNumber.getText().toString(),
                binding.etLoginBoosPsd.getText().toString(), "企业");
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
                    Toast.makeText(Login_Boos.this, "注册成功", Toast.LENGTH_SHORT).show();
                } else if (userResult.getZh() == -1) {
                    Toast.makeText(Login_Boos.this, "该号码已注册", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(Login_Boos.this, "网络错误", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
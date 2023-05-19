package com.example.laowuguanli.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.laowuguanli.bean.Feedback;
import com.example.laowuguanli.broadcast.MessageReceiver;
import com.example.laowuguanli.databinding.ActivityChangePasswordBinding;
import com.example.laowuguanli.net.ConnectServer;
import com.example.laowuguanli.net.HttpBinService;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ChangePassword extends BaseActivity {
    private ActivityChangePasswordBinding binding;
    private String number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChangePasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setUpToolBar("修改密码");
        number = MessageReceiver.getNumber();
        submit();
    }

    private void submit() {
        binding.btnPasswordChanges.setOnClickListener(view -> comparison());
    }

    //对比两次密码
    private void comparison() {
        String psd_1 = binding.etChange1.getText().toString();
        String psd_2 = binding.etChange2.getText().toString();
        if (psd_1.equals("")){
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
        }else if (psd_1.equals(psd_2)) {
            initEvent();
        } else {
            Toast.makeText(this, "两次密码输入不一致", Toast.LENGTH_SHORT).show();
        }
    }

    private void initEvent() {
        new Thread(this::searchInformation).start();
    }

    private void searchInformation() {
        String url = ConnectServer.url();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(url).build();
        HttpBinService httpBinService = retrofit.create(HttpBinService.class);
        Call<ResponseBody> call = httpBinService.changePassword(number, binding.etChange1.getText().toString());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Feedback feedback = null;
                try {
                    assert response.body() != null;
                    String string = response.body().string();
                    feedback = new Gson().fromJson(string, Feedback.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                assert feedback != null;
                if (feedback.isReminder()) {
                    Toast.makeText(ChangePassword.this, "修改成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ChangePassword.this, "修改失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(ChangePassword.this, "服务器错误", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
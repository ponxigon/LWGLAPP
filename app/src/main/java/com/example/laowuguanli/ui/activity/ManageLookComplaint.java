package com.example.laowuguanli.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.laowuguanli.bean.ComplaintEmail;
import com.example.laowuguanli.bean.Feedback;
import com.example.laowuguanli.databinding.ActivityManageLookComplaintBinding;
import com.example.laowuguanli.net.ConnectServer;
import com.example.laowuguanli.net.HttpBinService;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

//manage查看内容
public class ManageLookComplaint extends BaseActivity {
    private ActivityManageLookComplaintBinding binding;
    private int complaintid;
    public HttpBinService httpBinService =  new Retrofit.Builder().baseUrl(ConnectServer.url()).build().
            create(HttpBinService.class);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityManageLookComplaintBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setUpToolBar("投诉内容");
        complaintid = Integer.parseInt(getIntent().getStringExtra("complaintId"));
        initData();
    }

    private void initData() {
        new Thread(this::inter).start();
        binding.btnSolve.setOnClickListener(view -> interSolve());
    }

    private void interSolve() {
        Call<ResponseBody> call = httpBinService.manageSolve(complaintid);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    assert response.body() != null;
                    String string = response.body().string();
                    boolean reminder = new Gson().fromJson(string, Feedback.class).isReminder();
                    if (reminder){
                        Toast.makeText(ManageLookComplaint.this, "已处理", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(ManageLookComplaint.this, "处理失败", Toast.LENGTH_SHORT).show();
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

    private void inter() {
        Call<ResponseBody> call = httpBinService.manageLookMail(complaintid);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String string = response.body().string();
                    ComplaintEmail complaintEmail = new Gson().fromJson(string, ComplaintEmail.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("company",complaintEmail.getCompany());
                    bundle.putString("mailContent",complaintEmail.getMailContent());
                    Message message = new Message();
                    message.obj = bundle;
                    handler.sendMessage(message);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

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
            Bundle bundle = (Bundle) msg.obj;
            String company = bundle.getString("company");
            String mailContent = bundle.getString("mailContent");
            showData(company,mailContent);
        }
    };

    private void showData(String company, String mailContent) {
        binding.tvBoosName.setText(company);
        binding.tvContent.setText(mailContent);
    }
}
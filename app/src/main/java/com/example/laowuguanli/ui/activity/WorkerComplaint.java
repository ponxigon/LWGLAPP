package com.example.laowuguanli.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.laowuguanli.R;
import com.example.laowuguanli.bean.Feedback;
import com.example.laowuguanli.broadcast.MessageReceiver;
import com.example.laowuguanli.net.ConnectServer;
import com.example.laowuguanli.net.HttpBinService;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class WorkerComplaint extends BaseActivity implements View.OnClickListener {

    private EditText ed_name;
    private EditText ed_cause;
    private Button btn_submit;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_complaint);
        setUpToolBar("投诉反馈");
        initView();
    }
    private void initView() {
        ed_name = findViewById(R.id.tv_complaint_name_2);
        ed_cause = findViewById(R.id.tv_complaint_cause_2);
        btn_submit = findViewById(R.id.btn_worker_complaint);
        btn_submit.setOnClickListener(this);
        id = MessageReceiver.getId();
        System.out.println("workerid="+id);
    }

    @Override
    public void onClick(View view) {
        if ("".equals(ed_name.getText().toString())){
            Toast.makeText(this, "请输入投诉的企业名字", Toast.LENGTH_SHORT).show();
        }else {
            Retrofit build = new Retrofit.Builder().baseUrl(ConnectServer.url()).build();
            Call<ResponseBody> call = build.create(HttpBinService.class).complaint(id,ed_name.getText()
                    .toString(), ed_cause.getText().toString());
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        assert response.body() != null;
                        String string = response.body().string();
                        if ("".equals(string)){
                            Toast.makeText(WorkerComplaint.this, "投诉企业未注册", Toast.LENGTH_SHORT).show();
                        }else {
                            System.out.println("返回提是"+string);
                            Feedback feedback = new Gson().fromJson(string, Feedback.class);
                            if (feedback.isReminder()){
                                Toast.makeText(WorkerComplaint.this, "投诉提交成功", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(WorkerComplaint.this, "投诉提交失败", Toast.LENGTH_SHORT).show();
                            }
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
}
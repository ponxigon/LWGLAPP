package com.example.laowuguanli.ui.activity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import com.example.laowuguanli.bean.Feedback;
import com.example.laowuguanli.biz.MessageDao;
import com.example.laowuguanli.biz.SqlLang;
import com.example.laowuguanli.broadcast.MessageReceiver;
import com.example.laowuguanli.databinding.ActivityWorkerVacate1Binding;
import com.example.laowuguanli.net.ConnectServer;
import com.example.laowuguanli.net.HttpBinService;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class WorkerVacate_1 extends BaseActivity {
    private ActivityWorkerVacate1Binding binding;
    private int workerId;
    private String company;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWorkerVacate1Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setUpToolBar("请假申请");
        initData();
        initView();
    }

    @SuppressLint({"Recycle", "Range"})
    private void initData() {
        workerId = MessageReceiver.getId();
        MessageDao messageDao = new MessageDao(this);
        Cursor query = messageDao.database.query(SqlLang.CREATE_TABLE, new String[]{"companyfront"},
                "id like ?", new String[]{"" + workerId}, null, null, null);
        query.moveToNext();
        company = query.getString(query.getColumnIndex("companyfront"));
        messageDao.database.close();
    }

    private void initView() {
        binding.btnVacateSubmit.setOnClickListener(view -> new Thread(this::inter).start());
    }

    private void inter() {
        System.out.println("发送的公司："+company);
        Call<ResponseBody> call = new Retrofit.Builder().baseUrl(ConnectServer.url()).build()
                .create(HttpBinService.class).vacate1(workerId, company, binding.tv1.getText().toString(),
                        binding.tv2.getText().toString(), binding.tv3.getText().toString());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    assert response.body() != null;
                    String string = response.body().string();
                    boolean reminder = new Gson().fromJson(string, Feedback.class).isReminder();
                    if (reminder){
                        Toast.makeText(WorkerVacate_1.this, "提交成功", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(WorkerVacate_1.this, "未加入劳务派遣公司", Toast.LENGTH_SHORT).show();
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

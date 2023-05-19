package com.example.laowuguanli.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

import com.example.laowuguanli.bean.WorkerManHour;
import com.example.laowuguanli.broadcast.MessageReceiver;
import com.example.laowuguanli.databinding.ActivityWorkerTimeInquireBinding;
import com.example.laowuguanli.net.ConnectServer;
import com.example.laowuguanli.net.HttpBinService;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class WorkerTimeInquire extends BaseActivity {
    private ActivityWorkerTimeInquireBinding binding;
    private final Handler handler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            display((WorkerManHour) msg.obj);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWorkerTimeInquireBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        int id = MessageReceiver.getId();
        initEvent(id);
        setUpToolBar("工资工时");
    }

    private void initEvent(int id) {
        new Thread(() -> searchInformation(id)).start();
    }

    private void searchInformation(int id) {
        String url = ConnectServer.url();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(url).build();
        HttpBinService httpBinService = retrofit.create(HttpBinService.class);
        Call<ResponseBody> call = httpBinService.man_hour(id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String string = null;
                try {
                    string = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Gson gson = new Gson();
                WorkerManHour workerManHour = gson.fromJson(string, WorkerManHour.class);
                Message message = new Message();
                message.obj = workerManHour;
                System.out.println("返回值：------>" + workerManHour);
                handler.sendMessage(message);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(WorkerTimeInquire.this, "服务器错误", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void display(WorkerManHour workerManHour) {
        binding.timeTvId2.setText(String.valueOf(workerManHour.getId()));
        binding.timeTvName2.setText(workerManHour.getName());
        binding.timeTvTi2.setText(String.valueOf(workerManHour.getMonthhour()));
        binding.timeTvMoney2.setText(String.valueOf(workerManHour.getMonthsalary()));
        binding.timeTvRent2.setText(String.valueOf(workerManHour.getChummage()));
        binding.timeTvAdvance2.setText(String.valueOf(workerManHour.getAdvancemoney()));
        binding.timeTvEndmoney2.setText(String.valueOf(workerManHour.getEndmoney()));
    }
}
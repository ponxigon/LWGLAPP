package com.example.laowuguanli.ui.activity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.laowuguanli.R;
import com.example.laowuguanli.bean.BoosDispatchWorkerIm;
import com.example.laowuguanli.bean.WorkerInformationBean;
import com.example.laowuguanli.biz.BoosDispatchAdapter;
import com.example.laowuguanli.biz.MessageDao;
import com.example.laowuguanli.biz.SqlLang;
import com.example.laowuguanli.broadcast.MessageReceiver;
import com.example.laowuguanli.databinding.ActivityBoosDispatchBinding;
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


//boos派遣员工
public class BoosDispatch extends BaseActivity {
    private ActivityBoosDispatchBinding binding;
    private ArrayList<HashMap<String, Object>> arrayList = new ArrayList<>();
    MessageDao messageDao;
    private String company;
    private final Handler handler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            ArrayList<HashMap<String, Object>> obj = (ArrayList<HashMap<String, Object>>) msg.obj;
            initView(obj);
        }
    };

    private void initView(ArrayList<HashMap<String, Object>> obj) {
        BoosDispatchAdapter boosDispatchAdpter = new BoosDispatchAdapter(obj, this);
        binding.boosStaffDispatch.setAdapter(boosDispatchAdpter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBoosDispatchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setUpToolBar("劳务管理");
        messageDao = new MessageDao(this);
        initData();
    }

    @SuppressLint("Range")
    private void initData() {
        new Thread(this::inter).start();
        Button button = findViewById(R.id.btn_sousuo);
        button.setOnClickListener(v -> Toast.makeText(BoosDispatch.this, "搜索功能暂未开放！", Toast.LENGTH_SHORT).show());

        int id = MessageReceiver.getId();
        @SuppressLint("Recycle") Cursor cursor = messageDao.database.query(SqlLang.CREATE_TABLE, null,
                "id like ?", new String[]{"" + id}, null, null, null);
        cursor.moveToNext();
        company = cursor.getString(cursor.getColumnIndex("company"));
        messageDao.database.close();
    }

    private void inter() {
        Retrofit build = new Retrofit.Builder().baseUrl(ConnectServer.url()).build();
        HttpBinService httpBinService = build.create(HttpBinService.class);
        Call<ResponseBody> call = httpBinService.dispatch(company);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String string;
                try {
                    assert response.body() != null;
                    string = response.body().string();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                BoosDispatchWorkerIm workerIm = new Gson().fromJson(string, BoosDispatchWorkerIm.class);
                ArrayList<WorkerInformationBean> workerList = workerIm.getWorkerList();
                for (int i = 0; i < workerList.size(); i++) {
                    WorkerInformationBean bean = workerList.get(i);
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("workerId", bean.getId());
                    map.put("workerName", bean.getName());
                    map.put("companyrear", bean.getCompanyrear());
                    map.put("operatingpost", bean.getOperatingpost());
                    arrayList.add(map);
                }
                Message message = new Message();
                message.obj = arrayList;
                handler.sendMessage(message);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }
}
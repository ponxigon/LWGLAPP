package com.example.laowuguanli.ui.activity;

import androidx.annotation.NonNull;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.laowuguanli.R;
import com.example.laowuguanli.bean.BoosLabourResponse;
import com.example.laowuguanli.bean.Feedback;
import com.example.laowuguanli.bean.WorkerMessageLabour;
import com.example.laowuguanli.biz.BoosLabourAdpter;
import com.example.laowuguanli.biz.MessageDao;
import com.example.laowuguanli.biz.SqlLang;
import com.example.laowuguanli.broadcast.BoosLabourReceiver;
import com.example.laowuguanli.broadcast.MessageReceiver;
import com.example.laowuguanli.net.ConnectServer;
import com.example.laowuguanli.net.HttpBinService;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class BoosLabourCheck extends BaseActivity {
    private ListView listView;
    ArrayList<HashMap<String, Object>> list = new ArrayList<>();
    private int id;
    MessageDao messageDao;
    HashMap<String, Object> map;
    private String company;

    private final Handler handler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            ArrayList<HashMap<String, Object>> list = (ArrayList<HashMap<String, Object>>) msg.obj;
            initView(list);
        }
    };
    private final HttpBinService httpBinService = new Retrofit.Builder().baseUrl(ConnectServer.url())
            .build().create(HttpBinService.class);
    private BoosLabourReceiver boosLabourReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boos_labour_check);
        listView = findViewById(R.id.Lv_table);
        initReceiver();
        initData();
        setUpToolBar("工人录用");
    }

    @SuppressLint("Range")
    private void initData() {
        new Thread(this::inter).start();
        id = MessageReceiver.getId();
        ArrayList<HashMap<String, Object>> list1 = boosLabourReceiver.getList();
        messageDao = new MessageDao(this);

        @SuppressLint("Recycle") Cursor cursor = messageDao.database.query(SqlLang.CREATE_TABLE,null,
                "id like ?", new String[]{"" + id}, null, null, null);
        cursor.moveToNext();
        company = cursor.getString(cursor.getColumnIndex("company"));
        messageDao.database.close();


        Message message = new Message();
        message.obj = list1;
        handler.handleMessage(message);

    }

    private void initReceiver() {
        boosLabourReceiver = new BoosLabourReceiver();
        IntentFilter workerList = new IntentFilter("workerList");
        registerReceiver(boosLabourReceiver,workerList);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(boosLabourReceiver);
    }
    private void initView(ArrayList<HashMap<String, Object>> list) {
        BoosLabourAdpter adpter = new BoosLabourAdpter(this, list, httpBinService, company);
        listView.setAdapter(adpter);
//        initAdapter(adpter);

    }
    int i = 0;

    //-------------前-------------------------------------------
    private void initAdapter(BoosLabourAdpter adpter) {


        System.out.println("进入了了了了"+i++);

        for (int i = 0; i < list.size(); i++) {
            View view = adpter.getView(i, listView, null);
            TextView textViewName = view.findViewById(R.id.tv_worker_name);
            Button buttonTrue = view.findViewById(R.id.labour_true);
            Button buttonFalse = view.findViewById(R.id.labour_false);
            textViewName.setText((CharSequence) list.get(i).get("workerName"));

            int workerId = (int) list.get(i).get("workerId");
            int finalI = i;
            System.out.println("有"+i+"个");
            buttonTrue.setOnClickListener(view1 -> boosAgree(finalI,workerId,MessageReceiver.getCompany(),true,adpter));
//            buttonFalse.setOnClickListener(view12 -> boosAgree(finalI,workerId,httpBinService,null,false,adpter));
//            buttonFalse.setOnClickListener(view12 -> System.out.println("点击了"));
            buttonFalse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("点击了");
                }
            });
            System.out.println("过了监听");
        }
        System.out.println("hou进入了了了了"+i++);


    }

    private void boosAgree(int i,int workerid,String company,boolean auditResult,BoosLabourAdpter adpter) {
        System.out.println("发送结果了"+i);
        Call<ResponseBody> call = httpBinService.boosagree(workerid, company,auditResult);
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
                Feedback feedback = new Gson().fromJson(string, Feedback.class);
                if (auditResult){
                    if (feedback.isReminder()){
                        Toast.makeText(BoosLabourCheck.this, "添加成功", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(BoosLabourCheck.this, "添加失败", Toast.LENGTH_SHORT).show();
                    }

                }else {
                    if (feedback.isReminder()){
                        Toast.makeText(BoosLabourCheck.this, "已拒绝", Toast.LENGTH_SHORT).show();

                    }else {
                        Toast.makeText(BoosLabourCheck.this, "拒绝失败", Toast.LENGTH_SHORT).show();
                    }
                }
                list.remove(list.get(i));
                adpter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(BoosLabourCheck.this, "网络错误", Toast.LENGTH_SHORT).show();
            }
        });

        //----------------------------------------------
    }
    private void inter() {
        Call<ResponseBody> call = httpBinService.booslabourService(id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String string = response.body().string();
                    BoosLabourResponse boosLabourResponse = new Gson().fromJson(string, BoosLabourResponse.class);
                    if (!"".equals(string)){
                        for (int i = 0; i < boosLabourResponse.getWorkerMessageLabours().length; i++) {
                            map = new HashMap<>();
                            map.put("workerName", boosLabourResponse.getWorkerMessageLabours()[i].getWorkerName());
                            map.put("workerId", boosLabourResponse.getWorkerMessageLabours()[i].getWorkerId());
                            list.add(map);
                        }
                        Message message = new Message();
                        message.obj = list;
                        handler.sendMessage(message);
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
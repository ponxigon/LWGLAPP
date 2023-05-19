package com.example.laowuguanli.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.laowuguanli.R;
import com.example.laowuguanli.bean.BoosLookWorkerResponse;
import com.example.laowuguanli.biz.MessageDao;
import com.example.laowuguanli.biz.SqlLang;
import com.example.laowuguanli.broadcast.MessageReceiver;
import com.example.laowuguanli.net.ConnectServer;
import com.example.laowuguanli.net.HttpBinService;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

//Boos查看员工信息
public class BoosStaffInformation extends BaseActivity {

    private Button button;
    private ListView listView;
    MessageDao messageDao;
    private SimpleAdapter adapter;
    List<Map<String, String>> list = new ArrayList<>();
    private int workerId;
    private String company;
    private final Handler handler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            List<Map<String, String>> list = (List<Map<String, String>>) msg.obj;
            initView(list);
            setUpToolBar("员工信息");
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boos_staff_information);
        listView = findViewById(R.id.boos_staff_information);
        button = findViewById(R.id.btn_shousuo);
        button.setOnClickListener(v -> Toast.makeText(BoosStaffInformation.this, "搜索功能暂未开放！", Toast.LENGTH_SHORT).show());
        messageDao = new MessageDao(this);
        initData();
        System.out.println("IDL:"+workerId);
    }

    private void initView(List<Map<String, String>> list) {
        adapter = new SimpleAdapter(this, list, R.layout.boos_staffinformation,
                new String[]{"name", "company", "sex", "job"}, new int[]{R.id.tv_name,
                R.id.work_company, R.id.tv_age, R.id.tv_job});
        listView.setAdapter(adapter);
    }

    @SuppressLint("Range")
    private void initData() {
        new Thread(this::inter).start();
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(BoosStaffInformation.this, WorkerPersonalInformation.class);
            Map<String, String> map = list.get(i);
            workerId = Integer.parseInt(map.get("id"));
            intent.putExtra("id", workerId);
            startActivity(intent);
        });
        int id = MessageReceiver.getId();
        @SuppressLint("Recycle") Cursor cursor = messageDao.database.query(SqlLang.CREATE_TABLE,null,
                "id like ?", new String[]{"" + id}, null, null, null);
        cursor.moveToNext();
        company = cursor.getString(cursor.getColumnIndex("company"));
        messageDao.database.close();
    }

    private void inter() {
        String url = ConnectServer.url();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(url).build();
        HttpBinService httpBinService = retrofit.create(HttpBinService.class);
        Call<ResponseBody> call = httpBinService.booslookworker(company);
        try {
            Response<ResponseBody> response = call.execute();
            assert response.body() != null;
            String string = response.body().string();
            Gson gson = new Gson();
            BoosLookWorkerResponse boosLookWorkerResponse = gson.fromJson(string, BoosLookWorkerResponse.class);
            if (boosLookWorkerResponse.getTotal() < boosLookWorkerResponse.getSize()) {
                for (int i = 0; i < boosLookWorkerResponse.getTotal(); i++) {
                    Map<String, String> map = new HashMap<>();
                    map.put("id", boosLookWorkerResponse.getRecords()[i].getId().toString());
                    map.put("name", boosLookWorkerResponse.getRecords()[i].getName());
                    map.put("company", boosLookWorkerResponse.getRecords()[i].getCompanyrear());
                    map.put("sex", boosLookWorkerResponse.getRecords()[i].getSex());
                    map.put("job", boosLookWorkerResponse.getRecords()[i].getOperatingpost());
                    list.add(map);
                }
            } else {
                for (int i = 0; i < boosLookWorkerResponse.getSize(); i++) {
                    Map<String, String> map = new HashMap<>();
                    map.put("id", boosLookWorkerResponse.getRecords()[i].getId().toString());
                    map.put("name", boosLookWorkerResponse.getRecords()[i].getName());
                    map.put("company", boosLookWorkerResponse.getRecords()[i].getCompanyrear());
                    map.put("sex", boosLookWorkerResponse.getRecords()[i].getSex());
                    map.put("job", boosLookWorkerResponse.getRecords()[i].getOperatingpost());
                    list.add(map);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Message message = new Message();
        message.obj = list;
        handler.sendMessage(message);
    }
}


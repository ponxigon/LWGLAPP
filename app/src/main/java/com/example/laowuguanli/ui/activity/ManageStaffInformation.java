package com.example.laowuguanli.ui.activity;

import androidx.annotation.NonNull;

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

import com.example.laowuguanli.R;
import com.example.laowuguanli.bean.ManageLookBoosResponse;
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

//管理部门查看企业信息
public class ManageStaffInformation extends BaseActivity {

    private Button button;
    private ListView listView;
    private SimpleAdapter adapter;
    List<Map<String, String>> list = new ArrayList<>();
    private int boosId;
    MessageDao messageDao;

    private final Handler handler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            List<Map<String, String>> list = (List<Map<String, String>>) msg.obj;
            initView(list);
        }
    };
    private int id;

    private void initView(List<Map<String, String>> list) {
        adapter = new SimpleAdapter(this, list, R.layout.manage_staffinformation,
                new String[]{"company"}, new int[]{R.id.tv_company});
        listView.setAdapter(adapter);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_staff_information);
        listView = findViewById(R.id.manage_staff_information);
        button = findViewById(R.id.btn_sousuo);
        button.setOnClickListener(v -> Toast.makeText(ManageStaffInformation.this, "搜索功能暂未开放！", Toast.LENGTH_SHORT).show());
        setUpToolBar("企业信息");
        id = MessageReceiver.getId();
        messageDao = new MessageDao(this);
        initData();
    }

    private void initData() {
        new Thread(this::inter).start();
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(ManageStaffInformation.this,BoosPersonalInformation.class);
            Map<String, String> map = list.get(i);
            boosId = Integer.parseInt(map.get("id"));
            intent.putExtra("id", boosId);
            startActivity(intent);
        });
    }

    @SuppressLint("Range")
    private void inter() {
        System.out.println("管理部门的ID:"+MessageReceiver.getId());
        String url = ConnectServer.url();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(url).build();
        HttpBinService httpBinService = retrofit.create(HttpBinService.class);
        Cursor cursor = messageDao.database.query(SqlLang.CREATE_TABLE, new String[]{"city"}, "id like ?",
                new String[]{"" + id}, null, null, null);
        cursor.moveToNext();
        String city = cursor.getString(cursor.getColumnIndex("city"));
        messageDao.database.close();



        Call<ResponseBody> call = httpBinService.manageLookBoos(city);
        try {
            Response<ResponseBody> response = call.execute();
            assert response.body() != null;
            String string = response.body().string();
            Gson gson = new Gson();
            System.out.println(string);
            ManageLookBoosResponse manageLookBoosResponse = gson.fromJson(string, ManageLookBoosResponse.class);
            if (manageLookBoosResponse.getTotal() < manageLookBoosResponse.getSize()) {
                for (int i = 0; i < manageLookBoosResponse.getTotal(); i++) {
                    Map<String, String> map = new HashMap<>();
                    map.put("id",String.valueOf(manageLookBoosResponse.getRecords()[i].getId()));
                    map.put("company", manageLookBoosResponse.getRecords()[i].getCompany());
                    list.add(map);
                }
            } else {
                for (int i = 0; i < manageLookBoosResponse.getSize(); i++) {
                    Map<String, String> map = new HashMap<>();
                    map.put("id",String.valueOf(manageLookBoosResponse.getRecords()[i].getId()));
                    map.put("company", manageLookBoosResponse.getRecords()[i].getCompany());
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
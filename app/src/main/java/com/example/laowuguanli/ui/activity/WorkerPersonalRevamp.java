package com.example.laowuguanli.ui.activity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import com.example.laowuguanli.bean.Feedback;
import com.example.laowuguanli.biz.MessageDao;
import com.example.laowuguanli.biz.SqlLang;
import com.example.laowuguanli.broadcast.MessageReceiver;
import com.example.laowuguanli.databinding.ActivityWorkerPersonalRevampBinding;
import com.example.laowuguanli.net.ConnectServer;
import com.example.laowuguanli.net.HttpBinService;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class WorkerPersonalRevamp extends BaseActivity {

    private ActivityWorkerPersonalRevampBinding binding;
    private int id;
    private String sex = "男";
    MessageDao messageDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWorkerPersonalRevampBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        id = MessageReceiver.getId();
        setUpToolBar("修改资料");
        initEvent();
    }

    @SuppressLint("Range")
    private void initData() {
        Cursor cursor = messageDao.database.query(SqlLang.CREATE_TABLE,null,
                "id like ?", new String[]{"" + id}, null, null, null);
        cursor.moveToNext();
        String name = cursor.getString(cursor.getColumnIndex("name"));
        String id = cursor.getString(cursor.getColumnIndex("identitycard"));
        String bankid = cursor.getString(cursor.getColumnIndex("bankid"));
        binding.informationName2.setHint(name);
        binding.informationId2.setHint(id);
        binding.informationBankid2.setHint(bankid);

    }

    @Override
    protected void onStart() {
        super.onStart();
        messageDao = new MessageDao(this);
        initData();
    }

    private void initEvent() {
        binding.btnInformationChanges.setOnClickListener(view -> new Thread(this::submit).start());
        binding.workerAgeMan.setOnClickListener(view -> sex = "男");
        binding.workerAgeWoman.setOnClickListener(view -> sex = "女");
    }

    private void submit() {
        String url = ConnectServer.url();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(url).build();
        HttpBinService httpBinService = retrofit.create(HttpBinService.class);
        String name = ifNull(binding.informationName2.getText().toString());
        String identitycard = ifNull(binding.informationId2.getText().toString());
        String bankid = ifNull(binding.informationBankid2.getText().toString());
        Call<ResponseBody> call = httpBinService.workerRevamp(id, name, sex
                , identitycard, bankid);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Feedback feedback = null;
                try {
                    String string = response.body().string();
                    Gson gson = new Gson();
                    feedback = gson.fromJson(string, Feedback.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                assert feedback != null;
                if (feedback.isReminder()) {
                    Toast.makeText(WorkerPersonalRevamp.this, "修改成功", Toast.LENGTH_SHORT).show();
                    ContentValues values = new ContentValues();
                    values.put("name",name);
                    values.put("identitycard",identitycard);
                    values.put("bankid",bankid);
                    messageDao.database.update(SqlLang.CREATE_TABLE,values,"id like ?",new String[] {String.valueOf(id)});
                } else {
                    Toast.makeText(WorkerPersonalRevamp.this, "修改失败", Toast.LENGTH_SHORT).show();
                }
                messageDao.database.close();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(WorkerPersonalRevamp.this, "服务器错误", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String ifNull(String text) {
        return text.equals("") ? null : text;
    }
}
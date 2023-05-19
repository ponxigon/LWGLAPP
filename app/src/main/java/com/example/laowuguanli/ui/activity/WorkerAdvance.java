package com.example.laowuguanli.ui.activity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.laowuguanli.R;
import com.example.laowuguanli.bean.AdvanceJiLu_Bao;
import com.example.laowuguanli.bean.Feedback;
import com.example.laowuguanli.bean.WorkerAdvanceJiLu;
import com.example.laowuguanli.biz.AdvanceAdapter;
import com.example.laowuguanli.biz.MessageDao;
import com.example.laowuguanli.biz.SqlLang;
import com.example.laowuguanli.broadcast.MessageReceiver;
import com.example.laowuguanli.net.ConnectServer;
import com.example.laowuguanli.net.HttpBinService;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class WorkerAdvance extends BaseActivity {
    private EditText editText;
    private ListView listView;
    private int workerId;
    private ArrayList<WorkerAdvanceJiLu> advanceJiLus = new ArrayList<>();

    private String company;
    private final HttpBinService httpBinService = new Retrofit.Builder().baseUrl(ConnectServer.url())
            .build().create(HttpBinService.class);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_advance);
        setUpToolBar("预支工资");
        initView();
        initData();
    }

    @SuppressLint("Range")
    private void initData() {
        workerId = MessageReceiver.getId();
        MessageDao messageDao = new MessageDao(this);
        @SuppressLint("Recycle") Cursor query = messageDao.database.query(SqlLang.CREATE_TABLE, new String[]{"companyfront"},
                "id like ?", new String[]{"" + workerId}, null, null, null);
        query.moveToNext();
        company = query.getString(query.getColumnIndex("companyfront"));
        messageDao.database.close();
        new Thread(this::inter_2).start();
    }

    private void inter_2() {
        Call<ResponseBody> call = httpBinService.workerGetAdvance(workerId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    assert response.body() != null;
                    String string = response.body().string();
                    advanceJiLus = new Gson().fromJson(string, AdvanceJiLu_Bao.class).getAdvanceJiLus();

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                handler.sendMessage(new Message());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void initView() {
        listView = findViewById(R.id.lv_adavance);
        editText = findViewById(R.id.tv_advance_2);
        Button button = findViewById(R.id.btn_advance);
        button.setOnClickListener(view -> new Thread(this::inter).start());
    }

    private void inter() {
        Call<ResponseBody> call = httpBinService.workerAdvance(workerId,company, editText.getText().toString());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    assert response.body() != null;
                    String string = response.body().string();
                    boolean reminder = new Gson().fromJson(string, Feedback.class).isReminder();
                    if (reminder){
                        Toast.makeText(WorkerAdvance.this, "提交申请成功", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(WorkerAdvance.this, "未加入公司", Toast.LENGTH_SHORT).show();
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

    private final Handler handler = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            dataShow();
        }
    };

    private void dataShow() {
        AdvanceAdapter adapter = new AdvanceAdapter(this, advanceJiLus);
        listView.setAdapter(adapter);
    }

}
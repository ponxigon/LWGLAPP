package com.example.laowuguanli.ui.activity;

import androidx.annotation.NonNull;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.TextView;
import android.widget.Toast;

import com.example.laowuguanli.R;
import com.example.laowuguanli.bean.WorkerInformationBean;
import com.example.laowuguanli.broadcast.MessageReceiver;
import com.example.laowuguanli.net.ConnectServer;
import com.example.laowuguanli.net.HttpBinService;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class WorkerPersonalInformation extends BaseActivity {
    private TextView tv_workid;
    private TextView tv_name;
    private TextView tv_age;
    private TextView tv_identity_card;
    private TextView tv_company;
    private TextView tv_plant;
    private TextView tv_post;
    private TextView tv_bankid;
    HttpBinService httpBinService;
    private int id;


    private final Handler handler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            WorkerInformationBean workerInformationBean = (WorkerInformationBean) msg.obj;
            show(workerInformationBean);
            setUpToolBar("工人个人信息");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_personal_information);
        form(MessageReceiver.getForm());
        initEvent();
        new Thread(() -> inquireInformation(id)).start();
    }


    private void form(String form) {
        if (form.equals("工人")){
            id = MessageReceiver.getId();
        }else if (form.equals("企业")){
            id = getIntent().getIntExtra("id",-1);
        }
    }

    private void initEvent() {
        tv_workid = findViewById(R.id.information_workid_2);
        tv_name = findViewById(R.id.information_name_2);
        tv_age = findViewById(R.id.information_age_2);
        tv_identity_card = findViewById(R.id.information_id_2);
        tv_company = findViewById(R.id.information_company_2);
        tv_plant = findViewById(R.id.information_plant_2);
        tv_post = findViewById(R.id.information_post_2);
        tv_bankid = findViewById(R.id.information_bankid_2);
    }

    private void show(WorkerInformationBean workerInformationBean) {
        String id = String.valueOf(workerInformationBean.getId());
        tv_workid.setText(id);
        tv_name.setText(workerInformationBean.getName());
        tv_age.setText(workerInformationBean.getSex());
        tv_identity_card.setText(workerInformationBean.getIdentitycard());
        tv_company.setText(workerInformationBean.getCompanyfront());
        tv_plant.setText(workerInformationBean.getCompanyrear());
        tv_post.setText(workerInformationBean.getOperatingpost());
        tv_bankid.setText(workerInformationBean.getBankid());
    }

    //联网
    private void inquireInformation(int id) {
        String url = ConnectServer.url();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(url).build();
        httpBinService = retrofit.create(HttpBinService.class);
        Call<ResponseBody> call = httpBinService.getWorkerInformation(id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                try {
                    assert response.body() != null;
                    String string = response.body().string();
                    Gson gson = new Gson();
                    WorkerInformationBean workerInformationBean = gson.fromJson(string, WorkerInformationBean.class);
                    analysis(workerInformationBean);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(WorkerPersonalInformation.this, "服务器错误", Toast.LENGTH_SHORT).show();
            }
        });

    }

    //解析response数据
    private void analysis(WorkerInformationBean workerInformationBean) throws IOException {
        System.out.println(workerInformationBean);
        Message message = new Message();
        message.obj = workerInformationBean;
        handler.sendMessage(message);
    }
}
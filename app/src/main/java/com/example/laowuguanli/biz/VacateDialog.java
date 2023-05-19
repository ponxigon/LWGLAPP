package com.example.laowuguanli.biz;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.Toast;

import com.example.laowuguanli.bean.Feedback;
import com.example.laowuguanli.net.ConnectServer;
import com.example.laowuguanli.net.HttpBinService;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class VacateDialog {
    private final int vacateId;
    private final Context context;
    private final String vacatecause;
    private final HttpBinService httpBinService = new Retrofit.Builder().
            baseUrl(ConnectServer.url()).build().create(HttpBinService.class);


    public VacateDialog(int vacateId, Context context, String vacatecause) {
        this.vacateId = vacateId;
        this.context = context;
        this.vacatecause = vacatecause;
    }

    public void vacateLog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("请假事由");
        builder.setMessage(vacatecause);
        builder.setPositiveButton("通过申请", (dialogInterface, i) -> httpBinService
                .boosReplyVacate(vacateId,true).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    assert response.body() != null;
                    String string = response.body().string();
                    boolean reminder = new Gson().fromJson(string, Feedback.class).isReminder();
                    if (reminder){
                        Toast.makeText(context, "同意了", Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        }));
        builder.setNegativeButton("拒绝申请", (dialogInterface, i) -> httpBinService
                .boosReplyVacate(vacateId,false).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    assert response.body() != null;
                    String string = response.body().string();
                    boolean reminder = new Gson().fromJson(string, Feedback.class).isReminder();
                    if (reminder){
                        Toast.makeText(context, "已拒绝", Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        }));
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}

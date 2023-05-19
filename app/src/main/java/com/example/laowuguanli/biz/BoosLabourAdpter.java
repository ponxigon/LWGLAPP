package com.example.laowuguanli.biz;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.laowuguanli.R;
import com.example.laowuguanli.bean.Feedback;
import com.example.laowuguanli.net.HttpBinService;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BoosLabourAdpter extends BaseAdapter {
    Context context;
    ArrayList<HashMap<String,Object>> list;
    HttpBinService httpBinService;
    String company;

    public BoosLabourAdpter(Context context, ArrayList<HashMap<String, Object>> list,
                            HttpBinService httpBinService, String company) {
        this.context = context;
        this.list = list;
        this.httpBinService = httpBinService;
        this.company = company;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.boos_labour,null);
        TextView textViewName = view.findViewById(R.id.tv_worker_name);
        Button buttonTrue = view.findViewById(R.id.labour_true);
        Button buttonFalse = view.findViewById(R.id.labour_false);
        textViewName.setText((CharSequence) list.get(i).get("workerName"));

        int workerId = (int) list.get(i).get("workerId");
        buttonTrue.setOnClickListener(view1 -> boosAgree(i,workerId,httpBinService,company,true));
        buttonFalse.setOnClickListener(view12 -> boosAgree(i,workerId,httpBinService,null,false));
        return view;
    }

    private void boosAgree(int i,int workerid,HttpBinService httpBinService,String company,boolean auditResult) {
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
                        Toast.makeText(context, "添加成功", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(context, "添加失败", Toast.LENGTH_SHORT).show();
                    }

                }else {
                    if (feedback.isReminder()){
                        Toast.makeText(context, "已拒绝", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(context, "拒绝失败", Toast.LENGTH_SHORT).show();
                    }
                }
                deletedData(i);

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context, "网络错误", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void deletedData(int i) {
        list.remove(i);
        Intent intent = new Intent();
        intent.putExtra("msg",list);
        intent.setAction("workerList");
        context.sendBroadcast(intent);
    }
}

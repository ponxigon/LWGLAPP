package com.example.laowuguanli.biz;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.laowuguanli.R;
import com.example.laowuguanli.ui.activity.DispatchPage;

import java.util.ArrayList;
import java.util.HashMap;

public class BoosDispatchAdapter extends BaseAdapter {
    private ArrayList<HashMap<String,Object>> arrayList;
    Context context;


    public BoosDispatchAdapter(ArrayList<HashMap<String, Object>> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.dispatch,null);
        TextView tv_name = view.findViewById(R.id.tv_worker_name);
        TextView tv_companyrear = view.findViewById(R.id.tv_company_shang);
        TextView tv_operatingpost = view.findViewById(R.id.company_xia);
        Button button = view.findViewById(R.id.btn_bt);
        tv_name.setText((String) arrayList.get(i).get("workerName"));
        tv_companyrear.setText((String) arrayList.get(i).get("companyrear"));
        tv_operatingpost.setText((String) arrayList.get(i).get("operatingpost"));
        button.setOnClickListener(view1 -> {
            Intent intent = new Intent(context, DispatchPage.class);
            intent.putExtra("workerId",(Integer) arrayList.get(i).get("workerId"));
            context.startActivity(intent);
        });
        return view;
    }
}
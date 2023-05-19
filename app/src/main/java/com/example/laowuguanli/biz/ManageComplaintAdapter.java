package com.example.laowuguanli.biz;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.laowuguanli.R;
import com.example.laowuguanli.ui.activity.ManageLookComplaint;

import java.util.ArrayList;
import java.util.HashMap;

public class ManageComplaintAdapter extends BaseAdapter {

    ArrayList<HashMap<String,String>> arrayList;
    Context context;

    public ManageComplaintAdapter(ArrayList<HashMap<String, String>> arrayList, Context context) {
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
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.complant_box,null);
        TextView tv_worker_name = view.findViewById(R.id.tv_2);
        tv_worker_name.setText(arrayList.get(i).get("workerName"));
        view.setOnClickListener(view1 -> {
            Intent intent = new Intent(context, ManageLookComplaint.class);
            intent.putExtra("complaintId", arrayList.get(i).get("complaintId"));
            context.startActivity(intent);
        });
        return view;
    }
}

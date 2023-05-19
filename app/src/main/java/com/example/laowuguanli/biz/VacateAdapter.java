package com.example.laowuguanli.biz;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.laowuguanli.R;

import java.util.ArrayList;
import java.util.HashMap;

public class VacateAdapter extends BaseAdapter {
    private ArrayList<HashMap<String,String>> arrayList = new ArrayList<>();
    Context context;

    public VacateAdapter(ArrayList<HashMap<String, String>> arrayList, Context context) {
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

    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.worker_vacate_listview,null);
        TextView textView1 = view.findViewById(R.id.tv_vacate_date);
        TextView textView2 = view.findViewById(R.id.tv_state);
        textView1.setText(arrayList.get(i).get("vacatedate"));
        textView2.setText(arrayList.get(i).get("state"));
        System.out.println("显示vacatedate="+arrayList.get(i).get("vacatedate"));
        System.out.println("显示state="+arrayList.get(i).get("state"));
        return view;
    }
}

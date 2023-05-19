package com.example.laowuguanli.biz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.laowuguanli.R;
import com.example.laowuguanli.bean.WorkerAdvanceJiLu;

import java.util.ArrayList;

public class AdvanceAdapter extends BaseAdapter {
    private final Context context;
    private final ArrayList<WorkerAdvanceJiLu> advanceJiLus;

    public AdvanceAdapter(Context context, ArrayList<WorkerAdvanceJiLu> advanceJiLus) {
        this.context = context;
        this.advanceJiLus = advanceJiLus;
    }

    @Override
    public int getCount() {
        return advanceJiLus.size();
    }

    @Override
    public Object getItem(int i) {
        return advanceJiLus.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.worker_vacate_listview,null);
        TextView textView1 = view.findViewById(R.id.tv_vacate_date);
        TextView textView2 = view.findViewById(R.id.tv_state);
        String money = String.valueOf(advanceJiLus.get(i).getMoney());
        textView1.setText(money);
        textView2.setText(advanceJiLus.get(i).getState());
        return view;
    }
}

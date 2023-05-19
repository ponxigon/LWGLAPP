package com.example.laowuguanli.biz;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.laowuguanli.R;
import com.example.laowuguanli.bean.BoosVacate;

import java.util.ArrayList;

public class BoosVacateAdapter extends BaseAdapter {
    private final ArrayList<BoosVacate> arrayList;
    Context context;

    VacateDialog vacateDialog;

    public BoosVacateAdapter(ArrayList<BoosVacate> arrayList, Context context) {
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
        view = LayoutInflater.from(context).inflate(R.layout.boos_vacate_3,null);
        TextView name = view.findViewById(R.id.tv_worker_name);
        TextView date_1 = view.findViewById(R.id.riqi_1);
        TextView date_2 = view.findViewById(R.id.riqi_2);
        name.setText(arrayList.get(i).getWorkerName());
        date_1.setText(arrayList.get(i).getVacatedate());
        date_2.setText(arrayList.get(i).getReturndate());
        view.setOnClickListener(view1 -> {
            vacateDialog = new VacateDialog(arrayList.get(i).getVacateId(),context,arrayList.get(i).getVacatecause());
            vacateDialog.vacateLog();
        });
        return view;
    }
}

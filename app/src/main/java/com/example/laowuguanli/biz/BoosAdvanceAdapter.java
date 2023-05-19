package com.example.laowuguanli.biz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.laowuguanli.R;
import com.example.laowuguanli.bean.BoosLookAdvance;

import java.util.ArrayList;

public class BoosAdvanceAdapter extends BaseAdapter {
    private final Context context;
    private final ArrayList<BoosLookAdvance> arrayList;

    public BoosAdvanceAdapter(Context context, ArrayList<BoosLookAdvance> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
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
        view = LayoutInflater.from(context).inflate(R.layout.boos_advance,null);
        TextView workerName = view.findViewById(R.id.tv_name);
        TextView monry = view.findViewById(R.id.tv_money);
        TextView tv_workerId = view.findViewById(R.id.worker_id);
        String workerid = String.valueOf(arrayList.get(i).getWorkerId());
        tv_workerId.setText(workerid);
        System.out.println("AAAAA>"+arrayList.get(i).getWorkerName());
        workerName.setText(arrayList.get(i).getWorkerName());
        String money = String.valueOf(arrayList.get(i).getMoney());
        monry.setText(money);
        view.setOnClickListener(view1 -> {
            BoosAdvanceDialog dialog = new BoosAdvanceDialog(arrayList.get(i).getAdvanceId(), context);
            dialog.vacateLog();
        });
        return view;
    }
}

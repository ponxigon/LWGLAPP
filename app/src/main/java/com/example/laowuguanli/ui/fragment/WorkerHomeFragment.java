package com.example.laowuguanli.ui.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.laowuguanli.R;
import com.example.laowuguanli.ui.activity.WorkerAdvance;
import com.example.laowuguanli.ui.activity.WorkerComplaint;
import com.example.laowuguanli.ui.activity.WorkerLabourService;
import com.example.laowuguanli.ui.activity.WorkerPersonalInformation;
import com.example.laowuguanli.ui.activity.WorkerTimeInquire;
import com.example.laowuguanli.ui.activity.WorkerVacate;

public class WorkerHomeFragment extends Fragment implements View.OnClickListener {

    private View view_worker_time;
    private View view_worker_vacate;
    private View view_worker_information;
    private View view_worker_complaint;
    private View view_worker_advance;
    private View view_worker_labour;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view_worker_time = view.findViewById(R.id.service_worker_time);
        view_worker_time.setOnClickListener(this);
        view_worker_vacate = view.findViewById(R.id.service_vacate);
        view_worker_vacate.setOnClickListener(this);
        view_worker_complaint = view.findViewById(R.id.service_complaint);
        view_worker_complaint.setOnClickListener(this);
        view_worker_information = view.findViewById(R.id.service_information);
        view_worker_information.setOnClickListener(this);
        view_worker_advance = view.findViewById(R.id.service_advance);
        view_worker_advance.setOnClickListener(this);
        view_worker_labour = view.findViewById(R.id.labour_service);
        view_worker_labour.setOnClickListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_worker_home, container, false);
    }


    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
                //工时薪资
            case R.id.service_worker_time:
                intent = new Intent(getActivity(), WorkerTimeInquire.class);
                startActivity(intent);
                break;
                //请假调休
            case R.id.service_vacate:
                intent = new Intent(getActivity(), WorkerVacate.class);
                startActivity(intent);
                break;
                //个人信息
            case R.id.service_information:
                intent = new Intent(getActivity(), WorkerPersonalInformation.class);
                startActivity(intent);
                break;
                //投诉反馈
            case R.id.service_complaint:
                intent = new Intent(getActivity(),WorkerComplaint.class);
                startActivity(intent);
                break;
                //预支工资
            case R.id.service_advance:
                intent = new Intent(getActivity(),WorkerAdvance.class);
                startActivity(intent);
                break;
            case R.id.labour_service:
                intent = new Intent(getActivity(), WorkerLabourService.class);
//                intent.putExtra("id",id);
                startActivity(intent);
                break;
        }
    }
}
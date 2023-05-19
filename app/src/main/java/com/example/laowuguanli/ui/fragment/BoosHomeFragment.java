package com.example.laowuguanli.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.laowuguanli.databinding.FragmentBoosHomeBinding;
import com.example.laowuguanli.ui.activity.BoosAdvance;
import com.example.laowuguanli.ui.activity.BoosDispatch;
import com.example.laowuguanli.ui.activity.BoosLabourCheck;
import com.example.laowuguanli.ui.activity.BoosPersonalInformation;
import com.example.laowuguanli.ui.activity.BoosStaffInformation;
import com.example.laowuguanli.ui.activity.WorkerTimeInquire;
import com.example.laowuguanli.ui.activity.WorkerVacate_3;


public class BoosHomeFragment extends Fragment {
    private FragmentBoosHomeBinding binding;
    private Intent intent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBoosHomeBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.serviceStaffInformation.setOnClickListener(view1 -> {
            intent = new Intent(getActivity(), BoosStaffInformation.class);
            startActivity(intent);
        });
        binding.serviceVacate.setOnClickListener(view12 -> {
            intent = new Intent(getActivity(), WorkerVacate_3.class);
            startActivity(intent);
        });
        binding.serviceCompany.setOnClickListener(view14 -> {
            intent = new Intent(getActivity(), BoosPersonalInformation.class);
            startActivity(intent);
        });
        binding.serviceHiring.setOnClickListener(view13 -> {
            intent = new Intent(getActivity(), BoosLabourCheck.class);
            startActivity(intent);
        });
        binding.serviceDispatch.setOnClickListener(view15 -> {
            intent = new Intent(getActivity(), BoosDispatch.class);
            startActivity(intent);
        });
        binding.serviceAdvance.setOnClickListener(view16 -> {
            intent = new Intent(getActivity(), BoosAdvance.class);
            startActivity(intent);
        });
    }
}
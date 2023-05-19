package com.example.laowuguanli.ui.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.laowuguanli.databinding.FragmentManageHomeBinding;
import com.example.laowuguanli.ui.activity.ManageComplaint;
import com.example.laowuguanli.ui.activity.ManagePersonalInformation;
import com.example.laowuguanli.ui.activity.ManageStaffInformation;


public class ManageHomeFragment extends Fragment {
    private FragmentManageHomeBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentManageHomeBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.serviceInquireCompany.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), ManageStaffInformation.class);
            startActivity(intent);
        });
        binding.serviceLetterBox.setOnClickListener(view2 -> {
            Intent intent = new Intent(getActivity(), ManageComplaint.class);
            startActivity(intent);
        });
        binding.serviceInformation.setOnClickListener(view3 -> {
            Intent intent = new Intent(getActivity(), ManagePersonalInformation.class);
            startActivity(intent);
        });
    }
}
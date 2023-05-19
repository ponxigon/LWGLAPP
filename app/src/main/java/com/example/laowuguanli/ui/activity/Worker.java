package com.example.laowuguanli.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.laowuguanli.R;
import com.example.laowuguanli.ui.fragment.WorkerCenterFragment;
import com.example.laowuguanli.ui.fragment.WorkerHomeFragment;
import com.example.laowuguanli.ui.fragment.WorkerMessageFragment;

import java.util.ArrayList;
import java.util.List;

public class Worker extends AppCompatActivity {
    private final Fragment fragmentCenter = new WorkerCenterFragment();
    private final Fragment fragmentHome = new WorkerHomeFragment();
    private final Fragment fragmentMessage = new WorkerMessageFragment();
    private ImageView img1;
    private ImageView img2;
    private ImageView img3;
    private List<Fragment> fragmentList;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker);
        viewPager = findViewById(R.id.vp_worker);
        initData();
        initView();
        fragmentmanager();
        selectedhome();
    }

    private void initView() {
        img1 = findViewById(R.id.imbtn_service);
        img2 = findViewById(R.id.imbtn_message);
        img3 = findViewById(R.id.imbtn_personal);
    }

    private void fragmentmanager() {
        WorkerFragmentVPAdapter fragmentVPAdapter = new WorkerFragmentVPAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(fragmentVPAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                onViewPagerSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initData() {
        fragmentList = new ArrayList<>();
        fragmentList.add(fragmentHome);
        fragmentList.add(fragmentMessage);
        fragmentList.add(fragmentCenter);
    }

    public void jumpToWorkerHomeFragment(View view) {
//        replaceFragment(fragmentHome);
        viewPager.setCurrentItem(0);
    }

    public void jumpToWorkerMessageFragment(View view) {
//        replaceFragment(fragmentMessage);
        viewPager.setCurrentItem(1);
    }

    public void jumpToWorkerCenterFragment(View view) {
//        replaceFragment(fragmentCenter);
        viewPager.setCurrentItem(2);
    }

    private void onViewPagerSelected(int position) {
        switch (position) {
            case 0:
                selectedhome();
                break;
            case 1:
                selectedMessage();
                break;
            case 2:
                selectedcenter();
                break;
        }
    }

    private void selectedhome() {
        img1.setSelected(true);
        img2.setSelected(false);
        img3.setSelected(false);
    }

    private void selectedMessage() {
        img1.setSelected(false);
        img2.setSelected(true);
        img3.setSelected(false);
    }

    private void selectedcenter() {
        img1.setSelected(false);
        img2.setSelected(false);
        img3.setSelected(true);
    }
}
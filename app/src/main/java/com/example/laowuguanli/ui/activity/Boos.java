package com.example.laowuguanli.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.laowuguanli.R;
import com.example.laowuguanli.broadcast.OtherReceiver;
import com.example.laowuguanli.ui.fragment.BoosCenterFragment;
import com.example.laowuguanli.ui.fragment.BoosHomeFragment;
import com.example.laowuguanli.ui.fragment.BoosMessageFragment;

import java.util.ArrayList;
import java.util.List;

public class Boos extends AppCompatActivity {
    private final Fragment fragmentCenter = new BoosCenterFragment();
    private final Fragment fragmentHome = new BoosHomeFragment();
    private final Fragment fragmentMessage = new BoosMessageFragment();
    private View img1;
    private RelativeLayout img2;
    private RelativeLayout img3;
    private List<Fragment> fragmentList;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boos);
        viewPager = findViewById(R.id.vp_boos);
        initView();
        initData();
        fragmentmanager();
        selectedhome();
    }

    private void initView() {
        img1 = findViewById(R.id.layout_home);
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


    public void jumpToBoosHomeFragment(View view) {
        viewPager.setCurrentItem(0);
        selectedhome();
    }

    public void jumpToBoosMessageFragment(View view) {
        viewPager.setCurrentItem(1);
        selectedMessage();
    }

    public void jumpToBoosCenterFragment(View view) {
        viewPager.setCurrentItem(2);
        selectedcenter();
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
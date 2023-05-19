package com.example.laowuguanli.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.laowuguanli.R;

public class WorkerVacate extends BaseActivity implements View.OnClickListener {

    private Button button_1;
    private Button button_2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_vacate);
        setUpToolBar("请假调休");
        button_1 = findViewById(R.id.btn_vacate_1);
        button_1.setOnClickListener(this);
        button_2 = findViewById(R.id.btn_vacate_2);
        button_2.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_vacate_1:
                startActivity(new Intent(this,WorkerVacate_1.class));
                break;
            case R.id.btn_vacate_2:
                startActivity(new Intent(this,WorkerVacate_2.class));
                break;
        }
    }
}
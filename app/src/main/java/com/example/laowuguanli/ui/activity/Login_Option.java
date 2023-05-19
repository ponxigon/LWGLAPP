package com.example.laowuguanli.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.laowuguanli.R;

public class Login_Option extends BaseActivity {

    private Button button1;
    private Button button2;
    private Button button3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_option);
        initView();
        initEvent();
        setUpToolBar("注册");
    }

    private void initEvent() {
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login_Option.this, Login_Worker.class);
                startActivity(intent);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login_Option.this, Login_Boos.class);
                startActivity(intent);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login_Option.this, Login_manage.class);
                startActivity(intent);
            }
        });

    }

    private void initView() {
        button1 = findViewById(R.id.login_btn_worker);
        button2 = findViewById(R.id.login_btn_boos);
        button3 = findViewById(R.id.login_btn_manage);
    }
}
package com.example.laowuguanli.ui.activity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.laowuguanli.R;
import com.example.laowuguanli.bean.UserOtherMessage;
import com.example.laowuguanli.bean.UserResult;
import com.example.laowuguanli.biz.MessageDao;
import com.example.laowuguanli.biz.SqlLang;
import com.example.laowuguanli.databinding.ActivityMainBinding;
import com.example.laowuguanli.net.ConnectServer;
import com.example.laowuguanli.net.HttpBinService;
import com.example.laowuguanli.net.ReceiverServer;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    protected ActivityMainBinding binding;
    private String number;
    private final Handler handler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            binding.qqq.setText(msg.obj.toString());
        }
    };
    MessageDao messageDao;

    private final Retrofit retrofit = new Retrofit.Builder().baseUrl(ConnectServer.url()).build();
    private final HttpBinService httpBinService = retrofit.create(HttpBinService.class);
    private Message message;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //创建sqlite的类对象
        messageDao = new MessageDao(this);
        HashMap<String, Object> hashMap = getAutomaticLogin();
        System.out.println("hashMap" + hashMap);
        if (hashMap != null) {
            int id = (Integer) hashMap.get("id");
            System.out.println("hashMap" + id);
            String account = (String) hashMap.get("account");
            Intent intent = null;
            Intent intent1 = new Intent();

            switch (Objects.requireNonNull(account)) {
                case "工人":
                    intent = new Intent(this, Worker.class);
                    intent1.putExtra("id", id);
                    intent1.putExtra("form", "工人");
                    break;
                case "企业":
                    intent = new Intent(this, Boos.class);
                    intent1.putExtra("id", id);
                    intent1.putExtra("form", "企业");
                    break;
                case "管理部门":
                    intent = new Intent(this, Manage.class);
                    intent1.putExtra("id", id);
                    intent1.putExtra("form", "管理部门");
                    break;
            }
            intent1.setAction(ReceiverServer.MAIN_ACTION);
            intent1.setPackage(getPackageName());
            sendBroadcast(intent1);
            otherMessage(id, account);
            assert intent != null;
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);//清除栈顶Activity
            startActivity(intent);
            finish();
        } else {
            initEvent();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        messageDao = new MessageDao(this);
    }

    private void initEvent() {
        binding.btnEnter.setOnClickListener(this);
        binding.btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_enter:
                new Thread(this::retrofit).start();
                break;
            case R.id.btn_login:
                startActivity(new Intent(this, Login_Option.class));
                break;
        }

    }

    private void retrofit() {
        number = binding.etNumber.getText().toString();
        Call<ResponseBody> call = httpBinService.loginpost(number, binding.etPsd.getText().toString());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.body() != null) {
                    message = new Message();
                    message.obj = "顺畅";
                    try {
                        verify(response);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    handler.sendMessage(message);
                } else {
                    Toast.makeText(MainActivity.this, "没有返回体", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(MainActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void verify(@NonNull Response<ResponseBody> response) throws IOException {
        int id;
        assert response.body() != null;
        String result = response.body().string();
        UserResult userResult = new Gson().fromJson(result, UserResult.class);
        if (userResult.getZh() == 1) {
            Intent intent = null;
            String type = null;
            Intent intent_message = new Intent();
            String accountType = userResult.getAccountType();
            Toast.makeText(this, accountType, Toast.LENGTH_SHORT).show();
            switch (accountType) {
                case "工人":
                    Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
                    intent = new Intent(this, Worker.class);
                    intent_message.putExtra("form", "工人");
                    type = "工人";
                    break;
                case "企业":
                    Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
                    intent = new Intent(this, Boos.class);
                    intent_message.putExtra("form", "企业");
                    type = "企业";
                    break;
                case "管理部门":
                    Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
                    intent = new Intent(this, Manage.class);
                    intent_message.putExtra("form", "管理部门");
                    type = "管理部门";
                    break;
            }
            id = userResult.getId();
            intent_message.setAction(ReceiverServer.MAIN_ACTION);
            intent_message.putExtra("id", id);
            intent_message.putExtra("number", number);
            intent_message.setPackage(getPackageName());
            sendBroadcast(intent_message);
            otherMessage(id, type);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            assert intent != null;
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);//清除栈顶Activity
            startActivity(intent);
            finish();
            automaticLogin(id, type);

        } else if (userResult.getZh() == 0) {
            Toast.makeText(this, "账号不存在", Toast.LENGTH_SHORT).show();
        } else if (userResult.getZh() == -1) {
            Toast.makeText(this, "密码错误", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "嘻哈", Toast.LENGTH_SHORT).show();
        }
    }

    //保存自动登录信息
    private void automaticLogin(int id, String account) {
        preferences = getSharedPreferences("user_info", MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("id", id);
        editor.putString("account", account);
        editor.putBoolean("isLogin", true);
        editor.apply();
    }

    //检测自动登录
    private HashMap<String, Object> getAutomaticLogin() {
        preferences = getSharedPreferences("user_info", MODE_PRIVATE);
        boolean isLogin = preferences.getBoolean("isLogin", false);
        HashMap<String, Object> map = new HashMap<>();
        if (isLogin) {
            map.put("id", preferences.getInt("id", -1));
            map.put("account", preferences.getString("account", null));
            return map;
        } else {
            return null;
        }
    }


    private void otherMessage(int id, String type) {
        Call<ResponseBody> call = httpBinService.userOther(id, type);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    assert response.body() != null;
                    String string = response.body().string();
                    UserOtherMessage userOtherMessage = new Gson().fromJson(string, UserOtherMessage.class);
                    //sqlite建表
                    ContentValues values = new ContentValues();
                    values.put("id", id);
                    values.put("name", userOtherMessage.getName());
                    values.put("company", userOtherMessage.getCompany());
                    values.put("city", userOtherMessage.getCity());
                    values.put("sex", userOtherMessage.getSex());
                    values.put("identitycard", userOtherMessage.getIdentitycard());
                    values.put("companyfront", userOtherMessage.getCompanyfront());
                    values.put("companyrear", userOtherMessage.getCompanyrear());
                    values.put("operatingpost", userOtherMessage.getOperatingpost());
                    values.put("bankid", userOtherMessage.getBankid());
                    Cursor cursor = messageDao.database.query(SqlLang.CREATE_TABLE, null, "id like ?"
                            , new String[]{"" + id}, null, null, null);
                    if (cursor != null) {
                        if (cursor.moveToNext()) {
                            messageDao.database.update(SqlLang.CREATE_TABLE, values, "id like ?", new String[]{String.valueOf(id)});
                        } else {
                            messageDao.database.insert(SqlLang.CREATE_TABLE, null, values);
                        }
                    }
                    messageDao.database.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
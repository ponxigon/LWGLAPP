package com.example.laowuguanli.ui.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.laowuguanli.biz.LogoutDialog;
import com.example.laowuguanli.biz.MessageDao;
import com.example.laowuguanli.biz.SqlLang;
import com.example.laowuguanli.broadcast.MessageReceiver;
import com.example.laowuguanli.databinding.FragmentBoosCenterBinding;
import com.example.laowuguanli.ui.activity.BoosPersonalRevamp;
import com.example.laowuguanli.ui.activity.ChangePassword;
import com.example.laowuguanli.ui.activity.MainActivity;

import java.util.Objects;


public class BoosCenterFragment extends Fragment {
    @SuppressLint("StaticFieldLeak")
    private static FragmentBoosCenterBinding binding;

    MessageDao messageDao;
    private final Handler handler = new Handler(Looper.myLooper()){
        @SuppressLint("Range")
        @Override
        public void handleMessage(@NonNull Message msg) {
            int id = MessageReceiver.getId();
            System.out.println("ID:"+id);
            @SuppressLint("Recycle") Cursor cursor = messageDao.database.query(SqlLang.CREATE_TABLE,null,
                    "id like ?", new String[]{"" + id}, null, null, null);
            cursor.moveToNext();
            String name = cursor.getString(cursor.getColumnIndex("name"));
            messageDao.database.close();
            binding.tvBoosName.setText(name);
        }
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        messageDao = new MessageDao(getActivity());
    }

    @Override
    public void onStart() {
        super.onStart();
        messageDao = new MessageDao(getActivity());
        initData();
    }

    public void initData() {
        Message message = new Message();
        handler.sendMessage(message);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBoosCenterBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.boosCenterFunction01.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), BoosPersonalRevamp.class);
            startActivity(intent);
        });
        binding.boosCenterFunction03.setOnClickListener(view12 -> {
            Intent intent = new Intent(getActivity(), ChangePassword.class);
            startActivity(intent);
        });
        binding.boosBtnLogOut.setOnClickListener(view13 -> {
            SharedPreferences preferences = Objects.requireNonNull(getActivity()).getSharedPreferences("user_info", MODE_PRIVATE);
            @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("isLogin", false);
            editor.apply();
            LogoutDialog logoutDialog = new LogoutDialog(getActivity());
            logoutDialog.logout();
        });
        binding.boosCenterFunction02.setOnClickListener(v -> Toast.makeText(getActivity(), "反馈无效", Toast.LENGTH_SHORT).show());
    }
}

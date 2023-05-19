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
import com.example.laowuguanli.databinding.FragmentManageCenterBinding;
import com.example.laowuguanli.ui.activity.ChangePassword;
import com.example.laowuguanli.ui.activity.MainActivity;
import com.example.laowuguanli.ui.activity.ManagePersonalRevamp;

import java.util.Objects;

public class ManageCenterFragment extends Fragment {
    private FragmentManageCenterBinding binding;
    MessageDao messageDao;
    private final Handler handler = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            int id = MessageReceiver.getId();

            Cursor cursor = messageDao.database.query(SqlLang.CREATE_TABLE, new String[]{"name"},
                    "id like ?", new String[]{"" + id}, null, null, null);
            cursor.moveToNext();
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
            binding.tvManageName.setText(name);
            messageDao.database.close();
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        initData();
        messageDao = new MessageDao(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentManageCenterBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    public void initData() {
        Message message = new Message();
        handler.sendMessage(message);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.manageCenterFunction01.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), ManagePersonalRevamp.class);
            startActivity(intent);
        });
        binding.manageCenterFunction03.setOnClickListener(view12 -> {
            Intent intent = new Intent(getActivity(), ChangePassword.class);
            startActivity(intent);
        });

        binding.manageBtnLogOut.setOnClickListener(view13 -> {
            SharedPreferences preferences = Objects.requireNonNull(getActivity()).getSharedPreferences("user_info", MODE_PRIVATE);
            @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("isLogin", false);
            editor.apply();
            LogoutDialog logoutDialog = new LogoutDialog(getActivity());
            logoutDialog.logout();
        });
        binding.manageCenterFunction02.setOnClickListener(v -> Toast.makeText(getActivity(), "反馈无效", Toast.LENGTH_SHORT).show());
    }
}
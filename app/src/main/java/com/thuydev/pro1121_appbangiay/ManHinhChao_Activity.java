package com.thuydev.pro1121_appbangiay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ManHinhChao_Activity extends AppCompatActivity {
    private SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_chao);
        preferences = getSharedPreferences("begin",MODE_PRIVATE);
        int i =  preferences.getInt("only",0);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (i==0){
                    Intent intent = new Intent(ManHinhChao_Activity.this,ManHinhKhoiDau_Activity.class);
                    startActivity(intent);
                    thaygiatri();
                }else {
                dangNhap();
                }
                finish();
            }
        },3000);
    }

    private void thaygiatri() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("only",1);
        editor.apply();
    }

    private void dangNhap() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Intent intent;
        if (user==null){
            intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            Toast.makeText(this, "Chưa có tài khoản", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Vào màn hình đăng nhập", Toast.LENGTH_SHORT).show();
        }
    }
}
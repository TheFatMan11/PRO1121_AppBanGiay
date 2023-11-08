package com.thuydev.pro1121_appbangiay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ManHinhKhoiDau_Activity extends AppCompatActivity {
Button dangNhap;
TextView dangKy;
private  Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_khoi_dau);
        dangNhap = findViewById(R.id.btn_dangNhap_begin);
        dangKy = findViewById(R.id.tv_dangky_begin);

        dangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chuyen(XacNhan_Activity.class);
            }
        });
        dangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ManHinhKhoiDau_Activity.this, "Chưa làm ok", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void chuyen(Class aClass) {
        intent = new Intent(ManHinhKhoiDau_Activity.this, aClass);
        startActivity(intent);
        finish();
    }
}
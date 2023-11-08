package com.thuydev.pro1121_appbangiay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class DangNhap_Activity extends AppCompatActivity {
   private View dangky;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        dangky = findViewById(R.id.btn_dangky);

        dangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               chuyen(DangKy_Activity.class);
            }
        });
    }

    private void chuyen(Class a) {
        Intent intent = new Intent(this,a);
        startActivity(intent);
    }
}
package com.thuydev.pro1121appbangiay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.thuydev.pro1121appbangiay.fragment.Frag_cuahang;

public class ManHinhKhachHang extends AppCompatActivity {
Frag_cuahang fragCuahang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_khach_hang);
        fragCuahang = new Frag_cuahang();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().add(R.id.fcv_KhachHang,fragCuahang).commit();
    }
}
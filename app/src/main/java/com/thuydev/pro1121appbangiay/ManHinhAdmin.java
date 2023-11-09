package com.thuydev.pro1121appbangiay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.thuydev.pro1121appbangiay.adapter.viewPagerAdapter;
import com.thuydev.pro1121appbangiay.fragment.QuanLyGiay;
import com.thuydev.pro1121appbangiay.fragment.QuanLyKhachHang;
import com.thuydev.pro1121appbangiay.fragment.QuanLyNhanVien;
import com.thuydev.pro1121appbangiay.fragment.frg_ThongKe;

public class ManHinhAdmin extends AppCompatActivity {
    Toolbar toolbar;
    FragmentContainerView viewPager;
    BottomNavigationView bottomNavigationView;

    QuanLyNhanVien quanLyNhanVien = new QuanLyNhanVien();
    QuanLyKhachHang quanLyKhachHang = new QuanLyKhachHang();
    QuanLyGiay quanLyGiay = new QuanLyGiay();
    frg_ThongKe thongKe = new frg_ThongKe();
    FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_admin);
        toolbar = findViewById(R.id.toolbar_admin);
        viewPager = findViewById(R.id.fcv_Admin);
        bottomNavigationView = findViewById(R.id.bnv_Admin);


    }

    public void relaceFrg(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fcv_Admin, fragment).commit();
    }

}
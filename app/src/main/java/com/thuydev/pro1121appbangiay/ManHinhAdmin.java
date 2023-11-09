package com.thuydev.pro1121appbangiay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

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
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Quản Lý Nhân Viên");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.menu_admin_qlkh){
                    Toast.makeText(ManHinhAdmin.this, "Đang ở quản lý khách hàng", Toast.LENGTH_SHORT).show();
                    getSupportActionBar().setTitle("Quản Lý Khách Hàng");
                }else if (item.getItemId() == R.id.menu_admin_qlnv){
                    Toast.makeText(ManHinhAdmin.this, "Đang ở quản lý nhân viên", Toast.LENGTH_SHORT).show();
                    getSupportActionBar().setTitle("Quản Lý Nhân Viên");
                }else if (item.getItemId() == R.id.menu_admin_qlsp){
                    Toast.makeText(ManHinhAdmin.this, "Đang ở quản lý sản phẩm", Toast.LENGTH_SHORT).show();
                    getSupportActionBar().setTitle("Quản Lý Sản Phẩm");
                }else if (item.getItemId() == R.id.menu_admin_thongke){
                    Toast.makeText(ManHinhAdmin.this, "Đang ở thống kê", Toast.LENGTH_SHORT).show();
                    getSupportActionBar().setTitle("Thống kê");
                } else if (item.getItemId()== R.id.menu_admin_resetpass) {
                    Toast.makeText(ManHinhAdmin.this, "Đang ở đổi mật khẩu", Toast.LENGTH_SHORT).show();
                    getSupportActionBar().setTitle("Đổi mật khẩu");
                }else {
                    Toast.makeText(ManHinhAdmin.this, "Lỗi", Toast.LENGTH_SHORT).show();
                }

                return true;
            }
        });


    }

    public void relaceFrg(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fcv_Admin, fragment).commit();
    }

}
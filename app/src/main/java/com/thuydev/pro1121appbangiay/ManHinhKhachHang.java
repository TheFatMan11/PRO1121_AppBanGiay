package com.thuydev.pro1121appbangiay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.thuydev.pro1121appbangiay.fragment.Frag_cuahang;
import com.thuydev.pro1121appbangiay.fragment.Fragment_gioHang;

public class ManHinhKhachHang extends AppCompatActivity {
Frag_cuahang fragCuahang;
    Toolbar toolbar;
    BottomNavigationView bottomNavigationView;
    FragmentManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_khach_hang);
        fragCuahang = new Frag_cuahang();
        bottomNavigationView = findViewById(R.id.bnv_khachhang);
        toolbar = findViewById(R.id.toolbar_khachhang);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Cửa hàng");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        manager = getSupportFragmentManager();
        manager.beginTransaction().add(R.id.fcv_KhachHang,fragCuahang).commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId()==R.id.menu_khachhang_danhsachsp){
                    getSupportActionBar().setTitle("Cửa hàng");
                    manager.beginTransaction().replace(R.id.fcv_KhachHang,fragCuahang).commit();
                }else if (item.getItemId() ==R.id.menu_khachhang_giohang){
                    getSupportActionBar().setTitle("Giỏ hàng");
                    manager.beginTransaction().replace(R.id.fcv_KhachHang,new Fragment_gioHang()).commit();
                }
                else if (item.getItemId() ==R.id.menu_khachhang_hotro){
                    getSupportActionBar().setTitle("Hỗ trợ");
                }
                else if (item.getItemId() ==R.id.menu_khachhang_hoadon){
                    getSupportActionBar().setTitle("Hóa đơn");
                }
                else if (item.getItemId() ==R.id.menu_khachhang_thongtincanhan){
                    getSupportActionBar().setTitle("Cửa hàng");
                }
                return true;
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_toolbar) {
            item.setIcon(R.drawable.bell);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
package com.thuydev.pro1121appbangiay;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseApp;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.thuydev.pro1121appbangiay.adapter.Adapter_sanpham;
import com.thuydev.pro1121appbangiay.fragment.QuanLyGiay;
import com.thuydev.pro1121appbangiay.fragment.QuanLyKhachHang;
import com.thuydev.pro1121appbangiay.fragment.QuanLyNhanVien;
import com.thuydev.pro1121appbangiay.fragment.frg_DoiMatKhau;
import com.thuydev.pro1121appbangiay.fragment.frg_ThongKe;

import java.util.UUID;

public class ManHinhAdmin extends AppCompatActivity {

    Toolbar toolbar;
    FragmentContainerView viewPager;
    BottomNavigationView bottomNavigationView;

    QuanLyNhanVien quanLyNhanVien = new QuanLyNhanVien();
    QuanLyKhachHang quanLyKhachHang = new QuanLyKhachHang();
    QuanLyGiay quanLyGiay = new QuanLyGiay();
    frg_ThongKe thongKe = new frg_ThongKe();
    frg_DoiMatKhau doiMatKhau = new frg_DoiMatKhau();
    FragmentManager manager;
    Uri uri;
    ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult o) {
                    if (o.getResultCode() == RESULT_OK) {
                        Intent intent = o.getData();
                        if (intent == null) {
                            return;
                        }
                        uri = intent.getData();
                        quanLyGiay.hienthiAnh(uri);

                    }

                }
            });

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
        manager = getSupportFragmentManager();
        manager.beginTransaction().add(R.id.fcv_Admin, quanLyNhanVien).commit();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.menu_admin_qlkh) {
                    relaceFrg(quanLyKhachHang);
                    getSupportActionBar().setTitle("Quản Lý Khách Hàng");
                } else if (item.getItemId() == R.id.menu_admin_qlnv) {
                    relaceFrg(quanLyNhanVien);
                    getSupportActionBar().setTitle("Quản Lý Nhân Viên");
                } else if (item.getItemId() == R.id.menu_admin_qlsp) {
                    relaceFrg(quanLyGiay);
                    getSupportActionBar().setTitle("Quản Lý Sản Phẩm");
                } else if (item.getItemId() == R.id.menu_admin_thongke) {
                    relaceFrg(thongKe);
                    getSupportActionBar().setTitle("Thống kê");
                } else if (item.getItemId() == R.id.menu_admin_resetpass) {
                    relaceFrg(doiMatKhau);
                    getSupportActionBar().setTitle("Đổi mật khẩu");
                } else {
                    Toast.makeText(ManHinhAdmin.this, "Lỗi", Toast.LENGTH_SHORT).show();
                }

                return true;
            }
        });

        OnBackPressedDispatcher onBackPressedDispatcher = getOnBackPressedDispatcher();
        onBackPressedDispatcher.addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                AlertDialog.Builder builder = new AlertDialog.Builder(ManHinhAdmin.this);
                builder.setTitle("Thông báo");
                builder.setIcon(R.drawable.user1);
                builder.setMessage("Bạn có muốn đăng xuất");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(ManHinhAdmin.this, DangNhap_Activity.class);
                        startActivity(intent);
                        finish();
                        Toast.makeText(ManHinhAdmin.this, "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.create().show();

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

    public void relaceFrg(Fragment fragment) {
        manager.beginTransaction().replace(R.id.fcv_Admin, fragment).commit();
    }

    public void layAnh() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        launcher.launch(intent);
    }

    public void yeucauquyen(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            layAnh();
            return;
        }
        if (context.checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            // xử lý sau
            layAnh();
        } else {
            String[] quyen = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};
            requestPermissions(quyen, CODE_QUYEN);
        }
    }

    private static final int CODE_QUYEN = 1;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CODE_QUYEN) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                layAnh();
            } else {
                Toast.makeText(this, "Bạn cần cấp quyền để sử dụng tính năng này", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
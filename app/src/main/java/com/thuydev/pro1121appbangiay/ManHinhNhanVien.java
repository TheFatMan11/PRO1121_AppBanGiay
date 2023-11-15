package com.thuydev.pro1121appbangiay;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.thuydev.pro1121appbangiay.adapter.Adapter_quanlyhoadon;
import com.thuydev.pro1121appbangiay.fragment.Frg_quanLyHoaDon;
import com.thuydev.pro1121appbangiay.fragment.QuanLyGiay;
import com.thuydev.pro1121appbangiay.fragment.QuanLyKhachHang;
import com.thuydev.pro1121appbangiay.fragment.QuanLyNhanVien;
import com.thuydev.pro1121appbangiay.fragment.frg_DoiMatKhau;
import com.thuydev.pro1121appbangiay.fragment.frg_ThongKe;
import com.thuydev.pro1121appbangiay.model.DonHang;
import com.thuydev.pro1121appbangiay.model.GioHang;
import com.thuydev.pro1121appbangiay.model.SanPham;
import com.thuydev.pro1121appbangiay.model.User;

import java.util.ArrayList;
import java.util.List;

public class ManHinhNhanVien extends AppCompatActivity {
    Toolbar toolbar;
    FragmentContainerView viewPager;
    BottomNavigationView bottomNavigationView;

    QuanLyGiay quanLyGiay = new QuanLyGiay(1);

    Frg_quanLyHoaDon frgQuanLyHoaDon = new Frg_quanLyHoaDon();
    FragmentManager manager;
    Uri uri;
    RecyclerView recyclerView;
    Adapter_quanlyhoadon adapterQuanlyhoadon;
    List<DonHang> list_dh ;
    List<User> list_User ;
    List<GioHang> list_GioHang ;
    List<SanPham> list_sp ;
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
        setContentView(R.layout.activity_man_hinh_nhan_vien);
        toolbar = findViewById(R.id.toolbar_nhanvien);
        viewPager = findViewById(R.id.fcv_Nhanvien);
        bottomNavigationView = findViewById(R.id.bnv_NhanVien);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Quản Lý Sản Phẩm");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        manager = getSupportFragmentManager();
        manager.beginTransaction().add(R.id.fcv_Nhanvien, quanLyGiay).commit();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.menu_nhanvien_qlsp) {
                    relaceFrg(quanLyGiay);
                    getSupportActionBar().setTitle("Quản Lý Sản Phẩm");
                } else if (item.getItemId() == R.id.menu_nhanvien_thongke) {
                    relaceFrg(new frg_ThongKe());
                    getSupportActionBar().setTitle("Thống kê");
                } else if (item.getItemId() == R.id.menu_nhanvien_qlhd) {
                    list_dh = new ArrayList<>();
                    list_User = new ArrayList<>();
                    list_GioHang = new ArrayList<>();
                    list_sp = new ArrayList<>();
                    relaceFrg(frgQuanLyHoaDon);
                    getSupportActionBar().setTitle("Quản Lý Hóa Đơn");
                    nghe();
                    setQuanLy();
                } else if (item.getItemId() == R.id.menu_nhanvien_resetpass) {
                    ManHinhAdmin.doipass(ManHinhNhanVien.this);
                    return false;
                } else {
                    Toast.makeText(ManHinhNhanVien.this, "Lỗi", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
        OnBackPressedDispatcher onBackPressedDispatcher = getOnBackPressedDispatcher();
        onBackPressedDispatcher.addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                AlertDialog.Builder builder = new AlertDialog.Builder(ManHinhNhanVien.this);
                builder.setTitle("Thông báo");
                builder.setIcon(R.drawable.user1);
                builder.setMessage("Bạn có muốn đăng xuất");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(ManHinhNhanVien.this, DangNhap_Activity.class);
                        startActivity(intent);
                        finishAffinity();
                        Toast.makeText(ManHinhNhanVien.this, "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
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
        manager.beginTransaction().replace(R.id.fcv_Nhanvien, fragment).commit();
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            String[] quyen = new String[]{Manifest.permission.READ_MEDIA_IMAGES};
            requestPermissions(quyen, CODE_QUYEN);
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
    FirebaseFirestore db;
    public void ngheHoaDon() {
        db = FirebaseFirestore.getInstance();
        db.collection("donHang").whereEqualTo("trangThai", 0).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Toast.makeText(ManHinhNhanVien.this, "Lỗi không có dữ liệu", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (value != null) {
                    for (DocumentChange dc : value.getDocumentChanges()) {
                        switch (dc.getType()) {
                            case ADDED:
                                dc.getDocument().toObject(DonHang.class);
                                list_dh.add(dc.getDocument().toObject(DonHang.class));
                                adapterQuanlyhoadon.notifyDataSetChanged();
                                break;
                            case MODIFIED:
                                DonHang dtoq = dc.getDocument().toObject(DonHang.class);
                                if (dc.getOldIndex() == dc.getNewIndex()) {
                                    list_dh.set(dc.getOldIndex(), dtoq);
                                } else {
                                    list_dh.remove(dc.getOldIndex());
                                    list_dh.add(dtoq);
                                }
                                adapterQuanlyhoadon.notifyDataSetChanged();
                                break;
                            case REMOVED:
                                dc.getDocument().toObject(DonHang.class);
                                list_dh.remove(dc.getOldIndex());
                                adapterQuanlyhoadon.notifyDataSetChanged();
                                break;
                        }
                    }
                }
            }
        });
    }
public  void setQuanLy(){
        frgQuanLyHoaDon.setList_sp(list_sp);
        frgQuanLyHoaDon.setList_dh(list_dh);
        frgQuanLyHoaDon.setList_Users(list_User);
        frgQuanLyHoaDon.setList_gioHang(list_GioHang);

}

    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        adapterQuanlyhoadon = new Adapter_quanlyhoadon(list_sp,list_User,list_dh,this);
        recyclerView.setAdapter(adapterQuanlyhoadon);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
    }

    public void ngheKH() {

        db = FirebaseFirestore.getInstance();
        db.collection("user").whereEqualTo("chucVu", 3).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    return;
                }
                if (value != null) {
                    for (DocumentChange dc : value.getDocumentChanges()) {
                        Log.e("TAG", "onEvent: " + dc.getType());
                        switch (dc.getType()) {
                            case ADDED:
                                dc.getDocument().toObject(User.class);
                                list_User.add(dc.getDocument().toObject(User.class));
                                break;
                            case MODIFIED:
                                User user1 = dc.getDocument().toObject(User.class);
                                if (dc.getOldIndex() == dc.getNewIndex()) {
                                    list_User.set(dc.getOldIndex(), user1);
                                    adapterQuanlyhoadon.notifyDataSetChanged();
                                } else {
                                    list_User.remove(dc.getOldIndex());
                                    list_User.add(user1);
                                    adapterQuanlyhoadon.notifyDataSetChanged();
                                }
                                break;
                            case REMOVED:
                                dc.getDocument().toObject(User.class);
                                list_User.remove(dc.getOldIndex());
                                adapterQuanlyhoadon.notifyDataSetChanged();
                        }
                    }
                }
            }
        });
    }

    public void ngheSP() {
        db.collection("sanPham").orderBy("time").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Toast.makeText(ManHinhNhanVien.this, "Lỗi không có dữ liệu", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (value != null) {
                    for (DocumentChange dc : value.getDocumentChanges()) {
                        switch (dc.getType()) {
                            case ADDED:
                                dc.getDocument().toObject(SanPham.class);
                                list_sp.add(dc.getDocument().toObject(SanPham.class));
                                adapterQuanlyhoadon.notifyDataSetChanged();
                                break;
                            case MODIFIED:
                                SanPham dtoq = dc.getDocument().toObject(SanPham.class);
                                if (dc.getOldIndex() == dc.getNewIndex()) {
                                    list_sp.set(dc.getOldIndex(), dtoq);
                                } else {
                                    list_sp.remove(dc.getOldIndex());
                                    list_sp.add(dtoq);
                                }
                                adapterQuanlyhoadon.notifyDataSetChanged();
                                break;
                            case REMOVED:
                                dc.getDocument().toObject(SanPham.class);
                                list_sp.remove(dc.getOldIndex());
                                adapterQuanlyhoadon.notifyDataSetChanged();
                                break;
                        }
                    }
                }
            }
        });
    }
public void nghe(){
        db = FirebaseFirestore.getInstance();
        ngheGio();
        ngheKH();
        ngheHoaDon();
        ngheSP();
}
    public void ngheGio() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        db.collection("gioHang").whereEqualTo("maKhachHang", user.getUid()).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Toast.makeText(ManHinhNhanVien.this, "Lỗi không có dữ liệu", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (value != null) {
                    for (DocumentChange dc : value.getDocumentChanges()) {
                        switch (dc.getType()) {
                            case ADDED:
                                list_GioHang.add(dc.getDocument().toObject(GioHang.class));
                                adapterQuanlyhoadon.notifyDataSetChanged();
                                break;
                            case MODIFIED:
                                GioHang dtoq = dc.getDocument().toObject(GioHang.class);
                                if (dc.getOldIndex() == dc.getNewIndex()) {
                                    list_GioHang.set(dc.getOldIndex(), dtoq);
                                } else {
                                    list_GioHang.remove(dc.getOldIndex());
                                    list_GioHang.add(dtoq);
                                }
                                adapterQuanlyhoadon.notifyDataSetChanged();
                                break;
                            case REMOVED:
                                dc.getDocument().toObject(GioHang.class);
                                list_GioHang.remove(dc.getOldIndex());
                                adapterQuanlyhoadon.notifyDataSetChanged();
                                break;
                        }
                    }
                }
            }
        });
    }

}
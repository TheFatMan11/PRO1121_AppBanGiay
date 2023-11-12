package com.thuydev.pro1121appbangiay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.thuydev.pro1121appbangiay.adapter.Adapter_kichco;
import com.thuydev.pro1121appbangiay.model.SanPham;

import java.util.List;

public class SeeSanPham extends AppCompatActivity {
    RecyclerView rcv_list;
    SanPham sanPham = new SanPham();
    ;
    List<String> list_co;
    TextView ten, gia,nam;
    ImageView anh;
    Button them;
    Adapter_kichco adapterKichco;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sanpham_show);
        Intent intent = getIntent();
        String s = intent.getStringExtra("sanpham");

        nghe(s);
        rcv_list = findViewById(R.id.rcv_listco);
        ten = findViewById(R.id.tv_tensp_show);
        gia = findViewById(R.id.tv_giasp_show);
        them = findViewById(R.id.btn_themgio);
        nam = findViewById(R.id.tv_mamsp_show);
        anh = findViewById(R.id.imv_anh_sp_lon);
    }

    FirebaseFirestore db;

    private void nghe(String s) {
        db = FirebaseFirestore.getInstance();
        db.collection("sanPham").document(s).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isComplete()) {
                    Log.e("TAG", "onComplete: " + task.getResult().toObject(SanPham.class).getGia());
                    sanPham.setAnh(task.getResult().toObject(SanPham.class).getAnh());
                    sanPham.setMaSp(task.getResult().toObject(SanPham.class).getMaSp());
                    sanPham.setGia(task.getResult().toObject(SanPham.class).getGia());
                    sanPham.setTenSP(task.getResult().toObject(SanPham.class).getTenSP());
                    sanPham.setKichCo(task.getResult().toObject(SanPham.class).getKichCo());
                    sanPham.setNamSX(task.getResult().toObject(SanPham.class).getNamSX());
                    Log.e("TAG", "onComplete: " + sanPham.getKichCo().get(0));
                    ten.setText(sanPham.getTenSP());
                    nam.setText("Năm sản xuất: "+sanPham.getNamSX());
                    gia.setText("Giá: " + sanPham.getGia());
                    Glide.with(SeeSanPham.this).load(sanPham.getAnh()).error(R.drawable.baseline_crop_original_24).into(anh);
                    list_co = sanPham.getKichCo();
                    adapterKichco = new Adapter_kichco(list_co, SeeSanPham.this);
                    rcv_list.setAdapter(adapterKichco);
                    LinearLayoutManager manager = new LinearLayoutManager(SeeSanPham.this, LinearLayoutManager.HORIZONTAL, false);
                    rcv_list.setLayoutManager(manager);
                } else {
                    Toast.makeText(SeeSanPham.this, "Sản phẩm đã bị xóa", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
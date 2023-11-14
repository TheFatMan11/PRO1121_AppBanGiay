package com.thuydev.pro1121appbangiay.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.thuydev.pro1121appbangiay.DangNhap_Activity;
import com.thuydev.pro1121appbangiay.ManHinhAdmin;
import com.thuydev.pro1121appbangiay.R;
import com.thuydev.pro1121appbangiay.ThongTinTaiKhoan;
import com.thuydev.pro1121appbangiay.adapter.Adapter_diachi;
import com.thuydev.pro1121appbangiay.model.User;

import java.util.ArrayList;
import java.util.List;

public class Fragment_thongtin extends Fragment {
    LinearLayout thongtin, diachi, lichsu, doimatkhau, dangxuat;
    FirebaseFirestore db;
    User us;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_thongtincanhan, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        anhxa(view);
        ThongTinTaiKhoan tk = (ThongTinTaiKhoan) getActivity();
        dangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dangxuat();
            }
        });
        thongtin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tk.suaProFile();
            }
        });
        diachi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tk.addDiaChi();
            }
        });
        doimatkhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ManHinhAdmin.doipass(getActivity());
            }
        });
    }
    private void dangxuat() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Thông báo");
        builder.setIcon(R.drawable.user1);
        builder.setMessage("Bạn có muốn đăng xuất");
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getContext(), DangNhap_Activity.class);
                getActivity().finishAffinity();
                if (!getActivity().isFinishing()) {
                    return;
                }
                startActivity(intent);
            }
        });
        builder.create().show();

    }
    private void anhxa(View view) {
        thongtin = view.findViewById(R.id.ll_thongtincanhan);
        diachi = view.findViewById(R.id.ll_diaChi);
        lichsu = view.findViewById(R.id.ll_lichsumua);
        doimatkhau = view.findViewById(R.id.ll_doimatkhau);
        dangxuat = view.findViewById(R.id.ll_dangxuat);
        db = FirebaseFirestore.getInstance();
    }


    public void setUs(User us) {
        this.us = us;
    }
}

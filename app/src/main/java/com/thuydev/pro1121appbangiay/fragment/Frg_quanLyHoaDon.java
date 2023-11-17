package com.thuydev.pro1121appbangiay.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.thuydev.pro1121appbangiay.ManHinhNhanVien;
import com.thuydev.pro1121appbangiay.R;
import com.thuydev.pro1121appbangiay.adapter.Adapter_quanlyhoadon;
import com.thuydev.pro1121appbangiay.loatDonHang;
import com.thuydev.pro1121appbangiay.model.DonHang;
import com.thuydev.pro1121appbangiay.model.GioHang;
import com.thuydev.pro1121appbangiay.model.SanPham;
import com.thuydev.pro1121appbangiay.model.User;

import java.util.ArrayList;
import java.util.List;


public class Frg_quanLyHoaDon extends Fragment {

    RecyclerView recyclerView;
    Adapter_quanlyhoadon adapterQuanlyhoadon;
    List<GioHang> list_gioHang;
    List<SanPham> list_sp;
    List<DonHang> list_dh;

    List<User> list_Users;
    FirebaseFirestore db;

    public Frg_quanLyHoaDon() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_frg_quan_ly_hoa_don, container, false);
        recyclerView = view.findViewById(R.id.rcv_quanLyHoaDon);
        list_Users = new ArrayList<>();
        list_sp = new ArrayList<>();
        list_gioHang = new ArrayList<>();
        list_dh = new ArrayList<>();
        loatData();
        return view;
    }

    public void loatData() {
        ManHinhNhanVien nhanVien = (ManHinhNhanVien) getContext();
        nhanVien.setRecyclerView(recyclerView);
    }



    public void setList_gioHang(List<GioHang> list_gioHang) {
        this.list_gioHang = list_gioHang;
    }

    public void setList_sp(List<SanPham> list_sp) {
        this.list_sp = list_sp;
    }

    public void setList_dh(List<DonHang> list_dh) {
        this.list_dh = list_dh;
    }

    public void setList_Users(List<User> list_Users) {
        this.list_Users = list_Users;
    }

    public void setDb(FirebaseFirestore db) {
        this.db = db;
    }
}



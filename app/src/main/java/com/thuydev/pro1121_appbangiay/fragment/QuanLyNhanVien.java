package com.thuydev.pro1121_appbangiay.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thuydev.pro1121_appbangiay.R;


public class QuanLyNhanVien extends Fragment {


    public QuanLyNhanVien() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_quan_ly_nhan_vien, container, false);
    }
}
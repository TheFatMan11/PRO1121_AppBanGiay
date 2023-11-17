package com.thuydev.pro1121appbangiay.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.thuydev.pro1121appbangiay.ManHinhNhanVien;
import com.thuydev.pro1121appbangiay.R;
import com.thuydev.pro1121appbangiay.adapter.Adapter_Top10;
import com.thuydev.pro1121appbangiay.model.DonHang;
import com.thuydev.pro1121appbangiay.model.Hang;
import com.thuydev.pro1121appbangiay.model.SanPham;

import java.util.ArrayList;
import java.util.List;


public class ThongKe_Top10SP extends Fragment {

    RecyclerView recyclerView;
    List<SanPham> list_SanPham;
    List<DonHang> list_DonHang;
    List<Hang> list_Hang;
    Adapter_Top10 adapterTop10;
    FirebaseFirestore db;

    public ThongKe_Top10SP() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thong_ke__top10_s_p, container, false);
        recyclerView = view.findViewById(R.id.rcv_Top10sp);
        list_SanPham = new ArrayList<>();
        list_DonHang = new ArrayList<>();
        list_Hang = new ArrayList<>();
        adapterTop10 = new Adapter_Top10(list_DonHang,list_Hang, list_SanPham, getContext());
        recyclerView.setAdapter(adapterTop10);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        db = FirebaseFirestore.getInstance();
        ngheSP();
        return view;
    }

    private void ngheSP() {
        db.collection("sanPham").orderBy("time").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Toast.makeText(getContext(), "Lỗi không có dữ liệu", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (value != null) {
                    for (DocumentChange dc : value.getDocumentChanges()) {
                        switch (dc.getType()) {
                            case ADDED:
                                dc.getDocument().toObject(SanPham.class);
                                list_SanPham.add(dc.getDocument().toObject(SanPham.class));
                                adapterTop10.notifyDataSetChanged();
                                break;
                            case MODIFIED:
                                SanPham dtoq = dc.getDocument().toObject(SanPham.class);
                                if (dc.getOldIndex() == dc.getNewIndex()) {
                                    list_SanPham.set(dc.getOldIndex(), dtoq);
                                } else {
                                    list_SanPham.remove(dc.getOldIndex());
                                    list_SanPham.add(dtoq);
                                }
                                adapterTop10.notifyDataSetChanged();
                                break;
                            case REMOVED:
                                dc.getDocument().toObject(SanPham.class);
                                list_SanPham.remove(dc.getOldIndex());
                                adapterTop10.notifyDataSetChanged();
                                break;
                        }
                    }
                }
            }
        });
    }


}
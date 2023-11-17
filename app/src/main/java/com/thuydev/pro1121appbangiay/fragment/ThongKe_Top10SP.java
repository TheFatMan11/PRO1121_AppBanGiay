package com.thuydev.pro1121appbangiay.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.thuydev.pro1121appbangiay.ManHinhNhanVien;
import com.thuydev.pro1121appbangiay.R;
import com.thuydev.pro1121appbangiay.adapter.Adapter_Top10;
import com.thuydev.pro1121appbangiay.model.DonHang;
import com.thuydev.pro1121appbangiay.model.Hang;
import com.thuydev.pro1121appbangiay.model.SanPham;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ThongKe_Top10SP extends Fragment {

    RecyclerView recyclerView;
    List<SanPham> list_SanPham;
    List<DonHang> list_DonHang;
    List<Hang> list_Hang;
    Adapter_Top10 adapterTop10;
    FirebaseFirestore db;
    List<HashMap<String, Object>> list_top10;

    public ThongKe_Top10SP() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thong_ke__top10_s_p, container, false);
        recyclerView = view.findViewById(R.id.rcv_Top10sp);
        list_SanPham = new ArrayList<>();
        list_top10 = new ArrayList<>();
        getTop10();
        getSP();
        adapterTop10 = new Adapter_Top10(getContext(),list_SanPham,list_top10);
        recyclerView.setAdapter(adapterTop10);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        return view;
    }



    public void getSP() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("sanPham").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isComplete()) {
                    for (QueryDocumentSnapshot snapshot : task.getResult()) {
                        list_SanPham.add(snapshot.toObject(SanPham.class));
                    }
                    adapterTop10.notifyDataSetChanged();
                }
            }
        });
    }

    public void getTop10() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("top10").orderBy("soLuong", Query.Direction.DESCENDING).limit(10).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isComplete()) {
                    for (QueryDocumentSnapshot snapshot : task.getResult()) {
                        HashMap<String, Object> top10 = new HashMap<>();
                        top10.put("soLuong", snapshot.getLong("soLuong"));
                        top10.put("maSP", snapshot.get("maSP"));
                        list_top10.add(top10);
                        Log.e("TaG",""+top10.get("soLuong"));

                    }
                    adapterTop10.notifyDataSetChanged();
                }
            }
        });
    }

}
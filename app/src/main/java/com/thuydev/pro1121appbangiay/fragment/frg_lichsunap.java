package com.thuydev.pro1121appbangiay.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.thuydev.pro1121appbangiay.R;
import com.thuydev.pro1121appbangiay.adapter.Adapter_dsYeuCauNap;
import com.thuydev.pro1121appbangiay.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class frg_lichsunap extends Fragment {

    RecyclerView recyclerView;
    Adapter_dsYeuCauNap adapter_dsYeuCauNap;
    List<HashMap<String, Object>> list;
    List<User> list_use;
    FirebaseFirestore db;

    public frg_lichsunap() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_frg_lichsunap, container, false);
        recyclerView = view.findViewById(R.id.rcv_nap);
        list = getListYC();
        list_use = new ArrayList<>();
        getSoDU();
        adapter_dsYeuCauNap = new Adapter_dsYeuCauNap(getContext(), list, list_use);
        recyclerView.setAdapter(adapter_dsYeuCauNap);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

    private List<HashMap<String, Object>> getListYC() {
        List<HashMap<String, Object>> list = new ArrayList<>();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();
        db.collection("naptien").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (!task.isComplete()) {
                    return;
                }
                for (QueryDocumentSnapshot dc : task.getResult()) {
                    list.add((HashMap<String, Object>) dc.getData());
                    adapter_dsYeuCauNap.notifyDataSetChanged();
                }
            }
        });
        return list;
    }

    public void getSoDU() {
        db.collection("user").whereEqualTo("chucVu", 3).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (!task.isComplete()) {
                    return;
                }
                for (QueryDocumentSnapshot dc : task.getResult()) {
                    list_use.add(dc.toObject(User.class));
                    adapter_dsYeuCauNap.notifyDataSetChanged();
                }
            }
        });
    }
}
package com.thuydev.pro1121appbangiay.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.thuydev.pro1121appbangiay.R;
import com.thuydev.pro1121appbangiay.adapter.Adapter_choduyet;
import com.thuydev.pro1121appbangiay.model.DonHang;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

public class Fragment_khoanchi extends Fragment {
RecyclerView rcv_list;
TextView tongGia;
Adapter_choduyet adapterChoduyet;
List<DonHang> list;
FirebaseFirestore db;
LocalDate dateStart = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
LocalDate dateEnd= LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
String ngayStart = formatter.format(dateStart);
String ngayEnd=formatter.format(dateEnd);
FirebaseUser user ;
Long tong=0l;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_khoanchi,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        anhXa(view);
    }

    private void anhXa(View view) {
        rcv_list = view.findViewById(R.id.rcv_list_khoanchi);
        tongGia = view.findViewById(R.id.tv_tonggia_khoanchi);
        list = new ArrayList<>();
        user = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();
        getData();
        adapterChoduyet = new Adapter_choduyet(list,getContext(),2);
        rcv_list.setAdapter(adapterChoduyet);
        rcv_list.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
    }

    private void getData() {
        db.collection("donHangDaDuyet")
                .whereGreaterThanOrEqualTo("ngayMua", ngayStart)
                .whereLessThanOrEqualTo("ngayMua", ngayEnd).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (!task.isComplete()){
                            return;
                        }
                        list.clear();
                        for (QueryDocumentSnapshot dc : task.getResult()){
                            if (user.getUid().equals(dc.toObject(DonHang.class).getMaKhachHang())){
                                list.add(dc.toObject(DonHang.class));
                                tong+=dc.toObject(DonHang.class).getGiaDon();
                                tongGia.setText("Giá: "+tong+" VND");
                                adapterChoduyet.notifyDataSetChanged();
                            }
                        }
                    }
                });
    }
}

package com.thuydev.pro1121appbangiay.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.thuydev.pro1121appbangiay.R;
import com.thuydev.pro1121appbangiay.model.DonHang;
import com.thuydev.pro1121appbangiay.model.Hang;
import com.thuydev.pro1121appbangiay.model.SanPham;

import java.util.List;

public class Adapter_Top10 extends RecyclerView.Adapter<Adapter_Top10.viewHolder> {
    List<DonHang> list_donHang;
    Context context;
    List<Hang> list_hang;
    List<SanPham> list_sanPham;
    FirebaseFirestore db;

    public Adapter_Top10(List<DonHang> listDonHang, List<Hang> list_hang, List<SanPham> list_sanPham, Context context) {
        this.list_donHang = listDonHang;
        this.list_hang = list_hang;
        this.list_sanPham = list_sanPham;
        this.context = context;
        db=FirebaseFirestore.getInstance();

    }


    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_top10sp, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

    }

    private String[] getData(List<SanPham> list_sanPham, DonHang hang) {
        if (list_sanPham.size() <= 0 && list_hang.size() <= 0 && list_donHang.size()<=0) {
            return new String[]{};
        }
        String[] a = new String[]{"", "", ""};
        for (SanPham sp : list_sanPham){
            if(hang.getMaDon().equals(sp.getMaSp())){
                a[0] = sp.getTenSP();
                a[1] = sp.getTenHang();
            }
        }
        return a;
    }

    @Override
    public int getItemCount() {
        return list_sanPham.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView anh;
        TextView tv_tenSp, tv_thuonghieu, tv_soLuong;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            anh = itemView.findViewById(R.id.imgv_anhtop10);
            tv_tenSp = itemView.findViewById(R.id.tv_TENSP);
            tv_thuonghieu = itemView.findViewById(R.id.tv_THUONGHIEU);
            tv_soLuong = itemView.findViewById(R.id.tv_SOLUONG);
        }
    }
}

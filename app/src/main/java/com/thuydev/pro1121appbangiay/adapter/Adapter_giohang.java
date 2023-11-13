package com.thuydev.pro1121appbangiay.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.thuydev.pro1121appbangiay.R;
import com.thuydev.pro1121appbangiay.model.GioHang;
import com.thuydev.pro1121appbangiay.model.Hang;
import com.thuydev.pro1121appbangiay.model.SanPham;

import java.util.List;

public class Adapter_giohang extends RecyclerView.Adapter<Adapter_giohang.ViewHolder> {
    List<GioHang> list_gio;
    List<SanPham> list_sanPham;
    List<Hang> list_hang;
    Context context;

    public Adapter_giohang(List<GioHang> list_gio, List<SanPham> list_sanPham, List<Hang> list_hang, Context context) {
        this.list_gio = list_gio;
        this.list_sanPham = list_sanPham;
        this.list_hang = list_hang;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(((Activity) context).getLayoutInflater().
                inflate(R.layout.item_giohang, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String link = laylink(list_gio.get(position).getMaSanPham());
        Log.e("TAG", "onBindViewHolder: " + link);
        if (link.isEmpty()) {
            return;
        }
        Glide.with(context).load(link).
                error(R.drawable.baseline_crop_original_24).into(holder.anh);
        SanPham sp = getSanPham(list_gio.get(position).getMaSanPham());
        if (sp == null) {
            return;
        }
        holder.tenSP.setText(sp.getTenSP());
        holder.giaSP.setText("Giá: " + sp.getGia() + "");
        String tenHang = getTenLoai(sp.getMaHang());
        if (tenHang == null) {
            return;
        }
        holder.loaiSP.setText("Loại: " + tenHang + "");
        holder.soLuong.setText("Số lượng: " + list_gio.get(position).getSoLuong() + "");
        holder.kichCo.setText("Kích cỡ: " + list_gio.get(position).getKichCo() + "");


//        holder.mua.setText("Giá: "+sp.getGia()+"");
//        holder.xoa.setText("Giá: "+sp.getGia()+"");

    }

    private String getTenLoai(String maHang) {
        for (Hang s : list_hang) {
            if (maHang.equals(s.getMaHang())) {
                return s.getTenHang();
            }
        }
        return null;
    }

    private SanPham getSanPham(String maSP) {
        for (SanPham s : list_sanPham) {
            if (maSP.equals(s.getMaSp())) {
                return s;
            }
        }
        return null;
    }

    private String laylink(String maSP) {
        String link = "";
        for (SanPham s : list_sanPham) {
            if (maSP.equals(s.getMaSp())) {
                link = s.getAnh();
                return link;
            }
        }
        return link;
    }

    @Override
    public int getItemCount() {
        return list_gio.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView anh;
        TextView tenSP, loaiSP, kichCo, soLuong, giaSP, mua, xoa;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            anh = itemView.findViewById(R.id.imv_anh_sp_gioHang);
            tenSP = itemView.findViewById(R.id.tv_tensp_gioHang);
            giaSP = itemView.findViewById(R.id.tv_giasp_giohang);
            loaiSP = itemView.findViewById(R.id.tv_thuonghieu_gioHang);
            kichCo = itemView.findViewById(R.id.tv_kichcosp_giohang);
            soLuong = itemView.findViewById(R.id.tv_soluongsp_giohang);
            mua = itemView.findViewById(R.id.tv_mua_giohang);
            xoa = itemView.findViewById(R.id.tv_xoa_giohang);
        }
    }
}

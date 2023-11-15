package com.thuydev.pro1121appbangiay.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.thuydev.pro1121appbangiay.fragment.Frg_quanLyHoaDon;
import com.thuydev.pro1121appbangiay.model.DonHang;
import com.thuydev.pro1121appbangiay.model.User;
import com.thuydev.pro1121appbangiay.R;
import com.thuydev.pro1121appbangiay.model.GioHang;
import com.thuydev.pro1121appbangiay.model.SanPham;

import java.util.List;

public class Adapter_quanlyhoadon extends RecyclerView.Adapter<Adapter_quanlyhoadon.viewHolder> {
    Context context;
    List<SanPham> list_sanPham;
    List<User> list_Users;
    List<DonHang> list_doHang;

    FirebaseFirestore db;



    public Adapter_quanlyhoadon(List<SanPham> list_sanPham, List<User> list_Users, List<DonHang> list_doHang, Context context) {

        this.list_sanPham = list_sanPham;
        this.list_Users = list_Users;
        this.list_doHang = list_doHang;
        this.context = context;
        db = FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_ql_don_hang, parent, false);
        return new viewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, @SuppressLint("RecyclerView") int position) {
        if (list_Users.size() <= 0 && list_doHang.size() <= 0 && list_sanPham.size() <= 0) {
            return;
        }
        String[] data = getdata(list_Users,list_doHang.get(position));
        if (data.length <= 0) {
            return;
        }
        DonHang donHang = list_doHang.get(position);
        if (donHang==null){
            return;
        }
        Long gia = Long.parseLong(data[3]);
        String maKH = list_doHang.get(position).getMaKhachHang();
        holder.tv_tenKH.setText("Họ tên:" + data[0]);
        holder.tv_diaChi.setText("Địa chỉ: " + data[1]);
        holder.tv_sdt.setText("Sđt: " + data[2]);
        holder.tv_gia.setText("Giá :" + data[3]);
        holder.tv_soluong.setText("Số lượng sản phẩm mua: " + data[4]);
        holder.btn_Huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trangThai(3, donHang);
            }
        });
        holder.btn_xacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("user").document(maKH).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot snapshot = task.getResult();
                            if (snapshot.exists()) {
                                Long soDu = task.getResult().getLong("soDu");
                                if (soDu==null) {
                                    Toast.makeText(context, "Loi long", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                if (soDu<gia){
                                    Toast.makeText(context, "Số dư khách hàng không đủ", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                Long newSoDu = soDu-gia;
                                db.collection("user").document(maKH).update("soDu", newSoDu).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isComplete()){
                                            Toast.makeText(context, "Đã thanh toán", Toast.LENGTH_SHORT).show();
                                            trangThai(1, donHang);
                                            notifyDataSetChanged();
                                        }else {
                                            Toast.makeText(context, "Lỗi", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            } else {
                                Toast.makeText(context, "Nguoi dung k ton tai", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(context, "Loi truy van", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    private void trangThai(int i, DonHang donHang) {
        if (donHang==null){
            return;
        }
        donHang.setTrangThai(i);
        db.collection("donHang").document(donHang.getMaDon()).set(donHang).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isComplete()) {
                    Toast.makeText(context, "Thành công", Toast.LENGTH_SHORT).show();
                    notifyDataSetChanged();
                } else {
                    Toast.makeText(context, "Lỗi cụ rồi bảo dev fix đi", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private String[] getdata(List<User> list_Users,DonHang donHang) {
        if (list_Users.size() <= 0 && list_doHang.size() <= 0 && list_sanPham.size() <= 0) {
            return new String[]{};
        }
        String[] a = new String[]{"", "", "", "", ""};
        for (User u : list_Users) {
            if (donHang.getMaKhachHang()
                    .equals(u.getMaUser())) {
                a[0] = u.getHoTen();
                a[1] = u.getChonDiaCHi();
                a[2] = u.getSDT();
            }
        }
                a[3] = donHang.getGiaDon() + "";
                a[4] = String.valueOf((donHang.getListSP().size() + 1));
                return a;
    }


    @Override
    public int getItemCount() {
        return list_doHang.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView tv_tenKH, tv_tenSP, tv_gia, tv_diaChi, tv_sdt, tv_soluong;
        ImageButton btn_Huy, btn_xacNhan;
        ImageView anh;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            tv_tenKH = itemView.findViewById(R.id.tv_tenKhach);
            tv_diaChi = itemView.findViewById(R.id.tv_diaChi);
            tv_sdt = itemView.findViewById(R.id.tv_sdt);
            tv_soluong = itemView.findViewById(R.id.tv_soLuong_);
            tv_tenSP = itemView.findViewById(R.id.tv_tensp);
            tv_gia = itemView.findViewById(R.id.tv_gia);
            anh = itemView.findViewById(R.id.imgv_anhsp);

            btn_Huy = itemView.findViewById(R.id.ibtn_Huy);
            btn_xacNhan = itemView.findViewById(R.id.ibtn_XacNhan);
        }

    }
}

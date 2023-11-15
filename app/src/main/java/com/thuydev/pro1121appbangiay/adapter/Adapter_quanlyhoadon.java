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

//    public Adapter_quanlyhoadon(Context context, List<DonHang> listDoHang) {
//        this.context = context;
//        list_doHang = listDoHang;
//    }


    public Adapter_quanlyhoadon(List<SanPham> list_sanPham, List<User> list_Users, List<DonHang> list_doHang, Context context) {

        this.list_sanPham = list_sanPham;
        this.list_Users = list_Users;
        this.list_doHang = list_doHang;

        this.context = context;
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
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        if (list_Users.size() == 0 && list_doHang.size() == 0 && list_sanPham.size() == 0) {
            return;
        }
        String[] data = getdata(position);
        Log.e("TAG", "onBindViewHolder: " + getdata(position));
        if (data.length <= 0) {
            return;
        }
        holder.tv_tenKH.setText("Họ tên:" + data[0]);
        holder.tv_diaChi.setText("Địa chỉ: " + data[1]);
        holder.tv_sdt.setText("Sđt: " + data[2]);
        holder.tv_gia.setText("Giá :" + data[3]);
        holder.tv_soluong.setText("Số lượng sản phẩm mua: " + data[4]);
        holder.btn_Huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trangThai(3, list_doHang.get(position));
            }
        });
        holder.btn_xacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trangThai(1, list_doHang.get(position));
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("user").document(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot snapshot = task.getResult();
                            if (snapshot.exists()) {
                                DonHang hang = new DonHang();
                                Long soDu = Long.parseLong(String.valueOf(snapshot.getDouble("soDu").longValue()));
                                if (soDu!=null) {
                                   Long newSoDu = Long.parseLong(String.valueOf(soDu - hang.getGiaDon().longValue()));

                                    Toast.makeText(context, "từ thành công", Toast.LENGTH_SHORT).show();
                                    db.collection("user").document(user.getUid()).update("soDu", newSoDu).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(context, "Tru thanh cong", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(context, "Tru that bai", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                } else {
                                    Toast.makeText(context, "Loi long", Toast.LENGTH_SHORT).show();
                                }
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
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        donHang.setTrangThai(i);

        db.collection("donHang").document(donHang.getMaDon()).set(donHang).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isComplete()) {
                    Toast.makeText(context, "Thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Lỗi cụ rồi bảo dev fix đi", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private String[] getdata(int position) {
        String[] a = new String[]{"", "", "", "", ""};
        for (User u : list_Users) {
            if (list_doHang.get(position).getMaKhachHang()
                    .equals(u.getMaUser())) {
                a[0] = u.getHoTen();
                a[1] = u.getChonDiaCHi();
                a[2] = u.getSDT();
            }
            Log.e("TAG", "getdata: 1" + u.getMaUser());
            Log.e("TAG", "getdata: 2" + list_doHang.get(position).getMaKhachHang());
        }
        Long tong = 0l;
        for (SanPham s : list_sanPham) {
            for (int i = 0; i < list_doHang.get(position).getListSP().size(); i++) {
                if (s.getMaSp().equals(list_doHang.get(position).getListSP().get(i).getMaSP())) {
                    tong += s.getGia();
                }
            }
        }
        a[3] = tong + "";
        a[4] = String.valueOf((list_doHang.get(position).getListSP().size() + 1));
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

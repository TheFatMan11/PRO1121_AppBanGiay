package com.thuydev.pro1121appbangiay.fragment;

import static android.app.ProgressDialog.show;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.thuydev.pro1121appbangiay.ManHinhAdmin;
import com.thuydev.pro1121appbangiay.R;
import com.thuydev.pro1121appbangiay.adapter.Adapter_hang;
import com.thuydev.pro1121appbangiay.adapter.Adapter_sanpham;
import com.thuydev.pro1121appbangiay.model.Hang;
import com.thuydev.pro1121appbangiay.model.SanPham;
import com.thuydev.pro1121appbangiay.model.ThuongHieu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;


public class QuanLyGiay extends Fragment {

    private RecyclerView rcv_list;
    private ImageButton ibtn_them;
    private Adapter_sanpham adapterSanpham;
    private LinearLayoutManager manager;
    private List<SanPham> list_giay;
    private List<ThuongHieu> list_thuongHieu;
    private FirebaseFirestore db;
    private ImageView anh;
    private String linkImage = "";

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        anhxa(view);
        ibtn_them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                them();
            }
        });

    }

    private SanPham sanPham;
    private EditText thuongHieu;

    private void them() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_themsp, null, false);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();
        anh = view.findViewById(R.id.imv_addAnh);
        EditText ten = view.findViewById(R.id.edt_tensp);
        thuongHieu = view.findViewById(R.id.edt_tenthuongHieu);
        EditText gia = view.findViewById(R.id.edt_giaSP);
        EditText list_kichco = view.findViewById(R.id.rcv_kichco);
        EditText namSX = view.findViewById(R.id.edt_namSX);
        EditText soLuong = view.findViewById(R.id.edt_soLuong);
        Button them = view.findViewById(R.id.btn_themSP);
        id = UUID.randomUUID().toString();
        anh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ManHinhAdmin admin = (ManHinhAdmin) getActivity();
                admin.layAnh();
            }
        });
        thuongHieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addHang();
            }
        });
        sanPham = new SanPham();
        them.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (ten.getText().toString().isEmpty() && thuongHieu.getText().toString().isEmpty()
                        && gia.getText().toString().isEmpty() && list_kichco.getText().toString().isEmpty()
                        && namSX.getText().toString().isEmpty() && soLuong.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Không được để trống", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (linkImage.isEmpty()) {
                    Toast.makeText(getContext(), "Vui lòng thêm ảnh sản phẩm", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!list_kichco.getText().toString().trim().matches("[0-9,]+")) {
                    Toast.makeText(getContext(), "Bạn không được viết gì khác ngoài số và dấu phẩy", Toast.LENGTH_SHORT).show();
                    return;
                }
                sanPham.setAnh(linkMoi);
                sanPham.setMaSp(id);
                sanPham.setTenSP(ten.getText().toString().trim());
                sanPham.setGia(Long.parseLong(gia.getText().toString()));
                sanPham.setNamSX(namSX.getText().toString());
                sanPham.setTime(new Date().getTime());
                sanPham.setSoLuong(Long.parseLong(soLuong.getText().toString()));
                List<String> kichCo = Arrays.asList(list_kichco.getText().toString().trim().split(","));
                sanPham.setKichCo(kichCo);
                db.collection("sanPham").document(id).set(sanPham).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isComplete()) {
                            Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        } else {
                            Toast.makeText(getContext(), "Lỗi", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    private Adapter_hang adapter;
    private EditText edt_hang;
    private int change = 0;

    private void addHang() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_them_hang, null, false);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();
        ListView listView = view.findViewById(R.id.list_hang);
        edt_hang = view.findViewById(R.id.edt_themhang_);
        ImageButton themHang = view.findViewById(R.id.ibtn_addhang);
        List<Hang> list = new ArrayList<>();
        ngheHang(list);
        adapter = new Adapter_hang(list, getContext());

        listView.setAdapter(adapter);
        edt_hang.setVisibility(View.GONE);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                builder1.setTitle("Cảnh báo").setIcon(R.drawable.cancel).setMessage("Nếu bạn xác nhận dữ liệu sẽ mất mãi mãi");
                builder1.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder1.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.collection("hang").document(list.get(position).getMaHang()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isComplete()){
                                    Toast.makeText(getContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(getContext(), "Lỗi", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
                builder1.create().show();

                return true;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                thuongHieu.setText(list.get(position).getTenHang());
                sanPham.setMaHang(list.get(position).getMaHang());
                dialog.dismiss();
            }
        });
        themHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = UUID.randomUUID().toString();
                if (change == 0) {
                    edt_hang.setVisibility(View.VISIBLE);
                    change = 1;
                } else {
                    edt_hang.setVisibility(View.GONE);
                    change = 0;
                    db.collection("hang").document(id).set(new Hang(id, edt_hang.getText().toString(), new Date().getTime())).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isComplete()) {
                                Toast.makeText(getContext(), "Thêm hãng thành công", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                thuongHieu.setText(edt_hang.getText().toString());
                                sanPham.setMaHang(id);
                            } else {
                                Toast.makeText(getContext(), "Lỗi", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });


    }

    private void ngheHang(List<Hang> list) {
        db.collection("hang").orderBy("time").addSnapshotListener(new EventListener<QuerySnapshot>() {
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
                                dc.getDocument().toObject(Hang.class);
                                list.add(dc.getDocument().toObject(Hang.class));
                                adapter.notifyDataSetChanged();
                                break;
                            case MODIFIED:
                                Hang dtoq = dc.getDocument().toObject(Hang.class);
                                if (dc.getOldIndex() == dc.getNewIndex()) {
                                    list.set(dc.getOldIndex(), dtoq);
                                    adapterSanpham.notifyDataSetChanged();
                                } else {
                                    list.remove(dc.getOldIndex());
                                    list.add(dtoq);
                                    adapter.notifyDataSetChanged();
                                }
                                break;
                            case REMOVED:
                                dc.getDocument().toObject(Hang.class);
                                list.remove(dc.getOldIndex());
                                adapter.notifyDataSetChanged();
                                break;
                        }
                    }
                }
            }
        });
    }

    private String id;

    private void anhxa(View view) {
        rcv_list = view.findViewById(R.id.rcv_qlsp);
        ibtn_them = view.findViewById(R.id.ibtn_them_sp);
        list_giay = new ArrayList<>();
        db = FirebaseFirestore.getInstance();
        nghe();
        list_thuongHieu = new ArrayList<>();
        adapterSanpham = new Adapter_sanpham(list_giay, getContext());
        rcv_list.setAdapter(adapterSanpham);
        manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        rcv_list.setLayoutManager(manager);
    }

    private void nghe() {
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
                                list_giay.add(dc.getDocument().toObject(SanPham.class));
                                adapterSanpham.notifyDataSetChanged();
                                break;
                            case MODIFIED:
                                SanPham dtoq = dc.getDocument().toObject(SanPham.class);
                                if (dc.getOldIndex() == dc.getNewIndex()) {
                                    list_giay.set(dc.getOldIndex(), dtoq);
                                    adapterSanpham.notifyDataSetChanged();
                                } else {
                                    list_giay.remove(dc.getOldIndex());
                                    list_giay.add(dtoq);
                                    adapterSanpham.notifyDataSetChanged();
                                }
                                break;
                            case REMOVED:
                                dc.getDocument().toObject(SanPham.class);
                                list_giay.remove(dc.getOldIndex());
                                adapterSanpham.notifyDataSetChanged();
                                break;
                        }
                    }
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quan_ly_giay, container, false);
    }

    public void hienthiAnh(Uri uri) {
        Glide.with(getContext()).load(uri).error(R.drawable.shoes).into(anh);
        linkImage = uri.toString();
        upAnh(uri);
    }

    private StorageReference storageReference;
private String linkMoi="";
    private void upAnh(Uri imageUri) {
        // Tạo một tham chiếu đến nơi bạn muốn lưu trữ tệp ảnh trong Firebase Storage
        storageReference = FirebaseStorage.getInstance().getReference("images").child(id);
        // Tải lên tệp ảnh
        storageReference.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        storageReference.child("images").child(id).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                linkMoi = uri.toString();
                                Toast.makeText(getContext(), "Up ảnh thành công", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Lỗi khi tải lên
                    }
                });

    }

}
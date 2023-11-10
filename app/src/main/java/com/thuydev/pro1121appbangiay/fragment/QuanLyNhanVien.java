package com.thuydev.pro1121appbangiay.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.thuydev.pro1121appbangiay.R;
import com.thuydev.pro1121appbangiay.adapter.AdapterUser;
import com.thuydev.pro1121appbangiay.model.SanPham;
import com.thuydev.pro1121appbangiay.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class QuanLyNhanVien extends Fragment {
    List<User> list = new ArrayList<>();
    EditText edt_maNV, edt_Email, edt_matKhau, edt_hoTen, edt_sdt, edt_cv;
    AppCompatButton btn_Luu, btn_Huy;
    RecyclerView recyclerView;
    ImageButton button;
    AdapterUser adapterUser;
    String id;
    User user = new User();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public QuanLyNhanVien() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quan_ly_nhan_vien, null);
        recyclerView = view.findViewById(R.id.rcv_nhanVien);
        button = view.findViewById(R.id.ibtn_them_nv);

        loatData();
        nghe1();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                LayoutInflater inflater2 = getLayoutInflater();
                View view1 = inflater2.inflate(R.layout.dialog_nhanvien, null);
                builder.setView(view1);
                Dialog dialog = builder.create();
                dialog.show();

                edt_Email = view1.findViewById(R.id.edt_email);
                edt_matKhau = view1.findViewById(R.id.edt_matKhau);
                edt_hoTen = view1.findViewById(R.id.edt_hoTen);
                edt_sdt = view1.findViewById(R.id.edt_sdt);
                edt_cv = view1.findViewById(R.id.edt_chuVu);
                btn_Luu = view1.findViewById(R.id.btn_Luu);
                btn_Huy = view1.findViewById(R.id.btn_Huy);

                btn_Luu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String email = edt_Email.getText().toString();
                        String matkhau = edt_matKhau.getText().toString();
                        String hoten = edt_hoTen.getText().toString();
                        String sdt = edt_sdt.getText().toString();
                        String cv = edt_cv.getText().toString();
                        id = UUID.randomUUID().toString();


                        if (email.isEmpty() || matkhau.isEmpty() || hoten.isEmpty() || sdt.isEmpty() || cv.isEmpty()) {
                            Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                        } else if (!isValidateEmail(email)) {
                            Toast.makeText(getContext(), "Không đúng định dạng của email", Toast.LENGTH_SHORT).show();
                        } else if (matkhau.length() < 8) {
                            Toast.makeText(getContext(), "Mật khẩu phải từ 8 chữ số", Toast.LENGTH_SHORT).show();
                        } else if (!isValidatePhone(sdt) || sdt.length() > 10) {
                            Toast.makeText(getContext(), "Số điện thoại không đúng", Toast.LENGTH_SHORT).show();
                        } else {
                            user.setMaUser(id);
                            user.setEmail(email);
                            user.setHoTen(hoten);
                            user.setSDT(Long.parseLong(sdt));
                            user.setChucVu((int) Long.parseLong(cv));
                            user.setTrangThai(1);

                            db.collection("user").add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    if (documentReference == null) {
                                        Toast.makeText(getContext(), "Them thanh cong", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getContext(), "Them that bai", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });


                btn_Huy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

            }
        });


        return view;
    }

    public boolean isValidateEmail(CharSequence e) {
        return !TextUtils.isEmpty(e) && Patterns.EMAIL_ADDRESS.matcher(e).matches();
    }

    public boolean isValidatePhone(CharSequence e) {
        return !TextUtils.isEmpty(e) && Patterns.PHONE.matcher(e).matches();
    }

    public void loatData() {
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        adapterUser = new AdapterUser(getContext(), list);
        recyclerView.setAdapter(adapterUser);
    }

    private void nghe1() {
        CollectionReference reference = db.collection("user");

        reference.whereEqualTo("chucVu", 2).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (queryDocumentSnapshots.toObjects(User.class).isEmpty()) {
                    return;
                }
                for (DocumentChange dc : queryDocumentSnapshots.getDocumentChanges()) {
                    switch (dc.getType()) {
                        case ADDED:
                            list.add(dc.getDocument().toObject(User.class));
                            adapterUser.notifyDataSetChanged();
                            break;
                        case MODIFIED:
                            User dtoq = dc.getDocument().toObject(User.class);
                            if (dc.getOldIndex() == dc.getNewIndex()) {
                                list.set(dc.getOldIndex(), dtoq);
                            } else {
                                list.remove(dc.getOldIndex());
                                list.add(dtoq);
                            }
                            adapterUser.notifyDataSetChanged();
                            break;
                        case REMOVED:
                            list.remove(dc.getOldIndex());
                            adapterUser.notifyDataSetChanged();
                            break;
                    }
                }
            }
        });
    }

    private void nghe() {
        CollectionReference reference = db.collection("user");

        reference.whereEqualTo("chucVu", 2).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (queryDocumentSnapshots.toObjects(User.class) == null) {
                    return;
                }
                for (DocumentChange dc : queryDocumentSnapshots.getDocumentChanges()) {
                    switch (dc.getType()) {
                        case ADDED:
                            dc.getDocument().toObject(User.class);
                            list.add(dc.getDocument().toObject(User.class));
                            adapterUser.notifyDataSetChanged();
                            break;
                        case MODIFIED:
                            User dtoq = dc.getDocument().toObject(User.class);
                            if (dc.getOldIndex() == dc.getNewIndex()) {
                                list.set(dc.getOldIndex(), dtoq);
                                adapterUser.notifyDataSetChanged();
                            } else {
                                list.remove(dc.getOldIndex());
                                list.add(dtoq);
                                adapterUser.notifyDataSetChanged();
                            }
                            break;
                        case REMOVED:
                            dc.getDocument().toObject(User.class);
                            list.remove(dc.getOldIndex());
                            adapterUser.notifyDataSetChanged();
                            break;
                    }
                }
            }
        });

    }

}
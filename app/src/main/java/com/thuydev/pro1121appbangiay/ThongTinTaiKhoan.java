package com.thuydev.pro1121appbangiay;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.thuydev.pro1121appbangiay.adapter.Adapter_thongtin;
import com.thuydev.pro1121appbangiay.model.SanPham;
import com.thuydev.pro1121appbangiay.model.User;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ThongTinTaiKhoan extends AppCompatActivity {
    ViewPager2 pager2;
    TabLayout tabLayout;
    TabLayoutMediator mediator;
    Adapter_thongtin adapterThongtin;
    CircleImageView avatar;
    ImageView edit_profile,cancel;
    TextView ten, tien;
    FirebaseFirestore db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongtintaikhoan);
        user = FirebaseAuth.getInstance().getCurrentUser();
        adapterThongtin = new Adapter_thongtin(this);
        pager2 = findViewById(R.id.viewPage2_thongtin_khach);
        avatar = findViewById(R.id.imv_avatar);
        edit_profile = findViewById(R.id.imv_updatethongtin);
        cancel = findViewById(R.id.imv_cancel);
        ten = findViewById(R.id.tv_username_khach);
        tien = findViewById(R.id.tv_sodu_khach);
        db = FirebaseFirestore.getInstance();
        progressDialog = new ProgressDialog(ThongTinTaiKhoan.this);
        nghe();
        pager2.setAdapter(adapterThongtin);
        tabLayout = findViewById(R.id.tabLayout_thongtinkhach);
cancel.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        finish();
    }
});
        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                suaProFile();
            }
        });

        mediator = new TabLayoutMediator(tabLayout, pager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                if (position == 0) {
                    tab.setText("Thông tin cá nhân");
                } else {
                    tab.setText("Khoản chi trong tháng");
                }
            }
        });

        mediator.attach();
    }
    ImageView anh;
    private void suaProFile() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_updateprofile, null, false);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        anh = view.findViewById(R.id.imv_addAnh_edit);
        EditText ten = view.findViewById(R.id.edt_hoten_edit);
        EditText email = view.findViewById(R.id.edt_email_edit);
        EditText sdt = view.findViewById(R.id.edt_sdt_edit);
        Button sua = view.findViewById(R.id.btn_edit);

        anh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yeucauquyen(ThongTinTaiKhoan.this);
            }
        });
        if (us == null && user == null) {
            return;
        }
        Glide.with(this).load(user.getPhotoUrl()).
                error(R.drawable.baseline_crop_original_24).into(anh);
        ten.setText(us.getHoTen());
        email.setText(us.getEmail());
        sdt.setText(us.getSDT() + "");
        sua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (us == null && user == null) {
                    Toast.makeText(ThongTinTaiKhoan.this, "Không được để trống", Toast.LENGTH_SHORT).show();
                    return;
                }
                progressDialog.show();
                us.setEmail(email.getText().toString());
                us.setHoTen(ten.getText().toString());
                us.setSDT(sdt.getText().toString());
                setData();
                seTaiKhoan(Uri.parse(linkMoi));
                dialog.dismiss();
            }
        });


    }

    private void seTaiKhoan(Uri uri) {
        UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                .setDisplayName(us.getHoTen())
                .setPhotoUri(uri)
                .build();
        user.updateProfile(profileChangeRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isComplete()) {
                    Toast.makeText(ThongTinTaiKhoan.this, "Hoàn tất", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ThongTinTaiKhoan.this, "Lỗi", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setData() {
        db.collection("user").document(user.getUid()).set(us).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isComplete()) {
                    Toast.makeText(ThongTinTaiKhoan.this, "Thành công", Toast.LENGTH_SHORT).show();
                    progressDialog.cancel();
                } else {
                    Toast.makeText(ThongTinTaiKhoan.this, "Lỗi", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    User us;
    FirebaseUser user;

    private void nghe() {

        db.collection("user").document(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (!task.isComplete()) {
                    Toast.makeText(ThongTinTaiKhoan.this, "Lỗi", Toast.LENGTH_SHORT).show();
                    return;
                }
                us = task.getResult().toObject(User.class);
                if (us == null) {
                    Toast.makeText(ThongTinTaiKhoan.this, "Lỗi", Toast.LENGTH_SHORT).show();
                    return;
                }
                Glide.with(ThongTinTaiKhoan.this).
                        load(user.getPhotoUrl()).error(R.drawable.user1).into(avatar);
                if (us.getHoTen() == null) {
                    return;
                }
                ten.setText(us.getHoTen());
                tien.setText("Số dư: " + us.getSoDu() + " đ");
            }
        });
    }

    public void layAnh() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        launcher.launch(intent);
    }

    public void yeucauquyen(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            layAnh();
            return;
        }
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.TIRAMISU){
            String[] quyen = new String[]{android.Manifest.permission.READ_MEDIA_IMAGES};
            requestPermissions(quyen, CODE_QUYEN);
            return;
        }
        if (context.checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            // xử lý sau
            layAnh();
        } else {
            String[] quyen = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};
            requestPermissions(quyen, CODE_QUYEN);
        }
    }

    private static final int CODE_QUYEN = 1;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CODE_QUYEN) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                layAnh();
            } else {
                Toast.makeText(this, "Bạn cần cấp quyền để sử dụng tính năng này", Toast.LENGTH_SHORT).show();
            }
        }
    }
    Uri uri;
    ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult o) {
                    if (o.getResultCode() == RESULT_OK) {
                        Intent intent = o.getData();
                        if (intent == null) {
                            return;
                        }
                        uri = intent.getData();
                        upAnh(uri);

                    }

                }
            });
String linkMoi;
ProgressDialog progressDialog;
    public void upAnh(Uri imageUri ) {
        StorageReference storageReference;
        storageReference = FirebaseStorage.getInstance().getReference("images").child(user.getUid());
        storageReference.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        storageReference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                if (task.isSuccessful()) {
                                    Uri uri = task.getResult();
                                    linkMoi = uri.toString();
                                    Glide.with(ThongTinTaiKhoan.this).load(linkMoi).into(anh);
                                } else {
                                    Toast.makeText(ThongTinTaiKhoan.this, "Lỗi khi lấy đường dẫn", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ThongTinTaiKhoan.this, "Lỗi", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}

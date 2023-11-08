package com.thuydev.pro1121appbangiay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class DangNhap_Activity extends AppCompatActivity {
    private Button dangky, dangNhap;
    private EditText email, matKhau;
    private TextView quenMK;
    private ProgressDialog progressDialog;
    private FirebaseFirestore db;
    private DocumentReference reference;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        anhxa();
        dangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chuyen(DangKy_Activity.class);
            }
        });

        dangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dangnhap();
            }
        });
        quenMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quenMK();
            }
        });
    }

    private void quenMK() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.dialog_quenpass, null, false);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();
        EditText email = view.findViewById(R.id.edt_email_quen);
        Button gui = view.findViewById(R.id.btn_quen);

        gui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quenPass(email,dialog);

            }
        });
    }

    private void quenPass(EditText email,Dialog dialog) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String emailAddress = email.getText().toString();
        if (emailAddress.isEmpty()){
            Toast.makeText(this, "Không được để trống", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Sẽ mất một lúc vui lòng chờ");
        progressDialog.show();
        auth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            progressDialog.cancel();
                            Toast.makeText(DangNhap_Activity.this, "Đã gửi link khôi phục hãy kiểm tra email", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    }
                });
    }

    private void anhxa() {
        dangky = findViewById(R.id.btn_dangky);
        dangNhap = findViewById(R.id.btn_dangnhap);
        email = findViewById(R.id.edt_email_dangnhap);
        matKhau = findViewById(R.id.edt_matkhau_dangnhap);
        quenMK = findViewById(R.id.tv_quenpass);
        progressDialog = new ProgressDialog(this);
    }


    public void dangnhap() {
        String mEmail = email.getText().toString().trim();
        String mPass = matKhau.getText().toString().trim();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        if (email.getText().toString().isEmpty()||matKhau.getText().toString().isEmpty()){
            Toast.makeText(this, "Không được để trống", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Sẽ mất một lúc vui lòng chờ");
        progressDialog.show();
        mAuth.signInWithEmailAndPassword(mEmail, mPass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(DangNhap_Activity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                            vaomanhinh();

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(DangNhap_Activity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.cancel();
                    }
                });
    }

    private void chuyen(Class a) {
        Intent intent = new Intent(this, a);
        startActivity(intent);
    }


    private void vaomanhinh() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            intent = new Intent(this, DangNhap_Activity.class);
            startActivity(intent);
        } else {
            db = FirebaseFirestore.getInstance();
            final Long[] chucvu = {0l};
            reference = db.collection("user").document(user.getUid());
            reference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isComplete()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            // Tài liệu người dùng tồn tại
                            Map<String, Object> userData = document.getData();
                            chucvu[0] = (Long) userData.get("chucvu");
                            if (chucvu[0] == 1) {
                                intent = new Intent(DangNhap_Activity.this, ManHinhAdmin.class);
                                startActivity(intent);
                            } else if (chucvu[0] == 2) {
                                Toast.makeText(DangNhap_Activity.this, "Màn hình nhân viên", Toast.LENGTH_SHORT).show();
                            } else if (chucvu[0] == 3) {
                                Toast.makeText(DangNhap_Activity.this, "Màn hình khách hàng", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(DangNhap_Activity.this, "Lỗi", Toast.LENGTH_SHORT).show();
                            }
                            finish();
                        } else {
                            Toast.makeText(DangNhap_Activity.this, "Người dùng không tồn tại", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(DangNhap_Activity.this, "Lỗi truy vấn", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }

    }
}
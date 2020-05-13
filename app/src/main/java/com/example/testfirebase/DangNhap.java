package com.example.testfirebase;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.testfirebase.Common.Common;
import com.example.testfirebase.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class DangNhap extends AppCompatActivity {
    EditText editSdt, editMk;
    Button btndangnhap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        editSdt = findViewById(R.id.editSdt);
        editMk = findViewById(R.id.editMk);
        btndangnhap = findViewById(R.id.btndangnhap);

        //Trong Firebase
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        btndangnhap.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final ProgressDialog mDialog = new ProgressDialog(DangNhap.this);
                mDialog.setMessage("Vui lòng đợi....!");
                mDialog.show();

                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //kiểm tra nếu người dùng không tồn tại trong cơ sở dữ liệu
                        if (dataSnapshot.child(editSdt.getText().toString()).exists()) {
                            //lấy thông tin user
                            mDialog.dismiss();
                            User user = dataSnapshot.child(editSdt.getText().toString()).getValue(User.class);
                            assert user != null;
                            if (user.getPass().equals(editMk.getText().toString())) {
                               // Toast.makeText(DangNhap.this, "Dang nhap thanh cong!!", Toast.LENGTH_LONG).show();
                                Intent homeIntent = new Intent(DangNhap.this, Home.class);
                                Common.currentUser = user;
                                startActivity(homeIntent);
                                finish();
                            } else {
                                Toast.makeText(DangNhap.this, "Sai mat khau!!!!!!!", Toast.LENGTH_LONG).show();
                            }
                        }
                        else
                            {
                                mDialog.dismiss();
                                Toast.makeText(DangNhap.this, "User khong ton tai trong csdl.", Toast.LENGTH_LONG).show();

                            }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}

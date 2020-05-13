package com.example.testfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.testfirebase.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DangKy extends AppCompatActivity {
    EditText editTen, editSdt, editMk;
    Button btndangky;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);

        editTen = findViewById(R.id.editTen);
        editSdt = findViewById(R.id.editSdt);
        editMk = findViewById(R.id.editMk);
        btndangky = findViewById(R.id.btndangky);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        btndangky.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final ProgressDialog mDialog = new ProgressDialog(DangKy.this);
                mDialog.setMessage("Vui lòng đợi....!");
                mDialog.show();

                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(editSdt.getText().toString()).exists())
                        {
                        mDialog.dismiss();
                        Toast.makeText(DangKy.this,"So dien thoai da dang ky!!", Toast.LENGTH_LONG).show();
                        }else
                            {
                                mDialog.dismiss();
                                User user = new User(editTen.getText().toString(),editMk.getText().toString());
                                table_user.child(editSdt.getText().toString()).setValue(user);
                                Toast.makeText(DangKy.this,"Da dang ky thanh cong!!", Toast.LENGTH_LONG).show();
                                finish();
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

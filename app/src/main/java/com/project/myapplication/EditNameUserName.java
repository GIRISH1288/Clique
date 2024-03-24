package com.project.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditNameUserName extends AppCompatActivity {
    private EditText etEditFullName, etEditUserName;
    private Button btnEditUpdateUserName;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_name_user_name);

        etEditUserName = findViewById(R.id.etEditUserName);
        etEditFullName = findViewById(R.id.etEditFullName);
        btnEditUpdateUserName = findViewById(R.id.btnEditUpdateUserName);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        userID = mAuth.getCurrentUser().getUid();

        btnEditUpdateUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etEditUserName.getText().toString().trim();
                String name = etEditFullName.getText().toString().trim();

                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(name)) {
                    Toast.makeText(EditNameUserName.this, "Enter Username and Name", Toast.LENGTH_SHORT).show();
                    return;
                }

                DocumentReference documentReference = db.collection("users").document(userID);
                Map<String, Object> user = new HashMap<>();
                user.put("userName", username);
                user.put("name", name);
                documentReference.update(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(EditNameUserName.this, "Profile Updated Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(EditNameUserName.this, EditProfileMenu.class));
                            finish();
                        } else {
                            Toast.makeText(EditNameUserName.this, "Failed to update profile", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
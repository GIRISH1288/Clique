package com.project.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

public class EditAboutSection extends AppCompatActivity {

    EditText etabout, etskills;
    Button btnsetabout;
    String about, skills;
    FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_about_section);

        etabout = findViewById(R.id.etabout);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        userID = mAuth.getCurrentUser().getUid();
        etskills = findViewById(R.id.etskills);
        btnsetabout = findViewById(R.id.btnsetabout);
        btnsetabout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                about = etabout.getText().toString().trim();
                skills = etskills.getText().toString().trim();
                if (about == null || skills == null) {
                    Toast.makeText(EditAboutSection.this, "Add Information", Toast.LENGTH_SHORT).show();
                    return;
                }
                DocumentReference documentReference = db.collection("users").document(userID);
                Map<String, Object> user = new HashMap<>();
                user.put("about", about);
                user.put("skills", skills);

                documentReference.update(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(EditAboutSection.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(EditAboutSection.this, "Failed to update profile", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        });
    }
}


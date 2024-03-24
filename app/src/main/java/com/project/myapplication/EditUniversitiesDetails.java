package com.project.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditUniversitiesDetails extends AppCompatActivity {
    private AutoCompleteTextView tvUniversity;
    private AutoCompleteTextView tvDepartment;
    private Button btnEditUpdateUniversity;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private String userID;
    private String selectedUniversity;
    private String selectedDepartment;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_universities_details);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        userID = mAuth.getCurrentUser().getUid();

        tvUniversity = findViewById(R.id.tvUniversityName);
        tvDepartment = findViewById(R.id.tvDepartmentName);
        btnEditUpdateUniversity = findViewById(R.id.btnEditUpdateUniversity);

        String[] maharashtraCollegesAndUniversities = {"University of Mumbai", "Savitribai Phule Pune University", "Indian Institute of Technology Bombay",
                "Visvesvaraya National Institute of Technology", "Dr. Babasaheb Ambedkar Marathwada University", "University of Pune", "University of Nagpur", "Maharashtra University of Health Sciences", "Swami Ramanand Teerth Marathwada University", "Rashtrasant Tukadoji Maharaj Nagpur University", "Shivaji University", "Savitribai Phule Pune University",
                "Bharati Vidyapeeth Deemed University", "Dr. D. Y. Patil Vidyapeeth", "Yashwantrao Chavan Maharashtra Open University", "Symbiosis International University", "Tilak Maharashtra University", "MGM Institute of Health Sciences", "MIT Art, Design and Technology University", "Shreemati Nathibai Damodar Thackersey Women's University", "SNDT Women's University", "Tata Institute of Social Sciences", "Tata Institute of Fundamental Research", "Smt. Nathibai Damodar Thackersey Women's University", "Swami Ramanand Teerth Marathwada University",
                "Tata Institute of Social Sciences", "Dr. D. Y. Patil Institute of Technology, Pune", "Babasaheb Bhimrao Ambedkar University", "Bharati Vidyapeeth University", "Gokhale Institute of Politics and Economics", "Gondwana University", "Krishna Institute of Medical Sciences Deemed University", "Loknete Vyankatrao Hiray Arts, Science & Commerce College", "Maharashtra National Law University Mumbai", "Mahatma Phule Agriculture University", "Mahatma Gandhi Mission Institute of Health Sciences",
                "North Maharashtra University", "Sant Gadge Baba Amravati University", "Saraswati Vidyapeeth", "Smt. Nathibai Damodar Thackersey Women's University", "Symbiosis International University", "Tata Institute of Fundamental Research", "University of Mumbai", "University of Nagpur", "Visvesvaraya National Institute of Technology", "Walchand College of Engineering", "Yashwantrao Chavan Maharashtra Open University"
        };
        String[] commonDepartments = {
                "Computer Science", "Electrical Engineering", "Mechanical Engineering", "Civil Engineering", "Chemical Engineering",
                "Biotechnology", "Information Technology", "Electronics and Communication Engineering", "Mathematics", "Physics",
                "Chemistry", "Biology", "Environmental Science", "Geology", "Economics", "Business Administration",
                "Management Studies", "Accounting", "Finance", "Human Resource Management", "Marketing", "Law", "Medicine",
                "Pharmacy", "Nursing", "Dentistry", "Physiotherapy", "Education", "Social Work", "Fine Arts", "Performing Arts",
                "Journalism", "Mass Communication", "Psychology", "Sociology", "Political Science", "History", "Geography",
                "Languages and Literature", "Anthropology", "Archaeology", "Astrophysics", "Biochemistry", "Biomedical Engineering",
                "Botany", "Cell Biology", "Ceramic Engineering"
        };

        ArrayAdapter<String> universityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, maharashtraCollegesAndUniversities);
        ArrayAdapter<String> departmentAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, commonDepartments);

        tvUniversity.setAdapter(universityAdapter);
        tvDepartment.setAdapter(departmentAdapter);

        tvUniversity.setOnItemClickListener((parent, view, position, id) -> {
            selectedUniversity = (String) parent.getItemAtPosition(position);
        });
        tvDepartment.setOnItemClickListener((parent, view, position, id) -> {
            selectedDepartment = (String) parent.getItemAtPosition(position);
        });
        btnEditUpdateUniversity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedUniversity == null || selectedDepartment == null) {
                    Toast.makeText(EditUniversitiesDetails.this, "Please select both university and department", Toast.LENGTH_SHORT).show();
                    return;
                }
                DocumentReference documentReference = db.collection("users").document(userID);
                Map<String, Object> user = new HashMap<>();
                user.put("university", selectedUniversity);
                user.put("department", selectedDepartment);

                documentReference.update(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(EditUniversitiesDetails.this, "Profile Updated Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(EditUniversitiesDetails.this, EditProfileMenu.class));
                            finish();
                        } else {
                            Toast.makeText(EditUniversitiesDetails.this, "Failed to update profile", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }
}
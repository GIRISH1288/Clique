package com.project.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditCity extends AppCompatActivity {
    private AutoCompleteTextView etEditCity;
    private Button btnEditUpdateCity;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private String userID;
    String city;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_city);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        etEditCity = findViewById(R.id.etEditCity);
        btnEditUpdateCity = findViewById(R.id.btnEditUpdateCity);

        String[] maharashtraCities = {
                "Mumbai", "Nagpur", "Nashik", "Aurangabad", "Solapur", "Amravati", "Navi Mumbai",
                "Kolhapur", "Sangli-Miraj & Kupwad", "Malegaon", "Jalgaon", "Akola", "Latur", "Dhule",
                "Ahmednagar", "Chandrapur", "Parbhani", "Ichalkaranji", "Jalna", "Bhusawal", "Nanded",
                "Satara", "Beed", "Wardha", "Yavatmal", "Achalpur", "Osmanabad", "Nandurbar", "Washim",
                "Hinganghat", "Pimpri-Chinchwad", "Gondia", "Baramati", "Panvel", "Ulhasnagar", "Mira-Bhayandar",
                "Nandurbar", "Buldana", "Wani", "Ratnagiri", "Sindhudurg", "Udgir", "Ambajogai", "Gondia",
                "Tumsar", "Ausa", "Ballarpur", "Barshi", "Basmat", "Bhadravati", "Chalisgaon", "Chandrapur",
                "Devgarh", "Deulgaon Raja", "Dharampuri", "Digras", "Gadchiroli", "Gangakhed", "Georai",
                "Ghatanji", "Hinganghat", "Hingoli", "Ichalkaranji", "Junnar", "Kalyan-Dombivli", "Karad",
                "Khamgaon", "Khopoli", "Koradi", "Loha", "Lonavla", "Mahad", "Mahuli", "Malad", "Manchar",
                "Mangalvedhe", "Manjlegaon", "Manmad", "Mehkar", "Mhaswad", "Mira-Bhayandar", "Morshi",
                "Mukhed", "Mul", "Greater Mumbai", "Pandharkaoda", "Paithan", "Palghar", "Palus", "Patur",
                "Pauni", "Pen", "Phaltan", "Pulgaon", "Pune", "Purna", "Pusad", "Rahuri", "Rajura",
                "Ramtek", "Ratnagiri", "Raver", "Risod", "Sailu", "Sangamner", "Sangli", "Sangole",
                "Sasvad", "Satana", "Savner", "Sawantwadi", "Shahade", "Shegaon", "Shendurjana", "Shirdi",
                "Shirpur-Warwade", "Shirur", "Shrigonda", "Shrirampur", "Sillod", "Sinnar", "Solapur",
                "Soyagaon", "Talegaon Dabhade", "Talode", "Tasgaon", "Thane", "Tirora", "Tuljapur", "Tumsar",
                "Uran", "Umarkhed", "Umarga", "Umarkhed", "Vadgaon Kasba", "Vaijapur", "Vasai-Virar",
                "Vita", "Wadgaon Road", "Wai", "Wani", "Wardha", "Warora", "Warud", "Washim", "Wani",
                "Yawal", "Yevla", "Yeola"
        };
        ArrayAdapter<String> adapterCity = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, maharashtraCities);
        etEditCity.setAdapter(adapterCity);

        etEditCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                city = (String) parent.getItemAtPosition(position);
            }
        });

        btnEditUpdateCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateCity();
            }
        });
    }
    private void updateCity() {
        userID = mAuth.getCurrentUser().getUid();
        DocumentReference userRef = db.collection("users").document(userID);
        Map<String, Object> userProfileUpdates = new HashMap<>();
        userProfileUpdates.put("city", city);

        userRef.update(userProfileUpdates)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // City updated successfully
                        Toast.makeText(EditCity.this, "City updated successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditCity.this, EditProfileMenu.class));
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        // Handle any errors that occurred while updating the city
                        Toast.makeText(EditCity.this, "Failed to update city: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
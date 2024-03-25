package com.project.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CreateProfile extends AppCompatActivity {
    private EditText etCreateProfileBirthDate, etCreateProfileFullName, etCreateProfileUserName;
    private Calendar calendar;
    private String birthdate;
    private String city;
    private AutoCompleteTextView tvCreateProfileCity;
    private Spinner spinnerCreateProfileGender;
    private String gender;
    private Button btnCreateProfileCreateAccount;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);


        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        etCreateProfileBirthDate = findViewById(R.id.etCreateProfileBirthDate);
        etCreateProfileFullName = findViewById(R.id.etCreateProfileFullName);
        etCreateProfileUserName = findViewById(R.id.etCreateProfileUserName);
        tvCreateProfileCity = findViewById(R.id.tvCreateProfileCity);
        calendar = Calendar.getInstance();
        spinnerCreateProfileGender = findViewById(R.id.spinnerCreateProfileGender);
        btnCreateProfileCreateAccount = findViewById(R.id.btnCreateProfileCreateAccount);


        //Initialising Arrays
        String[] genderOptions = {"Male", "Female", "Other"};
        String[] maharashtraCities = {
                "Mumbai", "Pune", "Nagpur", "Nashik", "Aurangabad", "Solapur", "Amravati", "Navi Mumbai",
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
        //Initialising and setting adapters
        //Gender
        ArrayAdapter<String> adapterGender = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, genderOptions);
        adapterGender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCreateProfileGender.setAdapter(adapterGender);
        spinnerCreateProfileGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Capture the selected gender
                gender = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing if nothing is selected
            }
        });
        //cities
        ArrayAdapter<String> adapterCity = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, maharashtraCities);
        tvCreateProfileCity.setAdapter(adapterCity);
        tvCreateProfileCity.setOnItemClickListener((parent, view, position, id) -> {
            city = (String) parent.getItemAtPosition(position);
        });
        btnCreateProfileCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etCreateProfileFullName.getText().toString().trim();
                String username = etCreateProfileUserName.getText().toString().trim();
                if (TextUtils.isEmpty(username)) {
                    Toast.makeText(CreateProfile.this, "Enter Username", Toast.LENGTH_SHORT).show();
                    return; // Exit method if username is empty
                }
                if (TextUtils.isEmpty(city)) {
                    Toast.makeText(CreateProfile.this, "Enter city", Toast.LENGTH_SHORT).show();
                    return; // Exit method if username is empty
                }
                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(CreateProfile.this, "Enter name", Toast.LENGTH_SHORT).show();
                    return; // Exit method if username is empty
                }
                if (TextUtils.isEmpty(birthdate)) {
                    Toast.makeText(CreateProfile.this, "Enter birthdate", Toast.LENGTH_SHORT).show();
                    return; // Exit method if username is empty
                }
                db.collection("users")
                        .whereEqualTo("username", username)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    if (!task.getResult().isEmpty()) {
                                        // Username already exists, display error message
                                        Toast.makeText(CreateProfile.this, "Username already exists. Choose a different one.", Toast.LENGTH_SHORT).show();
                                    } else {
                                        // Username is unique, proceed with updating profile
                                        // Get other profile information
                                        userID = mAuth.getCurrentUser().getUid();
                                        DocumentReference userRef = db.collection("users").document(userID);
                                        Map<String, Object> userProfileUpdates = new HashMap<>();
                                        userProfileUpdates.put("city", city);
                                        userProfileUpdates.put("gender", gender);
                                        userProfileUpdates.put("name", name);
                                        userProfileUpdates.put("birthdate", birthdate);
                                        userProfileUpdates.put("username", username); // Add username field

                                        // Update profile
                                        userRef.update(userProfileUpdates)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        // Profile fields updated successfully
                                                        Toast.makeText(CreateProfile.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
                                                        // Proceed to the next activity or perform other actions
                                                        startActivity(new Intent(CreateProfile.this, BottomNavigation.class));
                                                        finish();
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(Exception e) {
                                                        // Handle any errors that occurred while updating the profile fields
                                                        Toast.makeText(CreateProfile.this, "Failed to update profile: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                    }
                                } else {
                                    // Error occurred while checking username existence
                                    Toast.makeText(CreateProfile.this, "Error checking username existence.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

    }
    public void showDatePickerDialog(View view) {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        updateDateEditText();
                        birthdate = year + "-" + (month + 1) + "-" + dayOfMonth;
                    }
                },
                year, month, dayOfMonth);

        datePickerDialog.show();
    }

    private void updateDateEditText() {
        String dateFormat = "dd/MM/yyyy"; // Customize date format if needed
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(dateFormat, java.util.Locale.getDefault());
        etCreateProfileBirthDate.setText(sdf.format(calendar.getTime()));
    }
    @Override
    public void onBackPressed() {
        // Call finishAffinity() to close all activities in the current task
        // and exit the app
        super.onBackPressed();
        finishAffinity();
    }
}
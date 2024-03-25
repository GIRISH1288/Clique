package com.project.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {
    LottieAnimationView step2, step3, step4, loading_animation;
    TextView tvSignUpEmailGreeting, tvSignUpEmailText, tvSignUpPasswordGreeting, tvSignUpPasswordText, tvSignUpUserNameGreeting, tvSignUpUserNameText, tvSignUpPhoneNumberGreeting, tvSignUpPhoneNumberText;
    EditText etSignUpEmail, etSignUpPassword, etSignUpUserName, etSignUpPhoneNumber;
    Button btnSignUpEmail, btnSignUpPassword, btnSignUpCreateAccount, btnSignUpPhoneNumber;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        //Initializing views
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        tvSignUpEmailGreeting = findViewById(R.id.tvSignUpEmailGreeting);
        tvSignUpEmailText = findViewById(R.id.tvSignUpEmailText);
        tvSignUpPhoneNumberGreeting = findViewById(R.id.tvSignUpPhoneNumberGreeting);
        tvSignUpPhoneNumberText = findViewById(R.id.tvSignUpPhoneNumberText);
        tvSignUpPasswordGreeting = findViewById(R.id.tvSignUpPasswordGreeting);
        tvSignUpPasswordText = findViewById(R.id.tvSignUpPasswordText);
        tvSignUpUserNameGreeting = findViewById(R.id.tvSignUpUserNameGreeting);
        tvSignUpUserNameText = findViewById(R.id.tvSignUpUserNameText);
        step2 = findViewById(R.id.step2);
        step3 = findViewById(R.id.step3);
        step4 = findViewById(R.id.step4);
        loading_animation = findViewById(R.id.animLoading);
        etSignUpEmail = findViewById(R.id.etSignUpEmail);
        etSignUpPhoneNumber = findViewById(R.id.etSignUpPhoneNumber);
        etSignUpPassword = findViewById(R.id.etSignUpPassword);
        etSignUpUserName = findViewById(R.id.etSignUpUserName);
        btnSignUpEmail = findViewById(R.id.btnSignUpEmail);
        btnSignUpPhoneNumber = findViewById(R.id.btnSignUpPhoneNUmber);
        btnSignUpPassword = findViewById(R.id.btnSignUpPassword);
        btnSignUpCreateAccount = findViewById(R.id.btnSignUpCreateAccount);
        //Setting button listener
        btnSignUpEmail.setOnClickListener(v -> {
            String email = etSignUpEmail.getText().toString().trim(); // Assuming etSignUpEmail is your EditText for email
            if (!email.isEmpty()) {
                step2.setVisibility(View.VISIBLE);
                tvSignUpEmailGreeting.setVisibility(View.INVISIBLE);
                tvSignUpEmailText.setVisibility(View.INVISIBLE);
                tvSignUpPhoneNumberGreeting.setVisibility(View.VISIBLE);
                tvSignUpPhoneNumberText.setVisibility(View.VISIBLE);
                etSignUpEmail.setVisibility(View.INVISIBLE);
                btnSignUpEmail.setVisibility(View.INVISIBLE);
                etSignUpPhoneNumber.setVisibility(View.VISIBLE);
                btnSignUpPhoneNumber.setVisibility(View.VISIBLE);
            } else {
                Toast.makeText(SignUp.this, "Enter Email", Toast.LENGTH_LONG).show();
            }
        });

        btnSignUpPhoneNumber.setOnClickListener(v -> {
            String phone = etSignUpPhoneNumber.getText().toString().trim();
            if (phone.length() != 10 ) {
                Toast.makeText(SignUp.this, "Enter 10 digit phone number", Toast.LENGTH_LONG).show();
            } else {
                step3.setVisibility(View.VISIBLE);
                tvSignUpPhoneNumberGreeting.setVisibility(View.INVISIBLE);
                tvSignUpPhoneNumberText.setVisibility(View.INVISIBLE);
                tvSignUpPasswordGreeting.setVisibility(View.VISIBLE);
                tvSignUpPasswordText.setVisibility(View.VISIBLE);
                etSignUpPhoneNumber.setVisibility(View.INVISIBLE);
                btnSignUpPhoneNumber.setVisibility(View.INVISIBLE);
                etSignUpPassword.setVisibility(View.VISIBLE);
                btnSignUpPassword.setVisibility(View.VISIBLE);
            }
        });
        btnSignUpPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etSignUpEmail.getText().toString().trim();
                String password = etSignUpPassword.getText().toString().trim();
                String phone = etSignUpPhoneNumber.getText().toString().trim();
                if (TextUtils.isEmpty(password) || password.length()<8) {
                    Toast.makeText(SignUp.this, "Password must be 8 characters long", Toast.LENGTH_SHORT).show();
                } else {
                    loading_animation.setVisibility(View.VISIBLE);
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (task.isSuccessful()) {
                                        Calendar calendar = Calendar.getInstance();
                                        int year = calendar.get(Calendar.YEAR);
                                        int month = calendar.get(Calendar.MONTH);
                                        userID = mAuth.getCurrentUser().getUid();
                                        DocumentReference documentReference = db.collection("users").document(userID);
                                        Map<String, Object> user = new HashMap<>();
                                        user.put("email", email);
                                        user.put("phone", phone);
                                        user.put("registrationMonth", month);
                                        user.put("registrationYear", year);
                                        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Toast.makeText(SignUp.this, "User Profile Is Created for:"+userID, Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                        startActivity(new Intent(SignUp.this, CreateProfile.class));
                                        finish();
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(SignUp.this, "Registration failed.", Toast.LENGTH_SHORT).show();
                                        loading_animation.setVisibility(View.INVISIBLE);
                                    }
                                }
                            });
                }
            }
        });

        overridePendingTransition(R.anim.activity_enter, R.anim.activity_exit);
    }

}
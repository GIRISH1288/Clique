package com.project.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUp extends AppCompatActivity {
    LottieAnimationView step2, step3, loading_animation;
    TextView tvSignUpEmailGreeting, tvSignUpEmailText, tvSignUpPasswordGreeting, tvSignUpPasswordText, tvSignUpUserNameGreeting, tvSignUpUserNameText;
    EditText etSignUpEmail, etSignUpPassword, etSignUpUserName;
    Button btnSignUpEmail, btnSignUpPassword, btnSignUpCreateAccount;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        //Initializing views
        mAuth = FirebaseAuth.getInstance();
        tvSignUpEmailGreeting = findViewById(R.id.tvSignUpEmailGreeting);
        tvSignUpEmailText = findViewById(R.id.tvSignUpEmailText);
        tvSignUpPasswordGreeting = findViewById(R.id.tvSignUpPasswordGreeting);
        tvSignUpPasswordText = findViewById(R.id.tvSignUpPasswordText);
        tvSignUpUserNameGreeting = findViewById(R.id.tvSignUpUserNameGreeting);
        tvSignUpUserNameText = findViewById(R.id.tvSignUpUserNameText);
        step2 = findViewById(R.id.step2);
        step3 = findViewById(R.id.step3);
        loading_animation = findViewById(R.id.animLoading);
        etSignUpEmail = findViewById(R.id.etSignUpEmail);
        etSignUpPassword = findViewById(R.id.etSignUpPassword);
        etSignUpUserName = findViewById(R.id.etSignUpUserName);
        btnSignUpEmail = findViewById(R.id.btnSignUpEmail);
        btnSignUpPassword = findViewById(R.id.btnSignUpPassword);
        btnSignUpCreateAccount = findViewById(R.id.btnSignUpCreateAccount);
        //Setting button listener
        btnSignUpEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etSignUpEmail.getText().toString().trim(); // Assuming etSignUpEmail is your EditText for email
                if (!email.isEmpty()) {
                    step2.setVisibility(View.VISIBLE);
                    tvSignUpEmailGreeting.setVisibility(View.INVISIBLE);
                    tvSignUpEmailText.setVisibility(View.INVISIBLE);
                    tvSignUpPasswordGreeting.setVisibility(View.VISIBLE);
                    tvSignUpPasswordText.setVisibility(View.VISIBLE);
                    etSignUpEmail.setVisibility(View.INVISIBLE);
                    btnSignUpEmail.setVisibility(View.INVISIBLE);
                    etSignUpPassword.setVisibility(View.VISIBLE);
                    btnSignUpPassword.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(SignUp.this, "Enter Email", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnSignUpPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = etSignUpPassword.getText().toString().trim();
                if (!password.isEmpty()) {
                    step3.setVisibility(View.VISIBLE);
                    tvSignUpPasswordGreeting.setVisibility(View.INVISIBLE);
                    tvSignUpPasswordText.setVisibility(View.INVISIBLE);
                    tvSignUpUserNameGreeting.setVisibility(View.VISIBLE);
                    tvSignUpUserNameText.setVisibility(View.VISIBLE);
                    etSignUpPassword.setVisibility(View.INVISIBLE);
                    btnSignUpPassword.setVisibility(View.INVISIBLE);
                    etSignUpUserName.setVisibility(View.VISIBLE);
                    btnSignUpCreateAccount.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(SignUp.this, "Enter Password", Toast.LENGTH_LONG).show();
                }
            }
        });
        btnSignUpCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etSignUpEmail.getText().toString().trim();
                String password = etSignUpPassword.getText().toString().trim();
                String username = etSignUpUserName.getText().toString().trim();

                if (TextUtils.isEmpty(username)) {
                    Toast.makeText(SignUp.this, "Enter Username", Toast.LENGTH_SHORT).show();
                }
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    //FirebaseUser user = mAuth.getCurrentUser();
                                    //updateUI(user);
                                    Toast.makeText(SignUp.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                    loading_animation.setVisibility(View.VISIBLE);
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            // Start the new activity
                                            startActivity(new Intent(SignUp.this, SignIn.class));
                                            // Finish the current activity
                                            finish();
                                        }
                                    }, 3000);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(SignUp.this, "Registration failed.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
        overridePendingTransition(R.anim.activity_enter, R.anim.activity_exit);
    }

}
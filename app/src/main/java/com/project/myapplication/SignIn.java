package com.project.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignIn extends AppCompatActivity {
    EditText etSignInEmail, etSignInPassword;
    Button btnLogIn;
    LottieAnimationView animLoadingSignIn;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        etSignInEmail = findViewById(R.id.etSignInEmail);
        etSignInPassword = findViewById(R.id.etSignInPassword);
        btnLogIn = findViewById(R.id.btnLogIn);
        animLoadingSignIn = findViewById(R.id.animLoadingSignIn);
        mAuth = FirebaseAuth.getInstance();
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etSignInEmail.getText().toString().trim();
                String password = etSignInPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(SignIn.this, "Enter Email", Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(SignIn.this, "Enter Password", Toast.LENGTH_SHORT).show();
                } else {
                    animLoadingSignIn.setVisibility(View.VISIBLE);
                    //authenticate user
                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                /*
                                     Sign in success, update UI with the signed-in user's information
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    updateUI(user);
                                    */
                                Toast.makeText(SignIn.this, "Logged In Successfully", Toast.LENGTH_SHORT).show();

                                startActivity(new Intent(SignIn.this, BottomNavigation.class));
                            } else {
                                Toast.makeText(SignIn.this, "Email or Username is Incorrect", Toast.LENGTH_SHORT).show();
                                animLoadingSignIn.setVisibility(View.INVISIBLE);
                            }
                        }
                    });
                }

            }
        });


        overridePendingTransition(R.anim.activity_enter, R.anim.activity_exit);
    }
}
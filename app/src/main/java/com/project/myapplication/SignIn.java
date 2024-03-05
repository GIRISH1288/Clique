package com.project.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.airbnb.lottie.LottieAnimationView;

public class SignIn extends AppCompatActivity {
    EditText etSignInEmail, etSignInPassword;
    Button btnLogIn;
    LottieAnimationView animLoadingSignIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        etSignInEmail = findViewById(R.id.etSignInEmail);
        etSignInPassword = findViewById(R.id.etSignInPassword);
        btnLogIn = findViewById(R.id.btnLogIn);


        overridePendingTransition(R.anim.activity_enter, R.anim.activity_exit);
    }
}
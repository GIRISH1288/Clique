package com.project.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

public class SignUp extends AppCompatActivity {
    LottieAnimationView step2, step3;
    TextView tvSignUpEmailGreeting, tvSignUpEmailText, tvSignUpPasswordGreeting, tvSignUpPasswordText, tvSignUpUserNameGreeting, tvSignUpUserNameText;
    EditText etSignUpEmail, etSignUpPassword, etSignUpUserName;
    Button btnSignUpEmail, btnSignUpPassword, btnSignUpUserName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        //Initializing views
        tvSignUpEmailGreeting = findViewById(R.id.tvSignUpEmailGreeting);
        tvSignUpEmailText = findViewById(R.id.tvSignUpEmailText);
        tvSignUpPasswordGreeting = findViewById(R.id.tvSignUpPasswordGreeting);
        tvSignUpPasswordText = findViewById(R.id.tvSignUpPasswordText);
        tvSignUpUserNameGreeting = findViewById(R.id.tvSignUpUserNameGreeting);
        tvSignUpUserNameText = findViewById(R.id.tvSignUpUserNameText);
        step2 = findViewById(R.id.step2);
        step3 = findViewById(R.id.step3);
        etSignUpEmail = findViewById(R.id.etSignUpEmail);
        etSignUpPassword = findViewById(R.id.etSignUpPassword);
        etSignUpUserName = findViewById(R.id.etSignUpUserName);
        btnSignUpEmail = findViewById(R.id.btnSignUpEmail);
        btnSignUpPassword = findViewById(R.id.btnSignUpPassword);
        btnSignUpUserName = findViewById(R.id.btnSignUpUserName);


        //Setting button listener
        btnSignUpEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                step2.setVisibility(View.VISIBLE);
                tvSignUpEmailGreeting.setVisibility(View.INVISIBLE);
                tvSignUpEmailText.setVisibility(View.INVISIBLE);
                tvSignUpPasswordGreeting.setVisibility(View.VISIBLE);
                tvSignUpPasswordText.setVisibility(View.VISIBLE);
                etSignUpEmail.setVisibility(View.INVISIBLE);
                btnSignUpEmail.setVisibility(View.INVISIBLE);
                etSignUpPassword.setVisibility(View.VISIBLE);
                btnSignUpPassword.setVisibility(View.VISIBLE);
            }
        });
        btnSignUpPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                step3.setVisibility(View.VISIBLE);
                tvSignUpPasswordGreeting.setVisibility(View.INVISIBLE);
                tvSignUpPasswordText.setVisibility(View.INVISIBLE);
                tvSignUpUserNameGreeting.setVisibility(View.VISIBLE);
                tvSignUpUserNameText.setVisibility(View.VISIBLE);
                etSignUpPassword.setVisibility(View.INVISIBLE);
                btnSignUpPassword.setVisibility(View.INVISIBLE);
                etSignUpUserName.setVisibility(View.VISIBLE);
                btnSignUpPassword.setVisibility(View.VISIBLE);
            }
        });
        overridePendingTransition(R.anim.activity_enter, R.anim.activity_exit);
    }
}
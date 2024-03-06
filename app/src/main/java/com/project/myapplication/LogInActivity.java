package com.project.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;

import com.airbnb.lottie.LottieAnimationView;

public class LogInActivity extends AppCompatActivity {
    private Button btnSignIn, btnSignUp, btnContGoogle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        btnContGoogle = findViewById(R.id.btnContinueWithGoogle);
        btnSignIn = findViewById(R.id.btnSignIn);
        btnSignUp = findViewById(R.id.btnSignUp);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogInActivity.this, SignUp.class));
            }
        });
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogInActivity.this, SignIn.class));
            }
        });
        overridePendingTransition(R.anim.activity_enter, R.anim.activity_exit);
    }
    /*
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    
     */
}
package com.project.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

    private static final int ANIMATION_DURATION = 1000;
    FirebaseAuth mAuth;// Adjust duration as needed

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        TextView tvClique = findViewById(R.id.tvClique);
        ImageView splashLogo = findViewById(R.id.splashLogo);
        mAuth = FirebaseAuth.getInstance();

        // Create fade-in animation
        AlphaAnimation fadeInAnimation = new AlphaAnimation(0.0f, 1.0f);
        fadeInAnimation.setDuration(ANIMATION_DURATION);
        fadeInAnimation.setRepeatMode(AlphaAnimation.REVERSE);
        fadeInAnimation.setRepeatCount(1); // Set repeat count to 1 for reverse animation

        // Apply fade-in animation
        tvClique.startAnimation(fadeInAnimation);
        splashLogo.startAnimation(fadeInAnimation);

        fadeInAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // Do nothing
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // Start the new activity when fade-out animation finishes
                startActivity(new Intent(SplashActivity.this, LogInActivity.class));
                finish(); // Finish the current activity
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // Do nothing
            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }
    private void updateUI(FirebaseUser currentUser) {
        // Implement UI update based on the user's authentication status
        if (currentUser != null) {
            TextView tvClique = findViewById(R.id.tvClique);
            ImageView splashLogo = findViewById(R.id.splashLogo);
            AlphaAnimation fadeInAnimation = new AlphaAnimation(0.0f, 1.0f);
            fadeInAnimation.setDuration(ANIMATION_DURATION);
            fadeInAnimation.setRepeatMode(AlphaAnimation.REVERSE);
            fadeInAnimation.setRepeatCount(1); // Set repeat count to 1 for reverse animation

            // Apply fade-in animation
            tvClique.startAnimation(fadeInAnimation);
            splashLogo.startAnimation(fadeInAnimation);
            fadeInAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    // Do nothing
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    // Start the new activity when fade-out animation finishes
                    startActivity(new Intent(SplashActivity.this, AfterLogin.class));
                    finish();// Finish the current activity
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                    // Do nothing
                }
            });
        } else {
            TextView tvClique = findViewById(R.id.tvClique);
            ImageView splashLogo = findViewById(R.id.splashLogo);
            AlphaAnimation fadeInAnimation = new AlphaAnimation(0.0f, 1.0f);
            fadeInAnimation.setDuration(ANIMATION_DURATION);
            fadeInAnimation.setRepeatMode(AlphaAnimation.REVERSE);
            fadeInAnimation.setRepeatCount(1); // Set repeat count to 1 for reverse animation

            // Apply fade-in animation
            tvClique.startAnimation(fadeInAnimation);
            splashLogo.startAnimation(fadeInAnimation);
            fadeInAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    // Do nothing
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    // Start the new activity when fade-out animation finishes
                    startActivity(new Intent(SplashActivity.this, LogInActivity.class));
                    finish(); // Finish the current activity
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                    // Do nothing
                }
            });
        }
    }
}

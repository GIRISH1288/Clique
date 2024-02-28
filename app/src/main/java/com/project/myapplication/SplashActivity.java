package com.project.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

    private static final int ANIMATION_DURATION = 2000; // Adjust duration as needed

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        TextView tvClique = findViewById(R.id.tvClique);
        ImageView splashLogo = findViewById(R.id.splashLogo);

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
}

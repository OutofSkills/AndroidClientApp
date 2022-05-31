package com.intelligentcarmanagement.carmanagementclientapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.intelligentcarmanagement.carmanagementclientapp.R;

public class SuccessActivity extends AppCompatActivity {

    private LottieAnimationView successAnimationView;
    private TextView successMessageTextView;
    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);

        successAnimationView = findViewById(R.id.ride_request_success_animation);
        successMessageTextView = findViewById(R.id.ride_request_success_text);
        backButton = findViewById(R.id.ride_request_success_back_button);

        Intent intent = getIntent();
        String message = intent.getStringExtra("message");

        showSuccessMessage(message);

        setEventListeners();
    }

    private void setEventListeners() {
        backButton.setOnClickListener(view -> {
            // Go back home
            startActivity(new Intent(SuccessActivity.this, HomeActivity.class));
            finish();
        });
    }

    private void showSuccessMessage(String message)
    {
        successAnimationView.playAnimation();
        successMessageTextView.setText(message);
    }
}
package com.intelligentcarmanagement.carmanagementclientapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.imageview.ShapeableImageView;
import com.intelligentcarmanagement.carmanagementclientapp.R;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "RegisterActivity";
    private static int RESULT_LOAD_IMAGE = 1;
    
    // Redirect button to login
    Button loginButtonRedirect;
    // Register data holders
    ShapeableImageView profileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Bind controls to views
        loginButtonRedirect = findViewById(R.id.registerLoginRedirect);
        profileImage = findViewById(R.id.register_item_image);

        // Set Event Listeners
        setEventListeners();
    }

    // Define here all the event listeners for the current activity
    private void setEventListeners()
    {
        // On click go back to login
        loginButtonRedirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

        // Open the gallery and load an image on click
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });
    }

    // Create an intent to open gallery window
    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, RESULT_LOAD_IMAGE);
    }

    // On image selected return its path
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == RESULT_LOAD_IMAGE){
            Uri imageUri = data.getData();
            setProfileImage(imageUri);
        }
    }

    // Set the image control's content
    private void setProfileImage(Uri profileImageURI)
    {
        // Load the image in the register view
        profileImage.setImageURI(profileImageURI);
    }
}
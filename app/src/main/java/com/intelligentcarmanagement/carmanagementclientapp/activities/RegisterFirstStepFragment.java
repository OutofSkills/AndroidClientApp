package com.intelligentcarmanagement.carmanagementclientapp.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.intelligentcarmanagement.carmanagementclientapp.R;

/**
 */
public class RegisterFirstStepFragment extends Fragment {

    private static final String TAG = "RegisterFirstFragment";

    // Activity launcher
    ActivityResultLauncher<Intent> mActivityResultLaunch = null;

    // Gallery intent request code
    private static final int RESULT_LOAD_IMAGE = 114;

    // Register account avatar
    private ShapeableImageView mUserAvatar;

    private ImageView mChooseAvatar;

    private TextInputLayout mFirstNameLayout, mLastNameLayout;

    private TextInputEditText mFirstNameText, mLastNameText;

    private Button mNextButton;

    private View mView;

    private boolean isFirstNameValid, isLastNameValid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_register_first_step, container, false);

        // Initiate activity launcher
        if(mActivityResultLaunch == null)
            initActivityLauncher();

        mNextButton = mView.findViewById(R.id.first_fragment_next_button);
        mUserAvatar = mView.findViewById(R.id.first_fragment_avatar);
        mChooseAvatar = mView.findViewById(R.id.first_fragment_choose_avatar);
        mFirstNameLayout = mView.findViewById(R.id.first_fragment_first_name_layout);
        mLastNameLayout = mView.findViewById(R.id.first_fragment_last_name_layout);
        mFirstNameText = mView.findViewById(R.id.first_fragment_first_name);
        mLastNameText = mView.findViewById(R.id.first_fragment_last_name);

        validateInputs();

        setEventListeners();

        return mView;
    }

    private void validateInputs()
    {
        mFirstNameText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                isFirstNameValid = isValidName(mFirstNameLayout, mFirstNameText);

                if(isFirstNameValid && isLastNameValid)
                    mNextButton.setEnabled(true);
                else
                    mNextButton.setEnabled(false);
            }
        });

        mLastNameText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                isLastNameValid = isValidName(mLastNameLayout, mLastNameText);

                if(isFirstNameValid && isLastNameValid)
                    mNextButton.setEnabled(true);
                else
                    mNextButton.setEnabled(false);
            }
        });
    }

    private boolean isValidName(TextInputLayout layout, TextInputEditText text)
    {
        boolean isValid = false;

        if (text.getText().toString().trim().isEmpty()) {
            layout.setErrorEnabled(false);
            isValid = false;
        } else {
            if (text.getText().toString().length() < 2) {
                layout.setError("At least 2 characters required.");
                layout.requestFocus();
                isValid = false;
            }
            else {
                layout.setErrorEnabled(false);
                isValid = true;
            }
        }
        return isValid;
    }

    private void setEventListeners()
    {
        mChooseAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });
    }

    protected Button getNextButton()
    {
        return mNextButton;
    }

    // Create an intent to open gallery window
    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        getActivity().setResult(RESULT_LOAD_IMAGE, gallery);
        mActivityResultLaunch.launch(gallery);
    }

    // Init the activity launcher and set the callbacks
    private void initActivityLauncher()
    {
        mActivityResultLaunch = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == AppCompatActivity.RESULT_OK){
                            Uri imageUri = result.getData().getData();
                            setProfileImage(imageUri);
                        }
                    }
                });
    }

    // Set the image control's content
    private void setProfileImage(Uri profileImageURI)
    {
        // Load the image in the register view
        mUserAvatar.setImageURI(profileImageURI);
    }
}
package com.intelligentcarmanagement.carmanagementclientapp.activities;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.intelligentcarmanagement.carmanagementclientapp.R;

/**
 */
public class RegisterSecondStepFragment extends Fragment {

    private static final String TAG = "RegisterSecondFragment";

    private TextInputLayout mUsernameLayout, mEmailLayout, mPasswordLayout, mConfirmPasswordLayout;

    private TextInputEditText mUsernameText, mEmailText, mPasswordText, mConfirmPasswordText;

    private Button mNextButton, mBackButton;

    private View view;

    private boolean isUsernameValid, isEmailValid, isPasswordValid, isConfirmPasswordValid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_register_second_step, container, false);

        mUsernameLayout = view.findViewById(R.id.second_fragment_username_layout);
        mUsernameText = view.findViewById(R.id.second_fragment_username);
        mEmailLayout = view.findViewById(R.id.second_fragment_email_layout);
        mEmailText = view.findViewById(R.id.second_fragment_email);
        mPasswordLayout = view.findViewById(R.id.second_fragment_password_layout);
        mPasswordText = view.findViewById(R.id.second_fragment_password);
        mConfirmPasswordLayout = view.findViewById(R.id.second_fragment_confirm_password_layout);
        mConfirmPasswordText = view.findViewById(R.id.second_fragment_confirm_password);
        mNextButton = view.findViewById(R.id.second_fragment_next_button);
        mBackButton = view.findViewById(R.id.second_fragment_back_button);

        validateInputs();

        return view;
    }

    private void validateInputs()
    {
        mUsernameText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                isUsernameValid = isValidUsername(mUsernameLayout, mUsernameText);

                if(isUsernameValid && isEmailValid && isPasswordValid && isConfirmPasswordValid)
                    mNextButton.setEnabled(true);
                else
                    mNextButton.setEnabled(false);
            }
        });

        mEmailText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                isEmailValid = isValidEmail(mEmailLayout, mEmailText);

                if(isUsernameValid && isEmailValid && isPasswordValid && isConfirmPasswordValid)
                    mNextButton.setEnabled(true);
                else
                    mNextButton.setEnabled(false);
            }
        });

        mPasswordText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                isPasswordValid = isValidPassword(mPasswordLayout, mPasswordText);

                if(isUsernameValid && isEmailValid && isPasswordValid && isConfirmPasswordValid)
                    mNextButton.setEnabled(true);
                else
                    mNextButton.setEnabled(false);
            }
        });

        mConfirmPasswordText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                isConfirmPasswordValid = mPasswordText.getText().toString().equals(mConfirmPasswordText.getText().toString());

                if(!isConfirmPasswordValid) {
                    mConfirmPasswordLayout.setError("Passwords are not matching.");
                    mConfirmPasswordLayout.requestFocus();
                }
                else{
                    mConfirmPasswordLayout.setErrorEnabled(false);
                }

                if(isUsernameValid && isEmailValid && isPasswordValid && isConfirmPasswordValid)
                    mNextButton.setEnabled(true);
                else
                    mNextButton.setEnabled(false);
            }
        });
    }

    private boolean isValidUsername(TextInputLayout layout, TextInputEditText text)
    {
        boolean isValid = false;

        if (text.getText().toString().trim().isEmpty()) {
            layout.setErrorEnabled(false);
            isValid = false;
        } else {
            if (text.getText().toString().length() < 3) {
                layout.setError("At least 3 characters required.");
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

    private boolean isValidEmail(TextInputLayout layout, TextInputEditText text)
    {
        boolean isValid = false;

        if (text.getText().toString().trim().isEmpty()) {
            layout.setErrorEnabled(false);
            isValid = false;
        } else {
            String emailId = text.getText().toString();
            Boolean  isValidFormat = android.util.Patterns.EMAIL_ADDRESS.matcher(emailId).matches();
            if (!isValidFormat) {
                layout.setError("Invalid Email address, ex: abc@example.com");
                layout.requestFocus();
                isValid = false;
            } else {
                layout.setErrorEnabled(false);
                isValid = true;
            }
        }
        return isValid;
    }

    private boolean isValidPassword(TextInputLayout layout, TextInputEditText text)
    {
        boolean isValid = false;

        if (text.getText().toString().trim().isEmpty()) {
            layout.setErrorEnabled(false);
            isValid = false;
        } else {
            if (text.getText().toString().length() < 5) {
                layout.setError("Password can't be shorter than 5 characters.");
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

    protected Button getNextButton() {
        return mNextButton;
    }

    protected Button getBackButton()
    {
        return mBackButton;
    }
}
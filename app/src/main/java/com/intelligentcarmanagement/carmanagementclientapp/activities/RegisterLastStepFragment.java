package com.intelligentcarmanagement.carmanagementclientapp.activities;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.intelligentcarmanagement.carmanagementclientapp.R;

/**
 */
public class RegisterLastStepFragment extends Fragment {
    private static final String TAG = "RegisterLastFragment";

    private TextInputLayout mPhoneNumberLayout;

    private TextInputEditText mPhoneNumberText;

    private Button mSubmitButton, mBackButton;

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_register_last_step, container, false);

        mPhoneNumberLayout = view.findViewById(R.id.last_fragment_phone_number_layout);
        mPhoneNumberText =  view.findViewById(R.id.last_fragment_phone_number);
        mSubmitButton = view.findViewById(R.id.last_fragment_submit_button);
        mBackButton = view.findViewById(R.id.last_fragment_back_button);

        validateInputs();

        return view;
    }

    private void validateInputs()
    {
        mPhoneNumberText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                boolean isPhoneNumberValid = isValidPhoneNumber(mPhoneNumberLayout, mPhoneNumberText);

                if(isPhoneNumberValid)
                    mSubmitButton.setEnabled(true);
                else
                    mSubmitButton.setEnabled(false);
            }
        });
    }

    private boolean isValidPhoneNumber(TextInputLayout layout, TextInputEditText text)
    {
        boolean isValid = false;

        if (text.getText().toString().trim().isEmpty()) {
            layout.setErrorEnabled(false);
            isValid = false;
        } else {
            String phoneNumber = text.getText().toString();
            Boolean  isValidFormat = Patterns.PHONE.matcher(phoneNumber).matches();
            if (!isValidFormat) {
                layout.setError("Invalid Phone number, ex: 0767 77 777");
                layout.requestFocus();
                isValid = false;
            } else {
                layout.setErrorEnabled(false);
                isValid = true;
            }
        }
        return isValid;
    }

    protected Button getSubmitButton() {
        return mSubmitButton;
    }

    protected Button getBackButton()
    {
        return mBackButton;
    }
}
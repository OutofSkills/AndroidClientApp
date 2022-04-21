package com.intelligentcarmanagement.carmanagementclientapp.activities;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.intelligentcarmanagement.carmanagementclientapp.R;

/**
 */
public class RegisterLastStepFragment extends Fragment {
    private static final String TAG = "RegisterLastFragment";

    private Button mSubmitButton, mBackButton;

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_register_last_step, container, false);
        if(mSubmitButton == null)
            mSubmitButton = view.findViewById(R.id.last_fragment_submit_button);
        if(mBackButton == null)
            mBackButton = view.findViewById(R.id.last_fragment_back_button);

        validateInputs();

        return view;
    }

    private void validateInputs()
    {
        // TODO: Validate the inputs here
        // then enable the button based on the validation result
        mSubmitButton.setEnabled(false);
    }

    protected Button getSubmitButton() {
        return mSubmitButton;
    }

    protected Button getBackButton()
    {
        return mBackButton;
    }
}
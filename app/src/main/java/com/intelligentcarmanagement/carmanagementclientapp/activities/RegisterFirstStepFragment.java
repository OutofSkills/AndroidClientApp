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
public class RegisterFirstStepFragment extends Fragment {

    private static final String TAG = "RegisterFirstFragment";

    private Button mNextButton;

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_register_first_step, container, false);
        mNextButton = view.findViewById(R.id.first_fragment_next_button);

        validateInputs();

        return view;
    }

    private void validateInputs()
    {
        // TODO: Validate the inputs here
        // then enable the button based on the validation result
        mNextButton.setEnabled(true);
    }

    protected Button getNextButton()
    {
        return mNextButton;
    }
}
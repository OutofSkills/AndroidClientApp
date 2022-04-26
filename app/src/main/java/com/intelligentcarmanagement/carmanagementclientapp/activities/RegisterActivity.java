package com.intelligentcarmanagement.carmanagementclientapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.intelligentcarmanagement.carmanagementclientapp.R;
import com.intelligentcarmanagement.carmanagementclientapp.adapters.ErrorsRecyclerViewAdapter;
import com.intelligentcarmanagement.carmanagementclientapp.models.RegisterRequest;
import com.intelligentcarmanagement.carmanagementclientapp.utils.RequestState;
import com.intelligentcarmanagement.carmanagementclientapp.viewmodels.RegisterViewModel;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "RegisterActivity";

    private static final String FIRST_FRAGMENT_TAG = "FirstFragment";
    private static final String SECOND_FRAGMENT_TAG = "SecondFragment";
    private static final String LAST_FRAGMENT_TAG = "LastFragment";
    private static final String SUCCESS_FRAGMENT_TAG = "SuccessFragment";

    // Redirect button to login
    private Button loginButtonRedirect;

    private RegisterFirstStepFragment defaultFragment;
    private RegisterSecondStepFragment secondStepFragment;
    private RegisterLastStepFragment lastStepFragment;
    private RegisterSuccessFragment successFragment;

    private FragmentManager fragmentManager;

    // Errors recycler view
    private RecyclerView recyclerView;
    private ErrorsRecyclerViewAdapter adapter;

    // New user object
    private RegisterRequest user = new RegisterRequest();

    private RegisterViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        loginButtonRedirect = findViewById(R.id.register_redirect_to_login);
        recyclerView = findViewById(R.id.register_errors_recycler_view);

        viewModel = new ViewModelProvider(this).get(RegisterViewModel.class);

        fragmentManager = getSupportFragmentManager();
        defaultFragment = new RegisterFirstStepFragment();
        secondStepFragment = new RegisterSecondStepFragment();
        lastStepFragment = new RegisterLastStepFragment();
        successFragment = new RegisterSuccessFragment();

        // Set the first(default) fragment
        if(findViewById(R.id.register_fragment_container) != null)
        {
            setDefaultFragmentsState();
        }

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
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if(defaultFragment.getNextButton() != null)
            defaultFragment.getNextButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClick: Avatar: " + defaultFragment.getAvatar());
                    Log.d(TAG, "onClick: Names: " + defaultFragment.getFirstName() + " " + defaultFragment.getLastName());

                    user.setAvatar(defaultFragment.getAvatar());
                    user.setFirstName(defaultFragment.getFirstName());
                    user.setLastName(defaultFragment.getLastName());

                    switchToSecondFragment();
                }
            });

        if(secondStepFragment.getNextButton() != null)
            secondStepFragment.getNextButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClick: Click" + secondStepFragment.getUsername() + " " + secondStepFragment.getEmail() + " " +  secondStepFragment.getPassword() + " "
                            + secondStepFragment.getConfirmPassword());

                    user.setUserName(secondStepFragment.getUsername());
                    user.setEmail(secondStepFragment.getEmail());
                    user.setPassword(secondStepFragment.getPassword());
                    user.setConfirmPassword(secondStepFragment.getConfirmPassword());

                    switchToLastFragment();
                }
            });

        if(secondStepFragment.getBackButton() != null)
            secondStepFragment.getBackButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClick: Click");
                    switchToFirstFragment();
                }
            });

        if(lastStepFragment.getBackButton() != null)
            lastStepFragment.getBackButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClick: Click");
                    switchToSecondFragment();
                }
            });

        if(lastStepFragment.getSubmitButton() != null)
            lastStepFragment.getSubmitButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClick: Data: " + lastStepFragment.getPhoneNumber());

                    user.setPhoneNumber(lastStepFragment.getPhoneNumber());

                    // Submit the register data
                    viewModel.register(user);
                    viewModel.getRegisterState().observe(RegisterActivity.this, new Observer<RequestState>() {
                        @Override
                        public void onChanged(RequestState requestState) {
                            switch (requestState){
                                case START:
                                    lastStepFragment.setProgressBarEnabled(true);
                                    lastStepFragment.getSubmitButton().setEnabled(false);
                                    break;
                                case SUCCESS:
                                    lastStepFragment.setProgressBarEnabled(false);
                                    switchToSuccessFragment();
                                    break;
                                case ERROR:
                                    lastStepFragment.setProgressBarEnabled(false);
                                    lastStepFragment.getSubmitButton().setEnabled(true);
                                    viewModel.getErrorsMutableLiveData().observe(RegisterActivity.this, new Observer<String[]>() {
                                        @Override
                                        public void onChanged(String[] strings) {
                                            adapter = new ErrorsRecyclerViewAdapter(RegisterActivity.this, strings);
                                            recyclerView.setAdapter(adapter);
                                            recyclerView.setLayoutManager(new LinearLayoutManager(RegisterActivity.this));
                                        }
                                    });
                                    break;
                            }
                        }
                    });
                }
            });
    }

    private void setDefaultFragmentsState()
    {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.register_fragment_container, defaultFragment, FIRST_FRAGMENT_TAG)
                .add(R.id.register_fragment_container, secondStepFragment, SECOND_FRAGMENT_TAG)
                .add(R.id.register_fragment_container, lastStepFragment, LAST_FRAGMENT_TAG)
                .add(R.id.register_fragment_container, successFragment, SUCCESS_FRAGMENT_TAG)
                .setReorderingAllowed(true)
                .commit();


        fragmentManager
                .beginTransaction()
                .hide(secondStepFragment)
                .hide(lastStepFragment)
                .hide(successFragment)
                .commit();
    }

    private void switchToFirstFragment()
    {
        fragmentManager
                .beginTransaction()
                .hide(secondStepFragment)
                .hide(lastStepFragment)
                .hide(successFragment)
                .commit();

        if(fragmentManager.findFragmentByTag(FIRST_FRAGMENT_TAG) == null)
            fragmentManager
                .beginTransaction()
                .add(R.id.register_fragment_container, defaultFragment, FIRST_FRAGMENT_TAG)
                .setReorderingAllowed(true)
                .commit();

        fragmentManager
                .beginTransaction()
                .show(defaultFragment)
                .commit();
    }

    private void switchToSecondFragment()
    {
        fragmentManager
                .beginTransaction()
                .hide(defaultFragment)
                .hide(lastStepFragment)
                .hide(successFragment)
                .commit();

        if(fragmentManager.findFragmentByTag(SECOND_FRAGMENT_TAG) == null)
            fragmentManager
                    .beginTransaction()
                    .add(R.id.register_fragment_container, secondStepFragment, SECOND_FRAGMENT_TAG)
                    .setReorderingAllowed(true)
                    .commit();

        fragmentManager
                .beginTransaction()
                .show(secondStepFragment)
                .commit();
    }

    private void switchToLastFragment()
    {
        fragmentManager
                .beginTransaction()
                .hide(defaultFragment)
                .hide(secondStepFragment)
                .hide(successFragment)
                .commit();

        if(fragmentManager.findFragmentByTag(LAST_FRAGMENT_TAG) == null)
            fragmentManager
                    .beginTransaction()
                    .add(R.id.register_fragment_container, lastStepFragment, LAST_FRAGMENT_TAG)
                    .setReorderingAllowed(true)
                    .commit();

        fragmentManager
                .beginTransaction()
                .show(lastStepFragment)
                .commit();
    }

    private void switchToSuccessFragment()
    {
        fragmentManager
                .beginTransaction()
                .hide(defaultFragment)
                .hide(secondStepFragment)
                .hide(lastStepFragment)
                .commit();

        if(fragmentManager.findFragmentByTag(SUCCESS_FRAGMENT_TAG) == null)
            fragmentManager
                    .beginTransaction()
                    .add(R.id.register_fragment_container, successFragment, SUCCESS_FRAGMENT_TAG)
                    .setReorderingAllowed(true)
                    .commit();

        fragmentManager
                .beginTransaction()
                .show(successFragment)
                .commit();
    }
}
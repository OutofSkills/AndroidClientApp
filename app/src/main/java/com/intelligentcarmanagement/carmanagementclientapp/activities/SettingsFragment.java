package com.intelligentcarmanagement.carmanagementclientapp.activities;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.intelligentcarmanagement.carmanagementclientapp.R;

public class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }
}

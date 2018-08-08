package com.example.android.habits.preferences;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceScreen;

import com.example.android.habits.R;

public class PrefFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener, Preference.OnPreferenceChangeListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.preferences);

        SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();
        PreferenceScreen prefScreen = getPreferenceScreen();
        int count = prefScreen.getPreferenceCount();

        for (int i = 0; i < count; i++) {
            Preference p = prefScreen.getPreference(i);
            if (!(p instanceof CheckBoxPreference)) {
                String value = sharedPreferences.getString(p.getKey(), "");
                setPreferenceSummary(p, value);
            }
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference preference = findPreference(key);
        if (preference != null && !(preference instanceof CheckBoxPreference)){
            String value = sharedPreferences.getString(preference.getKey(), "");
            setPreferenceSummary(preference, value);
        }
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {

       /*
        Toast error = Toast.makeText(getContext(), "Username start with capital letter", Toast.LENGTH_SHORT);

        String usernameKey = getString(R.string.pref_username_key);
        // Controllo quale delle ********************
        if (preference.getKey().equals(usernameKey)) {
            String username = (String) newValue;
            try {
                if (true){
                    // if capital letter
                } else {
                    // if not capital letter
                    error.show();
                }
            } catch (NumberFormatException nfe) {
                // wrong input
                error.show();
                return false;
            }
        }
        */
        return true;
    }


    private void setPreferenceSummary(Preference preference, String value){
        if (preference instanceof ListPreference) {
            ListPreference listPreference = (ListPreference) preference;

            int index = listPreference.findIndexOfValue(value);

            if (index >= 0) {
                listPreference.setSummary(listPreference.getEntries()[index]);
            }
        } else if (preference instanceof EditTextPreference) {
            preference.setSummary(value);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }
}


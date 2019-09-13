package com.bing.appwidgetdemo;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;

import java.util.prefs.PreferenceChangeEvent;
import java.util.prefs.PreferenceChangeListener;

public class SettingFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    EditTextPreference editTextPreference;
    ListPreference listPreference;
    EditTextPreference editTextPreference2;
    SharedPreferences pref;
    FloatingActionButton fab;

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);

    }

    @Override
    public void onResume() {
        editTextPreference.setSummary(editTextPreference.getText());
        listPreference.setSummary(listPreference.getValue());
        editTextPreference2.setSummary(editTextPreference2.getText());
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        super.onResume();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.setting_preference);
        //初始化两个控件
        editTextPreference =(EditTextPreference)getPreferenceScreen().findPreference("text");
        editTextPreference2 = (EditTextPreference)getPreferenceScreen().findPreference("text_size");
        listPreference = (ListPreference)getPreferenceScreen().findPreference("text_color");
        pref = PreferenceManager.getDefaultSharedPreferences(getContext());
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        switch (key){
            case "text":editTextPreference.setSummary(editTextPreference.getText());
            break;
            case "text_color":listPreference.setSummary(listPreference.getValue());
            break;
            case "text_size":editTextPreference2.setSummary(editTextPreference2.getText()+"sp");
            break;
        }
    }
}

package com.bing.appwidgetdemo;

import android.app.AlertDialog;
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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import java.util.prefs.PreferenceChangeEvent;
import java.util.prefs.PreferenceChangeListener;

public class SettingFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    //文字设置
    EditTextPreference textInput;
    EditTextPreference textSizeInput;
    ListPreference textColor;
    SharedPreferences pref;
    //倒计时设置
    Preference chooseDate;
    EditTextPreference timerTextInput;
    EditTextPreference timerTextSizeInput;
    ListPreference timerTextColor;
    String date;

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);

    }

    @Override
    public void onResume() {
        textInput.setSummary(textInput.getText());
        textColor.setSummary(textColor.getValue());
        textSizeInput.setSummary(textSizeInput.getText());
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        super.onResume();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.setting_preference);
        //初始化控件
        textInput =(EditTextPreference)getPreferenceScreen().findPreference("text");
        textSizeInput = (EditTextPreference)getPreferenceScreen().findPreference("text_size");
        textColor = (ListPreference)getPreferenceScreen().findPreference("text_color");
        chooseDate = getPreferenceScreen().findPreference("choose_date");
        timerTextColor = (ListPreference) getPreferenceScreen().findPreference("timer_text_color");
        timerTextInput = (EditTextPreference)getPreferenceScreen().findPreference("timer_text");
        timerTextSizeInput = (EditTextPreference)getPreferenceScreen().findPreference("timer_text_size");
        pref = PreferenceManager.getDefaultSharedPreferences(getContext());

        chooseDate.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setView(R.layout.preference_date_picker);
                builder.setTitle("选择起始日期");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        View view = LayoutInflater.from(getContext()).inflate(R.layout.preference_date_picker,null);
                        DatePicker datePicker = view.findViewById(R.id.date_picker);
                        datePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
                            @Override
                            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                date = year+" "+monthOfYear+" "+dayOfMonth;
                            }
                        });

                    }
                });
                builder.show();
                return false;
            }
        });
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        switch (key){
            case "text":textInput.setSummary(textInput.getText());
            break;
            case "text_color":textColor.setSummary(textColor.getValue());
            break;
            case "text_size":textSizeInput.setSummary(textSizeInput.getText()+"sp");
            break;
            case "timer_text":timerTextInput.setSummary(timerTextInput.getText());
            break;
            case "timer_text_size":timerTextSizeInput.setSummary(timerTextSizeInput.getText()+"sp");
            break;
            case "timer_text_color":timerTextColor.setSummary(timerTextColor.getValue());
            break;
            case "choose_date":chooseDate.setSummary(date);
            break;
        }
    }
}

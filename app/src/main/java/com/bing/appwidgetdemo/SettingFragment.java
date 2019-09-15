package com.bing.appwidgetdemo;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;
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
    //关于作者
    Preference aboutAuthor;

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        textInput.setSummary(textInput.getText());
        textColor.setSummary(textColor.getValue());
        textSizeInput.setSummary(textSizeInput.getText());
        timerTextColor.setSummary(timerTextColor.getValue());
        timerTextInput.setSummary(timerTextInput.getText());
        timerTextSizeInput.setSummary(timerTextSizeInput.getText());
        chooseDate.setSummary(date);
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.setting_preference);
        //初始化控件
        aboutAuthor =getPreferenceScreen().findPreference("about_author");
        textInput =(EditTextPreference)getPreferenceScreen().findPreference("text");
        textSizeInput = (EditTextPreference)getPreferenceScreen().findPreference("text_size");
        textColor = (ListPreference)getPreferenceScreen().findPreference("text_color");

        chooseDate = getPreferenceScreen().findPreference("choose_date");
        timerTextColor = (ListPreference) getPreferenceScreen().findPreference("timer_text_color");
        timerTextInput = (EditTextPreference)getPreferenceScreen().findPreference("timer_text");
        timerTextSizeInput = (EditTextPreference)getPreferenceScreen().findPreference("timer_text_size");
       String string =  getContext().getSharedPreferences("data",Context.MODE_PRIVATE).getString("date",null);
        chooseDate.setSummary(string);

        pref = PreferenceManager.getDefaultSharedPreferences(getContext());

        chooseDate.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog dialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        int temp = month+1;
                       date = year+"-"+temp+"-"+dayOfMonth;
                       SharedPreferences.Editor editor = getContext().getSharedPreferences("data", Context.MODE_PRIVATE).edit();
                       editor.putString("date",date);
                       editor.apply();
                        chooseDate.setSummary(date);
                    }
                },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONDAY),
                        calendar.get(Calendar.DAY_OF_MONTH));
                dialog.show();
                return true;
            }
        });

        aboutAuthor.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent intent  = new Intent(getContext(),IntroActivity.class);
                startActivity(intent);
                return true;
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

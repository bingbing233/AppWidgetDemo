package com.bing.appwidgetdemo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton fab;
    @Override
    protected void onResume() {
        super.onResume();
       saveData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveData();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getFragmentManager().beginTransaction().replace(R.id.frame,new SettingFragment()).commit();
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.appwidget.action.APPWIDGET_UPDATE");
                sendBroadcast(intent);
            }
        });
        saveData();
    }

    private void saveData(){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        String text = pref.getString("text",null);
        String color = pref.getString("text_color",null);
        String size = pref.getString("text_size",null );
        SharedPreferences.Editor editor = getSharedPreferences("data",MODE_PRIVATE).edit();
        editor.putString("text",text);
        editor.putString("text_color",color);
        editor.putString("text_size",size);
        editor.apply();
    }
}
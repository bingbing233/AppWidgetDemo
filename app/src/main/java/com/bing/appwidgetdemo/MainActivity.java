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
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.widget.Toolbar;

import java.text.SimpleDateFormat;
import java.util.Date;

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
                Log.d("MainActivity",getSharedPreferences("data",MODE_PRIVATE).getString("date",null));
                Intent intent = new Intent("android.appwidget.action.APPWIDGET_UPDATE");
                sendBroadcast(intent);
            }
        });
        saveData();
    }

    private void saveData(){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);

        //文本设置数据
        String text = pref.getString("text",null);
        String textColor= pref.getString("text_color",null);
        String textSize = pref.getString("text_size",null );
        //正计时设置数据
        String timerText = pref.getString("timer_text",null);
        String timerColor = pref.getString("timer_text_color",null);
        String timmerTextSize = pref.getString("timer_text_size",null);


        SharedPreferences.Editor editor = getSharedPreferences("data",MODE_PRIVATE).edit();
        //存储设置数据
        editor.putString("text",text);
        editor.putString("text_color",textColor);
        editor.putString("text_size",textSize);
        editor.putString("timer_text",timerText);
        editor.putString("timer_text_color",timerColor);
        editor.putString("timer_text_size",timmerTextSize);

        editor.apply();
    }
}
package com.bing.appwidgetdemo;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.RemoteViews;
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
                saveData();
                int [] ids = AppWidgetManager.getInstance(getApplication()).
                        getAppWidgetIds(new ComponentName(getApplication(),TextAppWidget.class));
                TextAppWidget textAppWidget = new TextAppWidget();
                textAppWidget.onUpdate(MainActivity.this,AppWidgetManager.getInstance(MainActivity.this),ids);

                int [] ids2 = AppWidgetManager.getInstance(getApplication()).
                        getAppWidgetIds(new ComponentName(getApplication(),TimerAppWidget.class));
                TimerAppWidget timerAppWidget= new TimerAppWidget();
                timerAppWidget.onUpdate(MainActivity.this,AppWidgetManager.getInstance(MainActivity.this),ids2);

                Toast.makeText(MainActivity.this,"保存成功",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveData(){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);

        //文本设置数据
        String text = pref.getString("text","hello world");
        String textColor= pref.getString("text_color","白色");
        String textSize = pref.getString("text_size","20" );
        //正计时设置数据
        String timerText = pref.getString("timer_text","hello world");
        String timerColor = pref.getString("timer_text_color","白色");
        String timmerTextSize = pref.getString("timer_text_size","20");


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
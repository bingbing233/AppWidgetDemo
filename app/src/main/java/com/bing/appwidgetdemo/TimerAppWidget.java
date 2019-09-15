package com.bing.appwidgetdemo;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.util.TypedValue;
import android.widget.RemoteViews;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Implementation of App Widget functionality.
 */
public class TimerAppWidget extends AppWidgetProvider {
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        SharedPreferences preferences = context.getSharedPreferences("data",Context.MODE_PRIVATE);
        String timerText = preferences.getString("timer_text",null);
        String timerTextcolor = preferences.getString("timer_text_color",null);
        String timerTextSize = preferences.getString("timer_text_size",null);
        String date = preferences.getString("date",null);
        String days = calculateDate(date);
        CharSequence widgetText = timerText+days+"天";
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.timer_app_widget);

        views.setTextViewText(R.id.timer, widgetText);
        views.setTextViewTextSize(R.id.timer, TypedValue.COMPLEX_UNIT_SP,Integer.parseInt(timerTextSize));
        if(timerTextcolor.equals("黑色")){
            views.setTextColor(R.id.timer, Color.BLACK);
        }
        if(timerTextcolor.equals("白色")) {
            views.setTextColor(R.id.timer,Color.WHITE);
        }
        if(timerTextcolor.equals("粉色")){
            views.setTextColor(R.id.timer,Color.parseColor("#FA7298"));
        }

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    public static String calculateDate(String date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        int  year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        String today = year+"-"+month+"-"+dayOfMonth;
        try {
            Date d1 = format.parse(today);//后的时间
            Date d2 = format.parse(date); //前的时间
            Long diff = d1.getTime() - d2.getTime(); //两时间差，精确到毫秒

            Long day = diff / (1000 * 60 * 60 * 24);   //以天数为单位取整

            Log.e("tag","day =" +day);
            return Math.abs(day)+"";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }
}


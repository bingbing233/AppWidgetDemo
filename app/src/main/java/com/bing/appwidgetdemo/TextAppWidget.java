package com.bing.appwidgetdemo;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.RemoteViews;
import android.widget.Toast;

/**
 * Implementation of App Widget functionality.
 */
public class TextAppWidget extends AppWidgetProvider {

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        SharedPreferences preferences = context.getSharedPreferences("data",Context.MODE_PRIVATE);
        String text = preferences.getString("text",null);
        String color = preferences.getString("text_color",null);
        String size = preferences.getString("text_size",null);
        CharSequence widgetText = text;
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.text_app_widget);

        //设置属性
        views.setTextViewText(R.id.appwidget_text, widgetText);
        views.setTextViewTextSize(R.id.appwidget_text, TypedValue.COMPLEX_UNIT_SP,Integer.parseInt(size));
        if(color.equals("黑色")){
            views.setTextColor(R.id.appwidget_text,Color.BLACK);
        }
        if(color.equals("白色")) {
            views.setTextColor(R.id.appwidget_text,Color.WHITE);
        }
        if(color.equals("粉色")){
            views.setTextColor(R.id.appwidget_text,Color.parseColor("#FA7298"));
        }
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            /*Intent intent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.text_app_widget);
            remoteViews.setOnClickPendingIntent(R.id.appwidget_text, pendingIntent);*/
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
}


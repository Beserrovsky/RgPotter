package com.beserrovsky.rgpotter.widgets;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.beserrovsky.rgpotter.R;

public class HouseWidgetProvider extends AppWidgetProvider {

//    @Override
//    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
//        super.onUpdate(context, appWidgetManager, appWidgetIds);
//
//        final int amount = appWidgetIds.length;
//        for (int i = 0; i < amount; i++) {
//            // TODO: Intent intent = new Intent(context, ACTIVITY);
//
//            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
//
//            // TODO: RemoteViews views = new RemoteViews(context.getPackageName(), LAYOUT_ID);
//            views.setOnClickPendingIntent(R.id.imageView3, pendingIntent);
//            appWidgetManager.updateAppWidget(appWidgetIds[i], views);
//        }
//    }
}

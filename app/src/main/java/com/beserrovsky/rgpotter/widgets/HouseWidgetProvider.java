package com.beserrovsky.rgpotter.widgets;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.beserrovsky.rgpotter.MainActivity;
import com.beserrovsky.rgpotter.R;

public class HouseWidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        for (int appWidgetId : appWidgetIds) {
            Intent intent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_house_gryffindor);
            views.setOnClickPendingIntent(R.id.widget_gryffindor_image, pendingIntent);
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }
}

package uca.es.estudiantes;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RemoteViews;
public class Widget extends AppWidgetProvider {
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {
        final int N = appWidgetIds.length;
        for (int i= 0; i<N; i++) {
            int appWidgetId = appWidgetIds[i];
            Intent intent = new Intent(context, TodosActivity.class);
            PendingIntent pendingIntent =
                    PendingIntent.getActivity(context,
                            0, intent,
                            0);
            RemoteViews views = new RemoteViews(context.getPackageName(),
                    R.layout.activity_widget);
            views.setOnClickPendingIntent(R.id.widget, pendingIntent);
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }
}


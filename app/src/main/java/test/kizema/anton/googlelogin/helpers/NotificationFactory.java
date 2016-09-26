package test.kizema.anton.googlelogin.helpers;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;

import test.kizema.anton.googlelogin.activity.ContentActivity;
import test.kizema.anton.googlelogin.R;

public class NotificationFactory {

    private static final int COLOR = Color.parseColor("#8A2BE2");
    private static final int RAND_NOTIF_ID = 39;

    private static NotificationManager mNotificationManager;
    private static Context appContext;

    public static void init(Context context) {
        appContext = context;
        mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public static void send(int rand) {

        String notificationMessage = "Rand num : " + rand;
        String title = "Rand service";

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(appContext)
                        .setSmallIcon(R.drawable.ic_notif_random)
                        .setContentTitle(title)
                        .setContentText(notificationMessage)
                        .setAutoCancel(true)
                        .setTicker(notificationMessage)
                        .setWhen(System.currentTimeMillis())
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(notificationMessage))
                        .setColor(COLOR)
                        .setDefaults(Notification.DEFAULT_ALL);

        Intent resultIntent = new Intent(appContext, ContentActivity.class);

        PendingIntent resultPendingIntent = PendingIntent.getActivities(appContext, 321,
                new Intent[]{resultIntent}, PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder.setContentIntent(resultPendingIntent);

        Notification notification = mBuilder.build();

        mNotificationManager.notify(RAND_NOTIF_ID, notification);
    }

    public static void cancelNotification(){
        mNotificationManager.cancel(RAND_NOTIF_ID);
    }
}

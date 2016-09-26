package test.kizema.anton.googlelogin.helpers;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;

import test.kizema.anton.googlelogin.ContentActivity;
import test.kizema.anton.googlelogin.R;

public class NotificationFactory {

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
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(title)
                        .setContentText(notificationMessage)
                        .setAutoCancel(true)
                        .setTicker(title + "\n" + notificationMessage)
                        .setWhen(System.currentTimeMillis())
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(notificationMessage))
                        .setColor(Color.parseColor("#8A2BE2"))
                        .setDefaults(Notification.DEFAULT_ALL);

        Intent resultIntent = new Intent(appContext, ContentActivity.class);
        resultIntent.putExtra(Constants.RAND_NUM, rand);

        PendingIntent resultPendingIntent;

        Intent backIntent = new Intent(appContext, ContentActivity.class);

        resultPendingIntent = PendingIntent.getActivities(appContext, 321,
                new Intent[]{backIntent, resultIntent}, PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder.setContentIntent(resultPendingIntent);

        Notification notification = mBuilder.build();

        mNotificationManager.notify(RAND_NOTIF_ID, notification);
    }

    public static void cancelNotification(){
        mNotificationManager.cancel(RAND_NOTIF_ID);
    }
}

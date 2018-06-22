package com.example.eldho.notification_udacity_sample;
/**Three functions 1.contentIntent 2. largeIcon 3.remindUserBecauseCharging*/

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

public class NotificationUtils {
    private static final int WATER_REMINDER_PENDING_INTENT_ID = 3417;
    public static final String  WATER_REMINDER_NOTIFICATION_CHANNEL_ID="reminder_notification_channel";
    public static final int WATER_REMINDER_NOTIFICATION_ID =1234;

    /**Allows to relaunch the app when we click the notification*/
    private static PendingIntent contentIntent(Context context){

        Intent startActivityIntent = new Intent(context,MainActivity.class);
        return PendingIntent.getActivity(context,
                WATER_REMINDER_PENDING_INTENT_ID,
                startActivityIntent,
                //FLAG_UPDATE_CURRENT -> keeps this instance valid and just updates the extra data associated with it coming from new intent
                PendingIntent.FLAG_UPDATE_CURRENT);
    }
    //Helps to create a bitmap image shown in the Notification
    private static Bitmap largeIcon (Context context){
        /**Decode an image from the resources to an bitmap image*/
        Resources res = context.getResources();
        Bitmap largeIcon = BitmapFactory.decodeResource(res,R.drawable.android);
        return largeIcon;
    }

    //This method is responsible for creating the notification and notification channel in which the notification belongs to and displaying it
    public static void remindUserBecauseCharging(Context context){
        /**Get reference to notification manager*/
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        /**From Oreo we need to display notifications in the notification channel*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    WATER_REMINDER_NOTIFICATION_CHANNEL_ID, //String ID
                    context.getString(R.string.main_notification_channel_name), //Name for the channel
                    NotificationManager.IMPORTANCE_HIGH); //Importance for the notification , In high we get headsUp notification
            notificationManager.createNotificationChannel(mChannel);
        }
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context,WATER_REMINDER_NOTIFICATION_CHANNEL_ID)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setSmallIcon(R.drawable.android)
                .setLargeIcon(largeIcon(context))
                .setContentTitle(context.getString(R.string.charging_reminder_notification_title)) // Title
                .setContentText(context.getString(R.string.charging_reminder_notification_body)) //Text
                // check different styles ref: https://developer.android.com/training/notify-user/expanded
                .setStyle(new NotificationCompat.BigTextStyle().bigText(
                        context.getString(R.string.charging_reminder_notification_body)))
                .setDefaults(Notification.DEFAULT_VIBRATE) // needed to add vibration permission on the manifest
                .setContentIntent(contentIntent(context)) //pending Intent (check its fn)
                .setAutoCancel(true); //Notification will go away when user clicks on it
        /**this will give heads up notification on versions below Oreo*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
                && Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            notificationBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
        }
        //WATER_REMINDER_NOTIFICATION_ID -> this ID can be used if the notification have to ba cancelled
        notificationManager.notify(WATER_REMINDER_NOTIFICATION_ID,notificationBuilder.build());
    }
}

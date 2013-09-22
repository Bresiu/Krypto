package com.bresiu.krypto.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.bresiu.krypto.LoginActivity;
import com.bresiu.krypto.R;

public class CreateNotification {
    // TODO
    public static void createNotification(Context context, String originatingAddress, String message) {
        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(ns);

        int icon = R.drawable.ic_launcher;
        CharSequence tickerText = "New Krypto Message";
        long when = System.currentTimeMillis();

        Notification notification = new Notification(icon, tickerText, when);

        // Uses the default lighting scheme
        notification.defaults |= Notification.DEFAULT_LIGHTS;

        // Will show lights and make the notification disappear when the presses it
        notification.flags |= Notification.FLAG_AUTO_CANCEL | Notification.FLAG_SHOW_LIGHTS;

        CharSequence contentTitle = "New Krypto Message";
        CharSequence contentText = "From: " + originatingAddress + " " + message;
        Intent notificationIntent = new Intent(context, LoginActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);

        notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);

        final int HELLO_ID = 1;

        mNotificationManager.notify(HELLO_ID, notification);
    }
}

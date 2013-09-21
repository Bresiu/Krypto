package com.bresiu.krypto.sms;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.telephony.SmsMessage;
import android.util.Log;

import com.bresiu.krypto.LoginActivity;
import com.bresiu.krypto.R;
import com.bresiu.krypto.utils.CaesarDecrypt;

public class SmsReceiver extends BroadcastReceiver {
    private static final String TAG = "SmsSender";
    private static final int DELAY = 2000;
    Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive");

        //---get the SMS message passed in---
        Bundle bundle = intent.getExtras();
        SmsMessage[] msgs = null;
        String str = "";
        if (bundle != null) {
            //---retrieve the SMS message received---
            Object[] pdus = (Object[]) bundle.get("pdus");
            msgs = new SmsMessage[pdus.length];
            for (int i = 0; i < msgs.length; i++) {
                msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                str += msgs[i].getMessageBody();
                str += "\n";
            }
            if (msgs[0].getMessageBody().startsWith(SendSMS.KRYPTO_TAG)) {
                //TODO AsyncTask
                CaesarDecrypt caesarDecrypt = new CaesarDecrypt();

                createNotification(context, msgs[0].getOriginatingAddress(), caesarDecrypt.CaesarDecrypt(str));
                Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(100);
                /*for (int i = 0; i < 3; i++) {
                    Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
                    vibrator.vibrate(30);
                    SystemClock.sleep(DELAY);
                }*/
                // TODO: uncomment
                abortBroadcast();
            }
        }
    }

    // TODO
    private void createNotification(Context context, String originatingAddress, String message) {
        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(ns);

        int icon = R.drawable.ic_launcher;
        CharSequence tickerText = "New Krypto Message";
        long when = System.currentTimeMillis();

        Notification notification = new Notification(icon, tickerText, when);

        CharSequence contentTitle = "New Krypto Message";
        CharSequence contentText = "From: " + originatingAddress + " " + message;
        Intent notificationIntent = new Intent(context, LoginActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);

        notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);

        final int HELLO_ID = 1;

        mNotificationManager.notify(HELLO_ID, notification);
    }
}
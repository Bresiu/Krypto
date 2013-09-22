package com.bresiu.krypto.sms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.telephony.SmsMessage;
import android.util.Log;

import com.bresiu.krypto.db.MessagesDataSource;
import com.bresiu.krypto.utils.CaesarDecrypt;
import com.bresiu.krypto.utils.CreateNotification;

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


                MessagesDataSource datasource = new MessagesDataSource(context);
                datasource.open();
                datasource.createMessage(str);
                datasource.close();
//                Message message = datasource.createMessage("sad");
//                adapter.add(comment);
//                datasource.close();
                CreateNotification.createNotification(context, msgs[0].getOriginatingAddress(), caesarDecrypt.caesarDecrypt(str));
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
}
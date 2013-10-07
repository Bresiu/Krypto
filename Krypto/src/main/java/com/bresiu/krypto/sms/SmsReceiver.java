package com.bresiu.krypto.sms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.telephony.SmsMessage;
import android.util.Log;

import com.bresiu.krypto.InboxActivity;
import com.bresiu.krypto.utils.CreateNotification;
import com.bresiu.krypto.utils.cipher.CaesarDecrypt;

public class SmsReceiver extends BroadcastReceiver {
    private static final String TAG = "SmsSender";
    public static boolean isOnTop = false;

    public static void setOnTop(boolean onTop) {
        isOnTop = onTop;
    }

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
                abortBroadcast();

                String phoneNo = msgs[0].getOriginatingAddress();
                SmsToDatabase.insert(phoneNo, str, 0, context);
                if (isOnTop) {
                    InboxActivity.notifyList();
                } else {
                    //TODO AsyncTask
                    //TODO: Decrypt("algorithm name", key)
                    //Cipher cipher = new Cipher();
                    //cipher.decrypt("caesar", key, message);
                    CaesarDecrypt caesarDecrypt = new CaesarDecrypt();
                    CreateNotification.createNotification(context, phoneNo, caesarDecrypt.caesarDecrypt(str));
                }
                Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(100);
            }
        }
    }
}
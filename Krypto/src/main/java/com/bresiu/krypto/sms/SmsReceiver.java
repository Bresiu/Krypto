package com.bresiu.krypto.sms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.telephony.SmsMessage;
import android.text.format.Time;
import android.util.Log;

import com.bresiu.krypto.db.MessagesDataSource;
import com.bresiu.krypto.utils.CreateNotification;
import com.bresiu.krypto.utils.cipher.CaesarDecrypt;

public class SmsReceiver extends BroadcastReceiver {
    private static final String TAG = "SmsSender";

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
                //TODO AsyncTask
                CaesarDecrypt caesarDecrypt = new CaesarDecrypt();
                Time now = new Time();
                now.setToNow();
                String phoneNo = msgs[0].getOriginatingAddress();
                //TODO: Decrypt("algorithm name", key)
                //Cipher cipher = new Cipher();
                //cipher.decrypt("caesar", key, message);
                MessagesDataSource datasource = new MessagesDataSource(context);
                datasource.open();
                datasource.createMessage(now.format2445(), phoneNo, str, 0);
                datasource.close();


                CreateNotification.createNotification(context, phoneNo, caesarDecrypt.caesarDecrypt(str));
                Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(100);
            }
        }
    }
}
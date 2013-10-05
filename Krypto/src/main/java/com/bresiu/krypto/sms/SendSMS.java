package com.bresiu.krypto.sms;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.text.format.Time;
import android.widget.Toast;

import com.bresiu.krypto.db.MessagesDataSource;
import com.bresiu.krypto.utils.cipher.CaesarEncrypt;

import java.util.ArrayList;

public class SendSMS {
    public static final String KRYPTO_TAG = "|";
    private static final String TAG = "SendSMS";

    public void sendSMS(String phoneNumber, String message, Context context) {
        ArrayList<PendingIntent> sentPendingIntents = new ArrayList<PendingIntent>();
        ArrayList<PendingIntent> deliveredPendingIntents = new ArrayList<PendingIntent>();
        PendingIntent sentPI = PendingIntent.getBroadcast(context, 0,
                new Intent(context, SmsSentReceiver.class), 0);
        PendingIntent deliveredPI = PendingIntent.getBroadcast(context, 0,
                new Intent(context, SmsDeliveredReceiver.class), 0);
        //Encrypt message
        CaesarEncrypt caesarEncrypt = new CaesarEncrypt();

        try {
            SmsManager sms = SmsManager.getDefault();
            ArrayList<String> smsParts = sms.divideMessage(KRYPTO_TAG + caesarEncrypt.caesarEncrypt(message));
            for (int i = 0; i < smsParts.size(); i++) {
                sentPendingIntents.add(i, sentPI);
                deliveredPendingIntents.add(i, deliveredPI);
            }
            sms.sendMultipartTextMessage(phoneNumber, null, smsParts,
                    sentPendingIntents, deliveredPendingIntents);
            addToDatabase(message, phoneNumber, context);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "SMS sending failed...", Toast.LENGTH_SHORT).show();
        }
    }

    private void addToDatabase(String message, String phoneNumber, Context context) {
        Time now = new Time();
        now.setToNow();
        MessagesDataSource datasource = new MessagesDataSource(context);
        datasource.open();
        datasource.createMessage(now.toString(), phoneNumber, message, 1);
        datasource.close();
    }
}

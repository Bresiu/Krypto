package com.bresiu.krypto.sms;

import android.telephony.SmsManager;
import android.util.Log;

public class SendSMS {
    private static final String TAG = "SendSMS";
    private static final String KRYPTO_TAG = "|";

    public void SendSMS(String phoneNumber, String message) {
        Log.d(TAG, "SendSMS");

        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNumber, null, KRYPTO_TAG + message, null, null);
    }
}

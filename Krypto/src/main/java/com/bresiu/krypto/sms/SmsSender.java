package com.bresiu.krypto.sms;

import android.telephony.SmsManager;
import android.util.Log;

public class SmsSender {
    private static final String TAG = "SmsSender";
    private static final String KRYPTO_TAG = "|";

    public void SendSMS(String phoneNumber, String message) {
        Log.d(TAG, "SmsSender");

        SmsManager smsManager = SmsManager.getDefault();
        Log.d(TAG, "Phone Number: " + phoneNumber + ", Message " + message);
        smsManager.sendTextMessage(phoneNumber, null, KRYPTO_TAG + message, null, null);
    }
}

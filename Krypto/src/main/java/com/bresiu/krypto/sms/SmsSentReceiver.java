package com.bresiu.krypto.sms;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

public class SmsSentReceiver extends BroadcastReceiver {
    private static final String TAG = "ComposeReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "SmsSentReceiver");
        switch (getResultCode()) {
            case Activity.RESULT_OK:
                Toast.makeText(context, "SMS Sent", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "SMS Sent");
                break;
            case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                Toast.makeText(context, "SMS generic failure", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "SMS generic failure");
                break;
            case SmsManager.RESULT_ERROR_NO_SERVICE:
                Toast.makeText(context, "SMS no service", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "SMS no service");
                break;
            case SmsManager.RESULT_ERROR_NULL_PDU:
                Toast.makeText(context, "SMS null PDU", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "SMS null PDU");
                break;
            case SmsManager.RESULT_ERROR_RADIO_OFF:
                Toast.makeText(context, "SMS radio off", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "SMS radio off");
                break;
        }
    }
}

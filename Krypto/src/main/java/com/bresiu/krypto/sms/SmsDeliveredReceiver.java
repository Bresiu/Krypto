package com.bresiu.krypto.sms;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class SmsDeliveredReceiver extends BroadcastReceiver {
    private static final String TAG = "ComposeReceiver";
    private String action = "";

    @Override
    public void onReceive(Context context, Intent arg1) {
        Log.d(TAG, "SmsDeliveredReceiver");
        switch (getResultCode()) {
            case Activity.RESULT_OK:
                action = "SMS delivered";
                Log.d(TAG, "SMS delivered");
                break;
            case Activity.RESULT_CANCELED:
                action = "SMS not delivered";
                Log.d(TAG, "SMS not delivered");
                break;
        }
        Intent intent = new Intent();
        intent.setAction("SmsDeliveredReceiver");
        context.sendBroadcast(intent);
    }

}

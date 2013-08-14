package com.bresiu.krypto;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.bresiu.krypto.sms.SendSMS;
import com.bresiu.krypto.utils.SlidingLayer;

public class InboxActivity extends SherlockActivity implements View.OnClickListener {
    private static final String TAG = "InBoxActivity";
    private static ActionBar mActionBar;
    private static LayoutInflater mInflater;
    private static View mAbsView;
    private static SlidingLayer slidingLayer;
    private static BroadcastReceiver receiver;
    private static IntentFilter filter;
    private static final int PHONE_NUMBER_MIN_LENGTH = 9;
    private static Context context;
    private static EditText mPhoneNumber;
    private static EditText mMessage;
    private static String phno;
    private static String msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);
        //setupActionBar();
        setupWidgets();
        setupReceiver();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy");
        unregisterReceiver(receiver);
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick");
        switch (v.getId()) {
            case R.id.send:
                phno = mPhoneNumber.getText().toString();
                msg = mMessage.getText().toString();
                context = getApplicationContext();
                if (phno.length() >= PHONE_NUMBER_MIN_LENGTH && msg.length() > 0) {
                    SendSMS sendSMS = new SendSMS();
                    sendSMS.sendSMS(phno, msg, context);
                } else {
                    Toast.makeText(context,
                            "Please enter both phone number and message.",
                            Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportMenuInflater().inflate(R.menu.inbox_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.settings:
                if (!slidingLayer.isOpened()) {
                    slidingLayer.openLayer(true);
                } else {
                    slidingLayer.closeLayer(true);
                }
                break;
            case R.id.compose:
                if (!slidingLayer.isOpened()) {
                    slidingLayer.openLayer(true);
                    mPhoneNumber.requestFocus();
                } else {
                    slidingLayer.closeLayer(true);
                }
                break;
        }
        return true;
    }

    private void setupReceiver() {
        receiver = new BReceiver();
        filter = new IntentFilter("SmsDeliveredReceiver");
        registerReceiver(receiver, filter);
    }

    private void setupWidgets() {
        slidingLayer = (SlidingLayer) findViewById(R.id.slidingLayerCompose);
        slidingLayer.setSlidingEnabled(false);
        mPhoneNumber = (EditText) findViewById(R.id.phoneNumber);
        mMessage = (EditText) findViewById(R.id.message);
    }

    private void showDataFromIntent(Intent intent) {
        mPhoneNumber.setText("");
        mMessage.setText("");
        slidingLayer.closeLayer(true);
    }

    private class BReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            showDataFromIntent(intent);
        }
    }

}

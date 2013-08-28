package com.bresiu.krypto;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.bresiu.krypto.sms.SendSMS;
import com.bresiu.krypto.utils.SlidingLayer;

public class InboxActivity extends SherlockActivity implements View.OnClickListener {
    private static final String TAG = "InBoxActivity";
    private static final int PHONE_NUMBER_MIN_LENGTH = 9;
    private static SlidingLayer slidingCompose;
    private static SlidingLayer slidingSettings;
    private static BroadcastReceiver receiver;
    private static IntentFilter filter;
    private static Context context;
    private static EditText mPhoneNumber;
    private static EditText mMessage;
    private static String phno;
    private static String msg;
    private static InputMethodManager imm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);
        setupWidgets();
        setupReceiver();
        imm = (InputMethodManager) this.getSystemService(Service.INPUT_METHOD_SERVICE);
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
                if (slidingCompose.isOpened()) {
                    slidingCompose.closeLayer(true);

                    imm.hideSoftInputFromWindow(mPhoneNumber.getWindowToken(), 0);
                }
                if (!slidingSettings.isOpened()) {
                    slidingSettings.openLayer(true);
                } else {
                    slidingSettings.closeLayer(true);
                }
                break;
            case R.id.compose:
                if (slidingSettings.isOpened()) {
                    slidingSettings.closeLayer(true);
                }
                if (!slidingCompose.isOpened()) {
                    slidingCompose.openLayer(true);
                    mPhoneNumber.requestFocus();
                    imm.showSoftInput(mPhoneNumber, 0);
                } else {
                    slidingCompose.closeLayer(true);
                    imm.hideSoftInputFromWindow(mPhoneNumber.getWindowToken(), 0);
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
        slidingCompose = (SlidingLayer) findViewById(R.id.slidingCompose);
        slidingCompose.setSlidingEnabled(false);
        slidingSettings = (SlidingLayer) findViewById(R.id.slidingMenu);
        mPhoneNumber = (EditText) findViewById(R.id.phoneNumber);
        mMessage = (EditText) findViewById(R.id.message);
    }

    private void showDataFromIntent(Intent intent) {
        mPhoneNumber.setText("");
        mMessage.setText("");
        slidingCompose.closeLayer(true);
    }

    private class BReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            showDataFromIntent(intent);
        }
    }

}

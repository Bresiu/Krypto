package com.bresiu.krypto;

import android.content.Context;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.bresiu.krypto.sms.SendSMS;
import com.bresiu.krypto.utils.TypefaceSpan;

public class ComposeActivity extends SherlockActivity implements View.OnClickListener {

    private static final String TAG = "ComposeActivity";
    private static final int PHONE_NUMBER_MIN_LENGTH = 9;
    private EditText mPhoneNumber;
    private EditText mMessage;

    Context context = getApplicationContext();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        final ActionBar ab = getSupportActionBar();

        SpannableString s = new SpannableString("Krypto");
        s.setSpan(new TypefaceSpan(this, "Roboto-Light"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        ab.setTitle(s);
        setContentView(R.layout.activity_compose);
        setupWidgets();
    }

    private void setupWidgets() {
        mPhoneNumber = (EditText) findViewById(R.id.phoneNumber);
        mMessage = (EditText) findViewById(R.id.message);
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick");
        switch (v.getId()) {
            case R.id.send:
                String phno = mPhoneNumber.getText().toString();
                String msg = mMessage.getText().toString();
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
}

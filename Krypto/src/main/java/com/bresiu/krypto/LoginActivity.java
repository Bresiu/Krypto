package com.bresiu.krypto;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LoginActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "LoginActivity";
    private static final String PREFERENCES_NAME = "Preferences";
    private static final String KEY_STORED = "KeyStored";
    private static final String KEY = "Key";
    private static String password;
    private static TextView mPass;
    private static LinearLayout mProg;
    private static LinearLayout mProg1;
    private static LinearLayout mProg2;
    private static LinearLayout mProg3;
    private static LinearLayout mProg4;
    private static int count = 0;
    private static SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        preferences = getSharedPreferences(PREFERENCES_NAME, Activity.MODE_PRIVATE);
        if (restoreData()) {
            setContentView(R.layout.activity_login);
            setupWidgets();
            password = "";
        } else {
            toRegister();
        }
    }

    private void setupWidgets() {
        mPass = (TextView) findViewById(R.id.pass);

        mProg = (LinearLayout) findViewById(R.id.prog);
        mProg1 = (LinearLayout) findViewById(R.id.prog1);
        mProg2 = (LinearLayout) findViewById(R.id.prog2);
        mProg3 = (LinearLayout) findViewById(R.id.prog3);
        mProg4 = (LinearLayout) findViewById(R.id.prog4);
    }

    @Override
    public void onClick(View v) {
        if (count == 0) {
            mPass.setText("");
            mProg.setBackgroundColor(getResources().getColor(R.color.newest_orange));
        }
        mPass.append("*");
        switch (v.getId()) {
            case R.id.b0:
                password += 0;
                break;
            case R.id.b1:
                password += 1;
                break;
            case R.id.b2:
                password += 2;
                break;
            case R.id.b3:
                password += 3;
                break;
            case R.id.b4:
                password += 4;
                break;
            case R.id.b5:
                password += 5;
                break;
            case R.id.b6:
                password += 6;
                break;
            case R.id.b7:
                password += 7;
                break;
            case R.id.b8:
                password += 8;
                break;
            case R.id.b9:
                password += 9;
                break;
            case R.id.back:

                break;
            case R.id.cancel:

                break;
        }
        count++;
        if (count == 4) {
            if (password.equals(preferences.getString(KEY, ""))) {
                loginComplete();
            } else {
                mPass.setText("Incorect Password");
                password = "";
                count = 0;
            }
        }
    }

    private boolean restoreData() {
        return preferences.getBoolean(KEY_STORED, false);
    }

    private void loginComplete() {
        Intent login = new Intent(this,
                ComposeActivity.class);
        startActivity(login);
        finish();
    }

    private void toRegister() {
        Intent register = new Intent(this,
                RegisterActivity.class);
        startActivity(register);
        finish();
    }
}

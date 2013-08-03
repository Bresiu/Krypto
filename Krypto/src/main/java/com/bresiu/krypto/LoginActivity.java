package com.bresiu.krypto;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class LoginActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "LoginActivity";
    private static final String PREFERENCES_NAME = "Preferences";
    private static final String KEY_STORED = "KeyStored";
    private static final String KEY = "Key";

    private static String password;
    private int count = 0;

    private TextView Pass;
    private SharedPreferences preferences;

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
        Pass = (TextView) findViewById(R.id.pass);
    }

    @Override
    public void onClick(View v) {
        if (count == 0) Pass.setText("");
        Pass.append("*");
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
        }
        count++;

        if (count == 4) {
            if (password.equals(preferences.getString(KEY, ""))) {
                loginComplete();
            } else {
                Pass.setText("Incorect Password");
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

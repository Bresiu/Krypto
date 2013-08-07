package com.bresiu.krypto;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class RegisterActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "RegisterActivity";
    private static final String PREFERENCES_NAME = "Preferences";
    private static final String KEY_STORED = "KeyStored";
    private static final String KEY = "Key";
    private static boolean isFirstAttempt = true;
    private static String passFirstAttempt;
    private static String password;
    private int count;
    private TextView Pass;
    private SharedPreferences preferences;
    private SharedPreferences.Editor preferencesEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");

        super.onCreate(savedInstanceState);

        preferences = getSharedPreferences(PREFERENCES_NAME, Activity.MODE_PRIVATE);
        preferencesEditor = preferences.edit();

        setContentView(R.layout.activity_register);
        setupWidgets();
        clean();
    }

    private void clean() {
        password = "";
        count = 0;
        Pass.setText("");
        isFirstAttempt = true;
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
            if (isFirstAttempt) {
                passFirstAttempt = password;
                password = "";
                isFirstAttempt = false;
                count = 0;
                Pass.setText("Enter pass again");
            } else {
                if (password.equals(passFirstAttempt)) {

                    saveData();
                } else {
                    Pass.setText("passes dont match");
                    password = "";
                    count = 0;
                }
                isFirstAttempt = true;
            }
        }
    }

    private void saveData() {
        preferencesEditor.putBoolean(KEY_STORED, true);
        preferencesEditor.putString(KEY, password);
        preferencesEditor.commit();
        passwordSet();
    }

    private void passwordSet() {
        Intent register = new Intent(this,
                ComposeActivity.class);
        startActivity(register);
        finish();
    }
}

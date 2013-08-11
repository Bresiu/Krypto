package com.bresiu.krypto;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class LoginActivity extends SherlockActivity implements View.OnClickListener {

    private static final String TAG = "LoginActivity";
    private static final String PREFERENCES_NAME = "Preferences";
    private static final String KEY_STORED = "KeyStored";
    private static final String KEY = "Key";
    private static boolean noKey;
    private static boolean isFirstAttempt;
    private static String passFirstAttempt;
    private static String password;
    private static int count;
    private static ActionBar mActionBar;
    private static LayoutInflater mInflater;
    private static View mAbsView;
    private static TextView mInfo;
    private static TextView mPass;
    private static Button mCancel;
    private static Button mBack;
    private static LinearLayout mProg1;
    private static LinearLayout mProg2;
    private static LinearLayout mProg3;
    private static LinearLayout mProg4;
    private static SharedPreferences preferences;
    private static SharedPreferences.Editor preferencesEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setupActionBar();
        setupWidgets();
        preferences = getSharedPreferences(PREFERENCES_NAME, Activity.MODE_PRIVATE);
        preferencesEditor = preferences.edit();
        if (!restoreData()) {
            initNewKey();
        } else {
            noKey = false;
        }
        initVars();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportMenuInflater().inflate(R.menu.login_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.delete_key:
                cleanData();
                break;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.b0:
                count++;
                password += 0;
                break;
            case R.id.b1:
                count++;
                password += 1;
                break;
            case R.id.b2:
                count++;
                password += 2;
                break;
            case R.id.b3:
                count++;
                password += 3;
                break;
            case R.id.b4:
                count++;
                password += 4;
                break;
            case R.id.b5:
                count++;
                password += 5;
                break;
            case R.id.b6:
                count++;
                password += 6;
                break;
            case R.id.b7:
                count++;
                password += 7;
                break;
            case R.id.b8:
                count++;
                password += 8;
                break;
            case R.id.b9:
                count++;
                password += 9;
                break;
            case R.id.back:
                if (count > 0) count--;
                if (password.length() > 0)
                    password = password.substring(0, password.length() - 1);
                break;
            case R.id.cancel:
                count = 0;
                password = "";
                break;
        }
        showProg();
    }

    private void setupActionBar() {
        mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowTitleEnabled(false);
        mInflater = LayoutInflater.from(this);
        mAbsView = mInflater.inflate(R.layout.abs_custom_font, null);
        mActionBar.setCustomView(mAbsView);
        mActionBar.setDisplayShowCustomEnabled(true);
    }

    private boolean restoreData() {
        return preferences.getBoolean(KEY_STORED, false);
    }

    private void initNewKey() {
        noKey = true;
        isFirstAttempt = true;
        passFirstAttempt = "";
        mInfo.setText("Enter Your New PIN:");
    }

    private void initVars() {
        password = "";
        count = 0;
    }

    private void setupWidgets() {
        mInfo = (TextView) findViewById(R.id.info);
        mPass = (TextView) findViewById(R.id.pass);
        mBack = (Button) findViewById(R.id.back);
        mCancel = (Button) findViewById(R.id.cancel);
        mProg1 = (LinearLayout) findViewById(R.id.prog1);
        mProg2 = (LinearLayout) findViewById(R.id.prog2);
        mProg3 = (LinearLayout) findViewById(R.id.prog3);
        mProg4 = (LinearLayout) findViewById(R.id.prog4);
        mBack.setEnabled(false);
        mCancel.setEnabled(false);
    }

    private void showProg() {
        switch (count) {
            case 0:
                mPass.setText("");
                mProg1.setBackgroundColor(getResources().getColor(R.color.newest_orange));
                mProg2.setBackgroundColor(getResources().getColor(R.color.newest_orange));
                mProg3.setBackgroundColor(getResources().getColor(R.color.newest_orange));
                mProg4.setBackgroundColor(getResources().getColor(R.color.newest_orange));
                mBack.setEnabled(false);
                mCancel.setEnabled(false);
                break;
            case 1:
                mPass.setText("*");
                mProg1.setBackgroundColor(getResources().getColor(R.color.newest_green));
                mProg2.setBackgroundColor(getResources().getColor(R.color.newest_orange));
                mProg3.setBackgroundColor(getResources().getColor(R.color.newest_orange));
                mProg4.setBackgroundColor(getResources().getColor(R.color.newest_orange));
                mBack.setEnabled(true);
                mCancel.setEnabled(true);
                break;

            case 2:
                mPass.setText("**");
                mProg1.setBackgroundColor(getResources().getColor(R.color.newest_green));
                mProg2.setBackgroundColor(getResources().getColor(R.color.newest_yellow));
                mProg3.setBackgroundColor(getResources().getColor(R.color.newest_orange));
                mProg4.setBackgroundColor(getResources().getColor(R.color.newest_orange));
                break;
            case 3:
                mPass.setText("***");
                mProg1.setBackgroundColor(getResources().getColor(R.color.newest_green));
                mProg2.setBackgroundColor(getResources().getColor(R.color.newest_yellow));
                mProg3.setBackgroundColor(getResources().getColor(R.color.newest_purple));
                mProg4.setBackgroundColor(getResources().getColor(R.color.newest_orange));
                break;
            case 4:
                if (noKey) {
                    if (isFirstAttempt) {
                        mProg1.setBackgroundColor(getResources().getColor(R.color.newest_orange));
                        mProg2.setBackgroundColor(getResources().getColor(R.color.newest_orange));
                        mProg3.setBackgroundColor(getResources().getColor(R.color.newest_orange));
                        mProg4.setBackgroundColor(getResources().getColor(R.color.newest_orange));
                        mPass.setText("");
                        passFirstAttempt = password;
                        isFirstAttempt = false;
                        initVars();
                        mInfo.setText("Confim PIN:");
                    } else {
                        if (password.equals(passFirstAttempt)) {
                            mInfo.setText("PIN set succesfully!\nLogging in...");
                            mProg1.setBackgroundColor(getResources().getColor(R.color.blue));
                            mProg2.setBackgroundColor(getResources().getColor(R.color.blue));
                            mProg3.setBackgroundColor(getResources().getColor(R.color.blue));
                            mProg4.setBackgroundColor(getResources().getColor(R.color.blue));
                            saveData();
                        } else {
                            mInfo.setText("PIN do not match.\nTry again...");
                            mProg1.setBackgroundColor(getResources().getColor(R.color.newest_orange));
                            mProg2.setBackgroundColor(getResources().getColor(R.color.newest_orange));
                            mProg3.setBackgroundColor(getResources().getColor(R.color.newest_orange));
                            mProg4.setBackgroundColor(getResources().getColor(R.color.newest_orange));
                            mPass.setText("");
                            isFirstAttempt = true;
                            passFirstAttempt = "";
                            initVars();
                        }
                    }
                } else {
                    if (password.equals(preferences.getString(KEY, ""))) {
                        mInfo.setText("PIN correct");
                        mPass.setText("");
                        mProg1.setBackgroundColor(getResources().getColor(R.color.blue));
                        mProg2.setBackgroundColor(getResources().getColor(R.color.blue));
                        mProg3.setBackgroundColor(getResources().getColor(R.color.blue));
                        mProg4.setBackgroundColor(getResources().getColor(R.color.blue));
                        loginComplete();
                    } else {
                        initVars();
                        mInfo.setText("Incorect PIN, try again");
                        mPass.setText("");
                        mProg1.setBackgroundColor(getResources().getColor(R.color.newest_orange));
                        mProg2.setBackgroundColor(getResources().getColor(R.color.newest_orange));
                        mProg3.setBackgroundColor(getResources().getColor(R.color.newest_orange));
                        mProg4.setBackgroundColor(getResources().getColor(R.color.newest_orange));
                    }
                    break;
                }
        }
    }

    private void cleanData() {
        preferencesEditor.putBoolean(KEY_STORED, false);
        preferencesEditor.commit();
        reload();
    }

    private void reload() {

        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();

        overridePendingTransition(0, 0);
        startActivity(intent);
    }

    private void saveData() {
        preferencesEditor.putBoolean(KEY_STORED, true);
        preferencesEditor.putString(KEY, password);
        preferencesEditor.commit();
        initVars();
        loginComplete();
    }

    private void loginComplete() {
        Intent login = new Intent(this,
                ComposeActivity.class);
        startActivity(login);
        finish();
    }
}

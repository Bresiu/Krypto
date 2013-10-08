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
import com.bresiu.krypto.utils.SlidingLayer;

import java.text.DateFormat;
import java.util.Date;

public class LoginActivity extends SherlockActivity implements View.OnClickListener {

    private static boolean noKey;
    private static boolean isFirstAttempt;
    private static String passFirstAttempt;
    private static String password;
    private static int count;
    private static TextView mInfo;
    private static TextView mPass;
    private static TextView mLastLogged;
    private static Button mCancel;
    private static Button mBack;
    private static SlidingLayer slidingMenu;
    private static LinearLayout mProg1;
    private static LinearLayout mProg2;
    private static LinearLayout mProg3;
    private static LinearLayout mProg4;
    private static SharedPreferences preferences;
    private static SharedPreferences.Editor preferencesEditor;
    private final String TAG = "LoginActivity";
    private final String KEY_STORED = "KeyStored";
    private final String KEY = "Key";
    private final String LAST_LOGGED = "LastLogged";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, getString(R.string.on_create));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setupActionBar();
        setupWidgets();
        String PREFERENCES_NAME = "Preferences";
        preferences = getSharedPreferences(PREFERENCES_NAME, Activity.MODE_PRIVATE);
        preferencesEditor = preferences.edit();
        if (!restoreData()) {
            initNewKey();
        } else {
            mLastLogged.setText(String.format("%s\n%s", getString(R.string.last_logged_in), preferences.getString(LAST_LOGGED, getString(R.string.blank))));
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
            case R.id.menu:
                if (!slidingMenu.isOpened()) {
                    slidingMenu.openLayer(true);
                } else {
                    slidingMenu.closeLayer(true);
                }
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
            case R.id.delete_button:
                cleanData();
                break;
            case R.id.about:
                break;
            case R.id.licences:
                break;
        }
        showProg();
    }

    private void setupActionBar() {
        ActionBar mActionBar;
        LayoutInflater mInflater;
        View mAbsView;
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
        mInfo.setText(getString(R.string.enter_pin));
    }

    private void initVars() {
        password = "";
        count = 0;
    }

    private void setupWidgets() {
        mInfo = (TextView) findViewById(R.id.info);
        mPass = (TextView) findViewById(R.id.pass);
        mLastLogged = (TextView) findViewById(R.id.last_logged_in);
        mBack = (Button) findViewById(R.id.back);
        mCancel = (Button) findViewById(R.id.cancel);
        slidingMenu = (SlidingLayer) findViewById(R.id.slidingMenu);
        mProg1 = (LinearLayout) findViewById(R.id.prog1);
        mProg2 = (LinearLayout) findViewById(R.id.prog2);
        mProg3 = (LinearLayout) findViewById(R.id.prog3);
        mProg4 = (LinearLayout) findViewById(R.id.prog4);
        mBack.setEnabled(false);
        mCancel.setEnabled(false);
    }

    private void showProg() {
        // TODO Dodac losowe kolory z listy do linear layout, wraz z wprowadzaniem kolejnych cyfr
        if (slidingMenu.isOpened()) {
            slidingMenu.closeLayer(true);
        }
        switch (count) {
            case 0:
                mPass.setText(getString(R.string.blank));
                setDark();
                mBack.setEnabled(false);
                mCancel.setEnabled(false);
                break;
            case 1:
                // mPass.setText(progStar + "*")
                // mPass.setText(progStar.substring(0, progStar.length()-1)));
                mPass.setText(getString(R.string.one_star));
                mProg1.setBackgroundColor(getResources().getColor(R.color.turquoise));
                mProg2.setBackgroundColor(getResources().getColor(R.color.stripes));
                mProg3.setBackgroundColor(getResources().getColor(R.color.stripes));
                mProg4.setBackgroundColor(getResources().getColor(R.color.stripes));
                mBack.setEnabled(true);
                mCancel.setEnabled(true);
                break;

            case 2:
                mPass.setText(getString(R.string.two_stars));
                mProg1.setBackgroundColor(getResources().getColor(R.color.turquoise));
                mProg2.setBackgroundColor(getResources().getColor(R.color.yellow));
                mProg3.setBackgroundColor(getResources().getColor(R.color.stripes));
                mProg4.setBackgroundColor(getResources().getColor(R.color.stripes));
                break;
            case 3:
                mPass.setText(getString(R.string.three_stars));
                mProg1.setBackgroundColor(getResources().getColor(R.color.turquoise));
                mProg2.setBackgroundColor(getResources().getColor(R.color.yellow));
                mProg3.setBackgroundColor(getResources().getColor(R.color.violet));
                mProg4.setBackgroundColor(getResources().getColor(R.color.stripes));
                break;
            case 4:
                if (noKey) {
                    if (isFirstAttempt) {
                        setDark();
                        mPass.setText(getString(R.string.blank));
                        passFirstAttempt = password;
                        isFirstAttempt = false;
                        initVars();
                        mInfo.setText(getString(R.string.confirm_pin));
                    } else {
                        if (password.equals(passFirstAttempt)) {
                            mInfo.setText(getString(R.string.pin_set));
                            mPass.setText(getString(R.string.four_stars));
                            setBlue();
                            saveData();
                        } else {
                            mInfo.setText(getString(R.string.pin_do_not_match));
                            setRed();
                            mPass.setText(getString(R.string.blank));
                            isFirstAttempt = true;
                            passFirstAttempt = getString(R.string.blank);
                            initVars();
                        }
                    }
                } else {
                    if (password.equals(preferences.getString(KEY, getString(R.string.blank)))) {
                        mInfo.setText(getString(R.string.pin_correct));
                        mPass.setText(getString(R.string.four_stars));
                        setBlue();
                        loginComplete();
                    } else {
                        initVars();
                        mInfo.setText(getString(R.string.pin_incorrect));
                        mPass.setText(getString(R.string.blank));
                        setRed();
                    }
                    break;
                }
        }
    }

    private void setBlue() {
        mProg1.setBackgroundColor(getResources().getColor(R.color.green));
        mProg2.setBackgroundColor(getResources().getColor(R.color.green));
        mProg3.setBackgroundColor(getResources().getColor(R.color.green));
        mProg4.setBackgroundColor(getResources().getColor(R.color.green));
    }

    private void setRed() {
        mProg1.setBackgroundColor(getResources().getColor(R.color.red));
        mProg2.setBackgroundColor(getResources().getColor(R.color.red));
        mProg3.setBackgroundColor(getResources().getColor(R.color.red));
        mProg4.setBackgroundColor(getResources().getColor(R.color.red));
    }

    private void setDark() {
        mProg1.setBackgroundColor(getResources().getColor(R.color.stripes));
        mProg2.setBackgroundColor(getResources().getColor(R.color.stripes));
        mProg3.setBackgroundColor(getResources().getColor(R.color.stripes));
        mProg4.setBackgroundColor(getResources().getColor(R.color.stripes));
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
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        preferencesEditor.putString(LAST_LOGGED, currentDateTimeString);
        preferencesEditor.commit();
        Intent login = new Intent(this,
                InboxActivity.class);
        startActivity(login);
        overridePendingTransition(R.anim.push_down_in, R.anim.push_down_out);

        finish();
    }
}

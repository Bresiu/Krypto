package com.bresiu.krypto;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Spannable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.bresiu.krypto.utils.SlidingLayer;
import com.bresiu.krypto.utils.cipher.Hash;
import com.bresiu.krypto.utils.util.ColorUtils;
import com.bresiu.krypto.utils.util.ConvertUtils;
import com.bresiu.krypto.utils.util.FontUtils;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LoginActivity extends SherlockActivity implements View.OnClickListener {

    private static boolean noKey;
    private static boolean isFirstAttempt;
    private static String passFirstAttempt;
    private static String password;
    private static int count;
    private static SharedPreferences preferences;
    private static SharedPreferences.Editor preferencesEditor;
    private final String TAG = "LoginActivity";
    private final String KEY_STORED = "KeyStored";
    private final String KEY = "Key";
    private final String LAST_LOGGED = "LastLogged";
    private List<Integer> gradients;
    private TextView mInfo;
    private TextView mPass;
    private TextView mLastLogged;
    private Button mLogin;
    private Button mBack;
    private SlidingLayer slidingMenu;
    private LinearLayout mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, getString(R.string.on_create));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setupActionBar();
        setupWidgets();
        setupPreferences();
        initVars();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, getString(R.string.on_create_options));
        getSupportMenuInflater().inflate(R.menu.login_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, getString(R.string.on_options_item_selected));
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
        Log.d(TAG, getString(R.string.on_click));
        switch (v.getId()) {
            case R.id.b0:
                count++;
                password += 0;
                insertNewColor();
                break;
            case R.id.b1:
                count++;
                password += 1;
                insertNewColor();
                break;
            case R.id.b2:
                count++;
                password += 2;
                insertNewColor();
                break;
            case R.id.b3:
                count++;
                password += 3;
                insertNewColor();
                break;
            case R.id.b4:
                count++;
                password += 4;
                insertNewColor();
                break;
            case R.id.b5:
                count++;
                password += 5;
                insertNewColor();
                break;
            case R.id.b6:
                count++;
                password += 6;
                insertNewColor();
                break;
            case R.id.b7:
                count++;
                password += 7;
                insertNewColor();
                break;
            case R.id.b8:
                count++;
                password += 8;
                insertNewColor();
                break;
            case R.id.b9:
                count++;
                password += 9;
                insertNewColor();
                break;
            case R.id.back_cancel:
                if (count > 0) count--;
                if (password.length() > 0)
                    password = password.substring(0, password.length() - 1);
                //TODO
                if (gradients.size() > 0) {
                    deleteLastColor();
                }
                break;
            case R.id.login:
                try {
                    attemptLogin();
                } catch (NoSuchProviderException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.about:
                //TODO
                break;
            case R.id.licences:
                //TODO
                break;
        }
        try {
            showProg();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
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

    private void setupWidgets() {
        mInfo = (TextView) findViewById(R.id.info);
        mPass = (TextView) findViewById(R.id.pass);
        mLastLogged = (TextView) findViewById(R.id.last_logged_in);

        slidingMenu = (SlidingLayer) findViewById(R.id.slidingMenu);
        mProgress = (LinearLayout) findViewById(R.id.progress);

        mBack = (Button) findViewById(R.id.back_cancel);
        mLogin = (Button) findViewById(R.id.login);

        mBack.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //TODO
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    initVars();
                    try {
                        showProg();
                    } catch (NoSuchProviderException e) {
                        e.printStackTrace();
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
                    return true;
                }
                return false;
            }
        });

        mBack.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                initVars();
                try {
                    showProg();
                } catch (NoSuchProviderException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                return true;
            }
        });

        Button but1 = (Button) findViewById(R.id.b1);
        Button but2 = (Button) findViewById(R.id.b2);
        Button but3 = (Button) findViewById(R.id.b3);
        Button but4 = (Button) findViewById(R.id.b4);
        Button but5 = (Button) findViewById(R.id.b5);
        Button but6 = (Button) findViewById(R.id.b6);
        Button but7 = (Button) findViewById(R.id.b7);
        Button but8 = (Button) findViewById(R.id.b8);
        Button but9 = (Button) findViewById(R.id.b9);
        Button but0 = (Button) findViewById(R.id.b0);

        Button[] buttons = new Button[]{but1, but2, but3, but4, but5, but6, but7, but8, but9, but0};
        Spannable[] spannables = FontUtils.createSpannableList(this);
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setText(spannables[i]);
        }

        mBack.setEnabled(false);
        mLogin.setEnabled(false);
    }

    private void setupPreferences() {
        String PREFERENCES_NAME = "Preferences";
        preferences = getSharedPreferences(PREFERENCES_NAME, Activity.MODE_PRIVATE);
        preferencesEditor = preferences.edit();
        if (!restoreData()) {
            initNewKey();
        } else {
            mLastLogged.setText(String.format("%s\n%s", getString(R.string.last_logged_in),
                    preferences.getString(LAST_LOGGED, getString(R.string.blank))));
            noKey = false;
        }
    }

    private void initVars() {
        password = "";
        count = 0;
        gradients = new ArrayList<Integer>();

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

    private void showProg() throws NoSuchProviderException, NoSuchAlgorithmException {
        if (slidingMenu.isOpened()) {
            slidingMenu.closeLayer(true);
        }
        if (count == 1) {
            mBack.setEnabled(true);
            mLogin.setEnabled(true);
            mInfo.setText(R.string.pin);
        }
        if (count == 0) {
            mBack.setEnabled(false);
            mLogin.setEnabled(false);
            setDark();
            mPass.setText(getString(R.string.blank));
        } else {
            mProgress.setBackgroundColor(gradients.get(0));
            String stars = "";
            for (int i = 0; i < count; i++) {
                stars += getString(R.string.star);
            }
            mPass.setText(stars);
        }

        if (gradients.size() > 1) {
            mProgress.setBackground(ColorUtils.makeGradient(ConvertUtils.listToArray(gradients)));
        } else if (gradients.size() == 1) {
            mProgress.setBackgroundColor(gradients.get(0));
        } else {
            setDark();
        }
    }

    private void saveData() throws NoSuchProviderException, NoSuchAlgorithmException {
        preferencesEditor.putBoolean(KEY_STORED, true);
        preferencesEditor.putString(KEY, Hash.toHash(password));
        preferencesEditor.commit();
        initVars();
        loginComplete();
    }

    private void attemptLogin() throws NoSuchProviderException, NoSuchAlgorithmException {
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
                    setGreen();
                    try {
                        saveData();
                    } catch (NoSuchProviderException e) {
                        e.printStackTrace();
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
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
            if (Hash.toHash(password).equals(preferences.getString(KEY, getString(R.string.blank)))) {
                mInfo.setText(getString(R.string.pin_correct));
                setGreen();
                loginComplete();
            } else {
                initVars();
                mInfo.setText(getString(R.string.pin_incorrect));
                mPass.setText(getString(R.string.blank));
                setRed();
            }
        }
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

    private void setGreen() {
        mProgress.setBackgroundColor(getResources().getColor(R.color.green));
    }

    private void setRed() {
        mProgress.setBackgroundColor(getResources().getColor(R.color.red));
    }

    private void setDark() {
        mProgress.setBackgroundColor(getResources().getColor(R.color.stripes));
    }

    private int makeColor() {
        return getResources().getColor(ColorUtils.getNextRandomColor(this));
    }

    private void insertNewColor() {
        gradients.add(makeColor());
    }

    private void deleteLastColor() {
        gradients.remove(gradients.size() - 1);
    }
}

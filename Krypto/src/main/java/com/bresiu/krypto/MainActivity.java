package com.bresiu.krypto;

import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.bresiu.krypto.utils.TypefaceSpan;

public class MainActivity extends SherlockActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        final ActionBar ab = getSupportActionBar();

        SpannableString s = new SpannableString("Krypto");
        s.setSpan(new TypefaceSpan(this, "Roboto-Light"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        ab.setTitle(s);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop");
        super.onStop();
        finish();
    }

}
package com.bresiu.krypto;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "LoginActivity";
    private static final String PASS = "0000";
    private static String password;
    private int count = 0;
    private Button Button0;
    private Button Button1;
    private Button Button2;
    private Button Button3;
    private Button Button4;
    private Button Button5;
    private Button Button6;
    private Button Button7;
    private Button Button8;
    private Button Button9;
    private TextView Pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setupWidgets();
        password = "";
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause");
        super.onDestroy();
    }

    private void setupWidgets() {
        Pass = (TextView) findViewById(R.id.pass);

        Button0 = (Button) findViewById(R.id.b0);
        Button1 = (Button) findViewById(R.id.b1);
        Button2 = (Button) findViewById(R.id.b2);
        Button3 = (Button) findViewById(R.id.b3);
        Button4 = (Button) findViewById(R.id.b4);
        Button5 = (Button) findViewById(R.id.b5);
        Button6 = (Button) findViewById(R.id.b6);
        Button7 = (Button) findViewById(R.id.b7);
        Button8 = (Button) findViewById(R.id.b8);
        Button9 = (Button) findViewById(R.id.b9);
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
        Log.d(TAG, password);
        count++;

        if (count == 4) {
            if (password.equals(PASS)) {
                loginComplete();
            } else {
                Pass.setText("Incorect Password");
                password = "";
                count = 0;
            }
        }
    }

    private void loginComplete() {
        Intent login = new Intent(this,
                MainActivity.class);
        startActivity(login);
        finish();
    }
}

package com.bresiu.krypto;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.bresiu.krypto.db.Message;
import com.bresiu.krypto.db.MessagesDataSource;
import com.bresiu.krypto.listViewAdapter.ListViewAdapter;
import com.bresiu.krypto.sms.SendSMS;
import com.bresiu.krypto.sms.SmsReceiver;
import com.bresiu.krypto.utils.SlidingLayer;

import java.util.List;

public class InboxActivity extends SherlockActivity implements View.OnClickListener {
    private static final String TAG = "InBoxActivity";
    private static final int PHONE_NUMBER_MIN_LENGTH = 9;
    public static ListView lview;
    public static ListViewAdapter lviewAdapter;
    private static SlidingLayer slidingCompose;
    private static SlidingLayer slidingSettings;
    private static BroadcastReceiver receiver;
    private static EditText mPhoneNumber;
    private static EditText mMessage;
    private static InputMethodManager imm;
    private static MessagesDataSource datasource;
    private static List<Message> values;

    public static void notifyList() {
        values.clear();
        values.addAll(datasource.getAllMessages());
        lviewAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);
        datasource = new MessagesDataSource(this);
        datasource.open();

        setupWidgets();
        setupReceiver();

        makeList();

        isOnTop(true);
        imm = (InputMethodManager) this.getSystemService(Service.INPUT_METHOD_SERVICE);
    }

    private void isOnTop(boolean isOnTop) {
        SmsReceiver.setOnTop(isOnTop);
    }

    @Override
    protected void onResume() {
        super.onResume();
        datasource.open();
        isOnTop(true);
        notifyList();
    }

    @Override
    protected void onPause() {
        super.onPause();
        datasource.close();
        isOnTop(false);
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy");
        unregisterReceiver(receiver);
        datasource.close();
        isOnTop(false);
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick");
        switch (v.getId()) {
            case R.id.send:
                String phno = mPhoneNumber.getText().toString();
                String msg = mMessage.getText().toString();
                Context context = getApplicationContext();
                if (phno.length() >= PHONE_NUMBER_MIN_LENGTH && msg.length() > 0) {
                    SendSMS sendSMS = new SendSMS();
                    sendSMS.sendSMS(phno, msg, context);
                } else {
                    Toast.makeText(context,
                            "Please enter both phone number and message.",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.delete_button:
                datasource.deleteAll();
                notifyList();
                slidingSettings.closeLayer(true);
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
                    new ShowKeyboard().execute();
                } else {
                    slidingCompose.closeLayer(true);
                    hideVirturalKeyboard();
                    //new HideKeyboard().execute();
                }
                break;
        }
        return true;
    }

    public void makeList() {
        values = datasource.getAllMessages();
        lviewAdapter = new ListViewAdapter(this, values);
        lview.setAdapter(lviewAdapter);
        lview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                Message message = (Message) lviewAdapter.getItem(position);
                datasource.deleteMessage(message);
                notifyList();
                Toast.makeText(InboxActivity.this, "Item in position " + position + " deleted",
                        Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    private void setupReceiver() {
        receiver = new BReceiver();
        IntentFilter filter = new IntentFilter("SmsDeliveredReceiver");
        registerReceiver(receiver, filter);
    }

    private void setupWidgets() {
        lview = (ListView) findViewById(R.id.listView);
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

    private void hideVirturalKeyboard() {
        View v = getCurrentFocus();
        if (v != null && v instanceof EditText) {
            InputMethodManager mgr = (InputMethodManager) (v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE));
            mgr.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

    private class BReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            showDataFromIntent(intent);
        }
    }

    //TODO: delete this
    private class ShowKeyboard extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            imm.showSoftInput(mPhoneNumber, 0);
            return null;
        }
    }

    private class HideKeyboard extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            imm.hideSoftInputFromWindow(slidingCompose.getWindowToken(), 0);
            return null;
        }
    }
}
package com.bresiu.krypto.sms;

import android.content.Context;
import android.text.format.Time;

import com.bresiu.krypto.db.MessagesDataSource;

public class SmsToDatabase {
    public static void insert(String phoneNo, String message, int isOwn, int isRead, Context context) {
        Time now = new Time();
        now.setToNow();
        MessagesDataSource datasource = new MessagesDataSource(context);
        datasource.open();
        datasource.createMessage(now.format2445(), phoneNo, message, isOwn, isRead);
        datasource.close();
    }
}

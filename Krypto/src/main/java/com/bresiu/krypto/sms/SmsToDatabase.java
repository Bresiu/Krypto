package com.bresiu.krypto.sms;

import android.content.Context;

import com.bresiu.krypto.db.MessagesDataSource;
import com.bresiu.krypto.utils.Parse;

import java.text.DateFormat;
import java.util.Date;

public class SmsToDatabase {
    public static void insert(String phoneNo, String message, int isOwn, int isRead, Context context) {
        MessagesDataSource datasource = new MessagesDataSource(context);
        datasource.open();
        datasource.createMessage(
                DateFormat.getDateTimeInstance().format(new Date()),
                Parse.phoneNo(phoneNo),
                message,
                isOwn,
                isRead);
        datasource.close();
    }
}

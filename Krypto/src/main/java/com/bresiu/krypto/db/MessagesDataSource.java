package com.bresiu.krypto.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.bresiu.krypto.InboxActivity;

import java.util.ArrayList;
import java.util.List;

public class MessagesDataSource {
    private final String TAG = "MessageDataSource";
    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = {
            MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_PHONE,
            MySQLiteHelper.COLUMN_TIME,
            MySQLiteHelper.COLUMN_SMS,
            MySQLiteHelper.COLUMN_OWN};

    public MessagesDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Message createMessage(String time, String phone, String message, int own) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_TIME, time);
        values.put(MySQLiteHelper.COLUMN_PHONE, phone);
        values.put(MySQLiteHelper.COLUMN_SMS, message);
        values.put(MySQLiteHelper.COLUMN_OWN, own);
        long insertId = database.insert(MySQLiteHelper.TABLE_SMS, null,
                values);
        Cursor cursor = database.query(
                MySQLiteHelper.TABLE_SMS,
                allColumns,
                MySQLiteHelper.COLUMN_ID + " = " + insertId
                , null, null, null, null);
        cursor.moveToFirst();
        Message newMessage = cursorToMessage(cursor);
        cursor.close();
        InboxActivity.notifyList();
        return newMessage;
    }

    public void deleteMessage(Message message) {
        long id = message.getId();
        Log.d(TAG, "Message deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_SMS, MySQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }

    public List<Message> getAllMessages() {
        List<Message> messages = new ArrayList<Message>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_SMS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Message message = cursorToMessage(cursor);
            messages.add(message);
            cursor.moveToNext();
        }
        // Make sure to close the cursor
        cursor.close();
        return messages;
    }

    private Message cursorToMessage(Cursor cursor) {
        Message message = new Message();
        message.setId(cursor.getLong(0));
        message.setPhone(cursor.getString(1));
        message.setTime(cursor.getString(2));
        message.setMessage(cursor.getString(3));
        message.setOwn(cursor.getInt(4));
        return message;
    }
}

package com.bresiu.krypto.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_SMS = "messages";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_SMS = "message";
    public static final String COLUMN_OWN = "own";

    private static final String DATABASE_NAME = "sms.db";
    private static final int DATABASE_VERSION = 1;
    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_SMS + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_PHONE + " text not null, "
            + COLUMN_TIME + " text not null , "
            + COLUMN_SMS + " text not null , "
            + COLUMN_OWN + " text not null);";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SMS);
        onCreate(db);
    }
}

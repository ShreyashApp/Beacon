package com.example.beacon;

/**
 * Created by Shreyash Appikatla on 11-06-2015.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shreyash Appikatla on 06-06-2015.
 */
public class MessageDatabase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "MessageDatabase";
    private static final String TABLE_NAME = "message";
    private static final String ID_KEY = "userId";
    private static final String TITLE = "body";

    public MessageDatabase(Context context) {
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + TABLE_NAME + "(" + ID_KEY + " TEXT NOT NULL," + TITLE + " TEXT NOT NULL," +")";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String query = "DROP TABLE IF EXISTS " + TABLE_NAME;
        sqLiteDatabase.execSQL(query);
        onCreate(sqLiteDatabase);
    }

    public void addRow(String title, String userId) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(TITLE, title);
        contentValues.put(ID_KEY, userId);

        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        sqLiteDatabase.close();
    }

    public List<Message> getRows() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_NAME;

        List<Message> reminderItems = new ArrayList<Message>();

        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        if(cursor.moveToFirst()) {
            do {
                String title = cursor.getString(cursor.getColumnIndex(TITLE));
                String userId = cursor.getString(cursor.getColumnIndex(ID_KEY));
                Message reminderItem = new Message(title, userId);
                reminderItems.add(reminderItem);
            } while(cursor.moveToNext());
        }
        return reminderItems;
    }
}

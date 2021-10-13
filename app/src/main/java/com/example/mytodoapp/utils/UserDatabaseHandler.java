package com.example.mytodoapp.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDatabaseHandler extends SQLiteOpenHelper {
    public static final String TAG = UserDatabaseHandler.class.getSimpleName();
    public static final String DB_NAME = "todoDB";
    public static final int DB_VERSION = 6;
    public static final String USER_TABLE = "user";
    public static final String ID = "id";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASS = "password";

    public static final String CREATE_TABLE_USERS = "CREATE TABLE " + USER_TABLE + "("
            + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_EMAIL + " TEXT,"
            + COLUMN_PASS + " TEXT);";

    public UserDatabaseHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        onCreate(db);
    }

    public void addUser(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASS, password);

        long id = db.insert(USER_TABLE, null, values);
        db.close();
    }

    public boolean getUser(String email, String pass){
        String selectQuery = "select * from  " + USER_TABLE + " where " +
                COLUMN_EMAIL + " = " + "'"+email+"'" + " and " + COLUMN_PASS + " = " + "'"+pass+"'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {

            return true;
        }
        cursor.close();
        db.close();

        return false;
    }

    public int getIdUser(String _email, String _password) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT " + ID + " FROM " + USER_TABLE + " WHERE " + COLUMN_EMAIL +
                " = ? AND " + COLUMN_PASS + " = ?";
        Cursor cursor;

        if (db != null) {
            cursor = db.rawQuery(query, new String[] {_email, _password});

            if (cursor.getCount() > 0 && cursor.moveToFirst()) {
                return cursor.getInt(0);
            } else {
                return 0;
            }
        }
        return 0;
    }
}

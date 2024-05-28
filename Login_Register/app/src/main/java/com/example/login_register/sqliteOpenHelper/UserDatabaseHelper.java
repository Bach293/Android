package com.example.login_register.sqliteOpenHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.login_register.model.User;

import java.util.ArrayList;

public class UserDatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "UserDB.db";
    public static final String USERS_TABLE_NAME = "users";
    public static final String USERS_COLUMN_ID = "id";
    public static final String USERS_COLUMN_EMAIL = "email";
    public static final String USERS_COLUMN_PASSWORD = "password";

    public UserDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create users table
        db.execSQL("CREATE TABLE " + USERS_TABLE_NAME + " (" + USERS_COLUMN_ID + " INTEGER PRIMARY KEY, " + USERS_COLUMN_EMAIL + " TEXT, " + USERS_COLUMN_PASSWORD + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + USERS_TABLE_NAME);
        // Create tables again
        onCreate(db);
    }
    public boolean insertUser(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USERS_COLUMN_EMAIL, email);
        contentValues.put(USERS_COLUMN_PASSWORD, password);
        db.insert(USERS_TABLE_NAME, null, contentValues);
        return true;
    }

    public boolean updateUser(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USERS_COLUMN_PASSWORD, password);
        db.update(USERS_TABLE_NAME, contentValues, USERS_COLUMN_EMAIL + " = ? ", new String[]{email});
        return true;
    }

    public Integer deleteUser(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(USERS_TABLE_NAME, USERS_COLUMN_EMAIL + " = ? ", new String[]{email});
    }

    public ArrayList<User> getAllUsers() {
        ArrayList<User> array_list = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + USERS_TABLE_NAME, null);

        if (res != null && res.moveToFirst()) {
            int emailIndex = res.getColumnIndex(USERS_COLUMN_EMAIL);
            int passwordIndex = res.getColumnIndex(USERS_COLUMN_PASSWORD);

            if (emailIndex != -1 && passwordIndex != -1) {
                do {
                    String email = res.getString(emailIndex);
                    String password = res.getString(passwordIndex);
                    User user = new User(email, password);
                    array_list.add(user);
                } while (res.moveToNext());
            }

            res.close();
        }

        return array_list;
    }
}


package com.example.fengshuistore.SqliteOpenHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import com.example.fengshuistore.Model.Product;
import com.example.fengshuistore.Model.User;

import java.util.ArrayList;

public class SqliteOpenHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "FengShuiStore.db";
    public static final String USERS_TABLE_NAME = "users";
    public static final String USERS_COLUMN_ID = "id";
    public static final String USERS_COLUMN_NAME = "name";
    public static final String USERS_COLUMN_USERNAME = "username";
    public static final String USERS_COLUMN_PASSWORD = "password";
    public static final String USERS_COLUMN_CREATE_AT = "create_at";
    public static final String PRODUCTS_TABLE_NAME = "products";
    public static final String PRODUCTS_COLUMN_ID = "id";
    public static final String PRODUCTS_COLUMN_NAME = "name";
    public static final String PRODUCTS_COLUMN_DESCRIPTION = "description";
    public static final String PRODUCTS_COLUMN_IMAGE = "image";
    public static final String PRODUCTS_COLUMN_PRICE = "price";

    public SqliteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + USERS_TABLE_NAME + " ("
                + USERS_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + USERS_COLUMN_NAME + " TEXT, "
                + USERS_COLUMN_USERNAME + " TEXT, "
                + USERS_COLUMN_PASSWORD + " TEXT, "
                + USERS_COLUMN_CREATE_AT + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP)");
        db.execSQL("CREATE TABLE " + PRODUCTS_TABLE_NAME + " ("
                + PRODUCTS_COLUMN_ID + " INTEGER PRIMARY KEY, "
                + PRODUCTS_COLUMN_NAME + " TEXT, "
                + PRODUCTS_COLUMN_DESCRIPTION + " TEXT, "
                + PRODUCTS_COLUMN_IMAGE + " TEXT, "
                + PRODUCTS_COLUMN_PRICE + " REAL )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USERS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + PRODUCTS_TABLE_NAME);
        onCreate(db);
    }

    public boolean insertUser(String name, String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USERS_COLUMN_NAME, name);
        contentValues.put(USERS_COLUMN_USERNAME, username);
        contentValues.put(USERS_COLUMN_PASSWORD, password);
        db.insert(USERS_TABLE_NAME, null, contentValues);
        return true;
    }

    public boolean updateUser(String username, String name, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USERS_COLUMN_NAME, username);
        contentValues.put(USERS_COLUMN_NAME, name);
        contentValues.put(USERS_COLUMN_PASSWORD, password);
        db.update(USERS_TABLE_NAME, contentValues, USERS_COLUMN_NAME + " = ? ", new String[]{name});
        return true;
    }

    public Integer deleteUser(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(USERS_TABLE_NAME, USERS_COLUMN_NAME + " = ? ", new String[]{name});
    }

    public ArrayList<User> getAllUsers() {
        ArrayList<User> array_list = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + USERS_TABLE_NAME, null);

        if (res != null && res.moveToFirst()) {
            int nameIndex = res.getColumnIndex(USERS_COLUMN_NAME);
            int usernameIndex = res.getColumnIndex(USERS_COLUMN_USERNAME);
            int passwordIndex = res.getColumnIndex(USERS_COLUMN_PASSWORD);
            int createAtIndex = res.getColumnIndex(USERS_COLUMN_CREATE_AT);

            if (nameIndex != -1 && usernameIndex != -1 && passwordIndex != -1 && createAtIndex != -1) {
                do {
                    String name = res.getString(nameIndex);
                    String username = res.getString(usernameIndex);
                    String password = res.getString(passwordIndex);
                    String create_at = res.getString(createAtIndex);
                    User user = new User(name, username, password);
                    user.setCreate_at(create_at);
                    array_list.add(user);
                } while (res.moveToNext());
            }

            res.close();
        }

        return array_list;
    }

    public boolean insertProduct(int id, String name, String description, float price, Uri imageUri) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PRODUCTS_COLUMN_ID, id);
        contentValues.put(PRODUCTS_COLUMN_NAME, name);
        contentValues.put(PRODUCTS_COLUMN_DESCRIPTION, description);
        contentValues.put(PRODUCTS_COLUMN_IMAGE, imageUri.toString());
        contentValues.put(PRODUCTS_COLUMN_PRICE, price);
        db.insert(PRODUCTS_TABLE_NAME, null, contentValues);
        return true;
    }

    public boolean updateProduct(int id, String name, String description, float price, Uri imageUri) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PRODUCTS_COLUMN_ID, id);
        contentValues.put(PRODUCTS_COLUMN_NAME, name);
        contentValues.put(PRODUCTS_COLUMN_DESCRIPTION, description);
        contentValues.put(PRODUCTS_COLUMN_IMAGE, imageUri.toString());
        contentValues.put(PRODUCTS_COLUMN_PRICE, price);
        db.update(PRODUCTS_TABLE_NAME, contentValues, PRODUCTS_COLUMN_NAME + " = ? ", new String[]{name});
        return true;
    }

    public Integer deleteProduct(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(PRODUCTS_TABLE_NAME, PRODUCTS_COLUMN_NAME + " = ? ", new String[]{name});
    }

    public ArrayList<Product> getAllProducts() {
        ArrayList<Product> array_list = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + PRODUCTS_TABLE_NAME, null);

        if (res != null && res.moveToFirst()) {
            int idIndex = res.getColumnIndex(PRODUCTS_COLUMN_ID);
            int nameIndex = res.getColumnIndex(PRODUCTS_COLUMN_NAME);
            int descriptionIndex = res.getColumnIndex(PRODUCTS_COLUMN_DESCRIPTION);
            int imageIndex = res.getColumnIndex(PRODUCTS_COLUMN_IMAGE);
            int priceIndex = res.getColumnIndex(PRODUCTS_COLUMN_PRICE);

            if (idIndex != -1 && nameIndex != -1 && descriptionIndex != -1 && imageIndex != -1 && priceIndex != -1) {
                do {
                    int id = res.getInt(idIndex);
                    String name = res.getString(nameIndex);
                    String description = res.getString(descriptionIndex);
                    String image = res.getString(imageIndex);
                    float price = res.getFloat(priceIndex);
                    Product product = new Product(id, name, description, price, Uri.parse(image));
                    array_list.add(product);
                } while (res.moveToNext());
            }

            res.close();
        }

        return array_list;
    }
}

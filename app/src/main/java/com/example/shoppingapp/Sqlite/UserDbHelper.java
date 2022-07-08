package com.example.shoppingapp.Sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.shoppingapp.dataFirebase.Users;

public class UserDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "User.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + UserContract.FeedEntry.TABLE_NAME + " (" +
                    UserContract.FeedEntry.ID + " INTEGER PRIMARY KEY," +
                    UserContract.FeedEntry.COLUMN_NAME + " TEXT," +
                    UserContract.FeedEntry.COLUMN_AGE + " TEXT," +
                    UserContract.FeedEntry.COLUMN_GENDER + " TEXT," +
                    UserContract.FeedEntry.COLUMN_IMAGE + " TEXT," +
                    UserContract.FeedEntry.COLUMN_ADMIN + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + UserContract.FeedEntry.TABLE_NAME;

    public UserDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void addUser(Users user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(UserContract.FeedEntry.ID, user.getId());
        values.put(UserContract.FeedEntry.COLUMN_NAME, user.getFullName());
        values.put(UserContract.FeedEntry.COLUMN_AGE, user.getAge());
        values.put(UserContract.FeedEntry.COLUMN_GENDER, user.getGenDer());
        values.put(UserContract.FeedEntry.COLUMN_IMAGE, user.getImagePerson());
        values.put(UserContract.FeedEntry.COLUMN_ADMIN, user.getIsAdmin());

        db.insert(UserContract.FeedEntry.TABLE_NAME, null, values);
        db.close();
    }

    public Users getUser(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(UserContract.FeedEntry.TABLE_NAME, null, UserContract.FeedEntry.ID + " = ?", new String[] { String.valueOf(userId) },null, null, null);
        if(cursor != null)
            cursor.moveToFirst();
        Users user = new Users(cursor.getInt(0) > 0, cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
        return user;
    }

    public void updateUser(Users user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UserContract.FeedEntry.COLUMN_NAME, user.getFullName());
        values.put(UserContract.FeedEntry.COLUMN_AGE, user.getAge());
        values.put(UserContract.FeedEntry.COLUMN_GENDER, user.getGenDer());
        values.put(UserContract.FeedEntry.COLUMN_IMAGE, user.getImagePerson());

        db.update(UserContract.FeedEntry.TABLE_NAME, values, UserContract.FeedEntry.ID + " = ?", new String[] { String.valueOf(user.getId()) });
        db.close();
    }

    public void deleteUser(int userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(UserContract.FeedEntry.TABLE_NAME, UserContract.FeedEntry.ID + " = ?", new String[] { String.valueOf(userId) });
        db.close();
    }
}

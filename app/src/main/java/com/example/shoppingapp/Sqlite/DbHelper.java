package com.example.shoppingapp.Sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.shoppingapp.dataFirebase.Users;

public class DbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "DbHelper.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + UserContract.FeedEntry.TABLE_NAME + " (" +
                    UserContract.FeedEntry.ID + " TEXT PRIMARY KEY," +
                    UserContract.FeedEntry.COLUMN_NAME + " TEXT," +
                    UserContract.FeedEntry.COLUMN_AGE + " INTEGER," +
                    UserContract.FeedEntry.COLUMN_GENDER + " TEXT," +
                    UserContract.FeedEntry.COLUMN_IMAGE + " TEXT," +
                    UserContract.FeedEntry.COLUMN_ADMIN + " INTEGER)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + UserContract.FeedEntry.TABLE_NAME;

    public DbHelper(Context context) {
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

    public boolean insertUser(Users user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(UserContract.FeedEntry.ID, user.getId());
        values.put(UserContract.FeedEntry.COLUMN_NAME, user.getName());
        values.put(UserContract.FeedEntry.COLUMN_AGE, user.getAge());
        values.put(UserContract.FeedEntry.COLUMN_GENDER, user.getGenDer());
        values.put(UserContract.FeedEntry.COLUMN_IMAGE, user.getImagePerson());
        values.put(UserContract.FeedEntry.COLUMN_ADMIN, user.getIsAdmin());

        long resutl = db.insert(UserContract.FeedEntry.TABLE_NAME, null, values);
        db.close();

        if(resutl == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean updateUser(Users user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UserContract.FeedEntry.COLUMN_NAME, user.getName());
        values.put(UserContract.FeedEntry.COLUMN_AGE, user.getAge());
        values.put(UserContract.FeedEntry.COLUMN_GENDER, user.getGenDer());
        values.put(UserContract.FeedEntry.COLUMN_IMAGE, user.getImagePerson());

        long resutl = db.update(UserContract.FeedEntry.TABLE_NAME, values, UserContract.FeedEntry.ID + " = ?", new String[] { String.valueOf(user.getId()) });
        db.close();

        if(resutl == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean deleteUser(String userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        long resutl = db.delete(UserContract.FeedEntry.TABLE_NAME, UserContract.FeedEntry.ID + " = ?", new String[] { userId });
        db.close();

        if(resutl == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Users getUser(String userId) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(UserContract.FeedEntry.TABLE_NAME, null, UserContract.FeedEntry.ID + " = ?", new String[] { userId },null, null, null);
        if(cursor != null)
            cursor.moveToFirst();
        // String id, boolean isAdmin, String name, int age, String genDer, String imagePerson
        Users user = new Users(cursor.getString(0), Boolean.parseBoolean(cursor.getString(1)), cursor.getString(2), cursor.getInt(3), cursor.getString(4), cursor.getString(5));
        db.close();
        return user;
    }
}

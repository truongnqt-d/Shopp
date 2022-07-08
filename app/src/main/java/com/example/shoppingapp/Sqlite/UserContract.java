package com.example.shoppingapp.Sqlite;

import android.provider.BaseColumns;

public class UserContract {
    private UserContract() {}

    /* Inner class that defines the table contents */
    public static class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "users";

        public static final String ID = "id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_AGE = "age";
        public static final String COLUMN_GENDER = "gender";
        public static final String COLUMN_IMAGE = "image";
        public static final String COLUMN_ADMIN = "admin";
    }
}

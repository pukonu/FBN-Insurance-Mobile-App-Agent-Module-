package com.dataminersconsult.fbninsurance.lib;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

import static com.dataminersconsult.fbninsurance.lib.AppProvider.CONTENT_AUTHORITY;
import static com.dataminersconsult.fbninsurance.lib.AppProvider.CONTENT_AUTHORITY_URI;

public class UserContract {
    public static final String TABLE_NAME = "Users";

    // Task fields
    public static class Columns {
        public static final String _ID = BaseColumns._ID;
        public static final String USER_EMAIL = "email";
        public static final String USER_PASSWORD = "password";
        public static final String USER_FIRSTNAME = "first_name";
        public static final String USER_LASTNAME = "last_name";
        public static final String USER_MIDDLENAME = "middle_name";
        public static final String USER_TITLE = "title";
        public static final String USER_OCCUPATION = "occupation";
        public static final String USER_IMAGE = "image";

        private Columns() {
            // private constructor to prevent instantiation. Makes this a singleton class
        }
    }

    public static final Uri CONTENT_URI = Uri.withAppendedPath(CONTENT_AUTHORITY_URI, TABLE_NAME);

    static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd." + CONTENT_AUTHORITY + "." + TABLE_NAME;
    static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd." + CONTENT_AUTHORITY + "." + TABLE_NAME;

    public static Uri buildUserUri(long userId) {
        return ContentUris.withAppendedId(CONTENT_URI, userId);
    }

    static long getUserId(Uri uri) {
        return ContentUris.parseId(uri);
    }
}

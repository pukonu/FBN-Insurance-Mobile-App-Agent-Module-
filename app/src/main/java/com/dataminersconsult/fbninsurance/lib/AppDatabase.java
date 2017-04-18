package com.dataminersconsult.fbninsurance.lib;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Basic database class for the application
 * The only class that should use this is {@link AppProvider}
 */
public class AppDatabase extends SQLiteOpenHelper {
    private static final String TAG = "AppDatabase";

    public static final String DATABASE_NAME = "FBNInsurance.db";
    public static final int DATABASE_VERSION = 1;

    // implement AppDatabase as a Singleton
    private static AppDatabase instance = null;

    public AppDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Get an instance of the app's singleton database helper object
     * @param context the content provider context
     * @return a SQLite database helper object
     */
    static AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = new AppDatabase(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sSQL; // Use a String  variable to facilitate logging
        sSQL = "CREATE TABLE " + UserContract.TABLE_NAME + " ("
                + UserContract.Columns._ID + " INTEGER PRIMARY KEY NOT NULL, "
                + UserContract.Columns.USER_EMAIL + " TEXT, "
                + UserContract.Columns.USER_PASSWORD + " TEXT, "
                + UserContract.Columns.USER_FIRSTNAME + " TEXT, "
                + UserContract.Columns.USER_LASTNAME + " TEXT, "
                + UserContract.Columns.USER_MIDDLENAME + " TEXT, "
                + UserContract.Columns.USER_TITLE + " TEXT, "
                + UserContract.Columns.USER_OCCUPATION + " TEXT, "
                + UserContract.Columns.USER_IMAGE + " TEXT);";
        db.execSQL(sSQL);

        // TODO remove the line before production
        String qs = "INSERT INTO " + UserContract.TABLE_NAME +
                " (email, password, title, first_name, last_name, middle_name, occupation) " +
                "VALUES ('pukonu@gmail.com', 'password', 'Mr', 'Peter', 'Ukonu', '', 'Software Consultant');";
        db.execSQL(qs);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        switch (oldVersion) {
            case 1:
                // upgrade logic from version 1
                break;
            default:
                throw new IllegalStateException("onUpgrade() with unknown newVersion" + newVersion);
        }
    }
}

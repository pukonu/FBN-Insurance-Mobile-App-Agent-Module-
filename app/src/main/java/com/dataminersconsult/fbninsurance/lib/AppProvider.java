package com.dataminersconsult.fbninsurance.lib;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Provider for the FBN Insurance app. This is the only class that knows about {@link AppDatabase}
 */
public class AppProvider extends ContentProvider {
    private static final String TAG = "AppProvider";

    private AppDatabase mOpenHelper;
    public static final UriMatcher sUriMatcher = buildUriMatcher();
    static final String CONTENT_AUTHORITY = "com.dataminersconsult.fbninsurance.provider";
    public static final Uri CONTENT_AUTHORITY_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    private static final int USER = 100;
    private static final int USER_ID = 101;

    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

        // e.g. content://com.prologic.tasktimer.provide/Tasks
        matcher.addURI(CONTENT_AUTHORITY, UserContract.TABLE_NAME, USER);
        // e.g. content://com.prologic.tasktimer.provide/Tasks/8
        matcher.addURI(CONTENT_AUTHORITY, UserContract.TABLE_NAME + "/#", USER_ID);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = AppDatabase.getInstance(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Log.d(TAG, "query: called with URI " + uri);
        final int match = sUriMatcher.match(uri);
        Log.d(TAG, "query: match is " + match);

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        switch(match) {
            case USER:
                queryBuilder.setTables(UserContract.TABLE_NAME);
                break;
            case USER_ID:
                queryBuilder.setTables(UserContract.TABLE_NAME);
                long taskId = UserContract.getUserId(uri);
                queryBuilder.appendWhere(UserContract.Columns._ID + " = " + taskId);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        SQLiteDatabase db = mOpenHelper.getReadableDatabase();
//        return queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        Log.d(TAG, "query: rows in returned cursor = " + cursor.getCount());   // TODO remove this line

        if (getContext() != null) {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
            return cursor;
        } else {
            return null;
        }
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);

        switch(match) {
            case USER:
                return UserContract.CONTENT_TYPE;

            case USER_ID:
                return UserContract.CONTENT_ITEM_TYPE;

            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        Log.d(TAG, "Entering insert, called with uri: " + uri);
        final int match = sUriMatcher.match(uri);
        Log.d(TAG, "match is " + match);

        final SQLiteDatabase db;

        Uri returnUri;
        long recordId;

        switch (match) {
            case USER:
                db = mOpenHelper.getWritableDatabase();
                recordId = db.insert(UserContract.TABLE_NAME, null, contentValues);
                if (recordId >= 0) {
                    returnUri = UserContract.buildUserUri(recordId);
                } else {
                    throw new SQLException("Failed to insert into " + uri.toString());
                }
                break;

            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        if (recordId >= 0){
            // something must have been inserted
            Log.d(TAG, "insert: setting notifyChanged with " + uri);
            if (getContext() != null) {
                getContext().getContentResolver().notifyChange(uri, null);
            }
        } else {
            Log.d(TAG, "insert: nothing inserted");
        }

        Log.d(TAG, "Exiting insert " + returnUri);
        return returnUri;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        Log.d(TAG, "update called with uri " + uri);
        final int match = sUriMatcher.match(uri);
        Log.d(TAG, "match is " + match);

        final  SQLiteDatabase db;
        int count;

        String selectionCriteria;

        switch (match) {
            case USER:
                db = mOpenHelper.getWritableDatabase();
                count = db.update(UserContract.TABLE_NAME, contentValues, selection, selectionArgs);
                break;

            case USER_ID:
                db = mOpenHelper.getWritableDatabase();
                long taskId = UserContract.getUserId(uri);
                selectionCriteria = UserContract.Columns._ID + " = " + taskId;

                if (selection != null && selection.length() > 0) {
                    selectionCriteria += " AND (" + selection + ")";
                }
                count = db.update(UserContract.TABLE_NAME, contentValues, selectionCriteria, selectionArgs);
                break;

            default:
                throw new IllegalArgumentException("Unknown uri " + uri);
        }

        if (count > 0){
            // somethingwas deleted
            Log.d(TAG, "update: setting notifyChanged with " + uri);
            if (getContext() != null) {
                getContext().getContentResolver().notifyChange(uri, null);
            }
        } else {
            Log.d(TAG, "update: nothing updated");
        }

        Log.d(TAG, "Exiting update, returning " + count);
        return count;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Log.d(TAG, "delete called with uri " + uri);
        final int match = sUriMatcher.match(uri);
        Log.d(TAG, "match is " + match);

        final  SQLiteDatabase db;
        int count;

        String selectionCriteria;

        switch (match) {
            case USER:
                db = mOpenHelper.getWritableDatabase();
                count = db.delete(UserContract.TABLE_NAME, selection, selectionArgs);
                break;

            case USER_ID:
                db = mOpenHelper.getWritableDatabase();
                long taskId = UserContract.getUserId(uri);
                selectionCriteria = UserContract.Columns._ID + " = " + taskId;

                if (selection != null && selection.length() > 0) {
                    selectionCriteria += " AND (" + selection + ")";
                }
                count = db.delete(UserContract.TABLE_NAME, selectionCriteria, selectionArgs);
                break;

            default:
                throw new IllegalArgumentException("Unknown uri " + uri);
        }

        if (count > 0){
            // something was deleted
            Log.d(TAG, "delete: setting notifyChanged with " + uri);
            if (getContext() != null) {
                getContext().getContentResolver().notifyChange(uri, null);
            }
        } else {
            Log.d(TAG, "delete: nothing deleted");
        }

        Log.d(TAG, "Exiting delete, returning " + count);
        return count;
    }
}

package com.dataminersconsult.fbninsurance;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.dataminersconsult.fbninsurance.lib.AppProvider;
import com.dataminersconsult.fbninsurance.lib.UserContract;

public class WelcomeActivity extends AppCompatActivity {
    private static final String TAG = "WelcomeActivity";

    public static final long DEFAULT_TABLE_ID = 1L;

    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewPlaceholder;

    public static final long TMP_ID = 1L;
    public static final String TMP_EMAIL = "pukonu@gmail.com";
    public static final String TMP_PASSWORD = "password";
    public static final String TMP_FIRSTNAME = "Peter";
    public static final String TMP_LASTNAME = "Ukonu";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        // setup UI for data collection
        editTextEmail = (EditText) findViewById(R.id.wa_edit_email);
        editTextPassword = (EditText) findViewById(R.id.wa_edit_password);
        textViewPlaceholder = (TextView) findViewById(R.id.wa_label_login_error);
        TextView textViewLogin = (TextView) findViewById(R.id.wa_label_login);

        editTextEmail.setText(TMP_EMAIL);           // TODO remove this line, autofill login for debugging
        editTextPassword.setText(TMP_PASSWORD);     // TODO remove this line, autofill login for debugging
        textViewPlaceholder.setVisibility(View.GONE);

        /*
        initialize app database
        let us throw in a sample user record for user to successfully login
        this record will only be entered if not exists
         */
        final ContentResolver contentResolver = this.getContentResolver();
        ContentValues contentValues = new ContentValues();
        contentValues.put(UserContract.Columns.USER_EMAIL, TMP_EMAIL);
        contentValues.put(UserContract.Columns.USER_PASSWORD, TMP_PASSWORD);
        contentValues.put(UserContract.Columns.USER_FIRSTNAME, TMP_FIRSTNAME);
        contentValues.put(UserContract.Columns.USER_LASTNAME, TMP_LASTNAME);

        String paramWhere = UserContract.Columns.USER_EMAIL + "=?";
        String[] paramSelection = new String[]{TMP_EMAIL};

        contentResolver.delete(UserContract.CONTENT_URI, paramWhere, paramSelection);
        contentResolver.insert(UserContract.CONTENT_URI, contentValues);

        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] projections = new String[] {UserContract.Columns._ID, UserContract.Columns.USER_EMAIL};
                String paramWhere = UserContract.Columns.USER_EMAIL + "=? AND " + UserContract.Columns.USER_PASSWORD + "=?";
                String[] paramSelection = new String[]{editTextEmail.getText().toString(), editTextPassword.getText().toString()};
                Cursor cursor = getContentResolver().query(UserContract.buildUserUri(TMP_ID), projections, paramWhere, paramSelection, null);
                if (cursor != null) {
                    int cursorCount = cursor.getCount();
                    cursor.close();
                    Log.d(TAG, String.format("Cursor returned %s object(s)", cursorCount));

                    if (cursorCount >= 1) {
                        Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        textViewPlaceholder.setVisibility(View.VISIBLE);
                        textViewPlaceholder.setText(R.string.str_error_username_password);
                    }
                } else {
                    throw new IllegalArgumentException("Cursor is empty???");
                }
            }
        });
    }
}
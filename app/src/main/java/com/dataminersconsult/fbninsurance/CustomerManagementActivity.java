package com.dataminersconsult.fbninsurance;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.dataminersconsult.customviews.ListViewStyleA;
import com.dataminersconsult.fbninsurance.lib.UserContract;
import com.dataminersconsult.fbninsurance.lib_customer_management.CustomerAdapter;
import com.dataminersconsult.fbninsurance.lib_customer_management.CustomerList;

import java.util.ArrayList;

public class CustomerManagementActivity extends AppCompatActivity {

    private ListViewStyleA listViewStyleA;
    private ArrayList<CustomerList> customerLists;
    private CustomerAdapter customerAdapter;

    private static final String TAG = "CustomerManagementActiv";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cma);

        // set some view options
        ImageView backButton = (ImageView) findViewById(R.id.button_back_toolbar_cma);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // query from database via content provider
        customerLists = new ArrayList<CustomerList>();

        String[] projections = new String[]{
                UserContract.Columns._ID,
                UserContract.Columns.USER_EMAIL,
                UserContract.Columns.USER_FIRSTNAME,
                UserContract.Columns.USER_LASTNAME,
                UserContract.Columns.USER_MIDDLENAME,
                UserContract.Columns.USER_OCCUPATION,
                UserContract.Columns.USER_TITLE,
                UserContract.Columns.USER_IMAGE,
        };
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(UserContract.CONTENT_URI,
                projections,
                null,
                null,
                null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                CustomerList customerItem = new CustomerList(
                        cursor.getString(cursor.getColumnIndex(UserContract.Columns.USER_EMAIL)),
                        cursor.getString(cursor.getColumnIndex(UserContract.Columns.USER_TITLE)),
                        cursor.getString(cursor.getColumnIndex(UserContract.Columns.USER_FIRSTNAME)),
                        cursor.getString(cursor.getColumnIndex(UserContract.Columns.USER_LASTNAME)),
                        cursor.getString(cursor.getColumnIndex(UserContract.Columns.USER_MIDDLENAME)),
                        cursor.getString(cursor.getColumnIndex(UserContract.Columns.USER_OCCUPATION)),
                        cursor.getString(cursor.getColumnIndex(UserContract.Columns.USER_IMAGE))
                );

                customerLists.add(customerItem);
            }
            cursor.close();
//                ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, R.layout.contact_detail, contacts);
//                contactNames.setAdapter(adapter);

            customerAdapter = new CustomerAdapter(CustomerManagementActivity.this, customerLists) {};
            listViewStyleA = (ListViewStyleA) findViewById(R.id.cma_listview);
            listViewStyleA.setAdapter(customerAdapter);
        }

    }
}

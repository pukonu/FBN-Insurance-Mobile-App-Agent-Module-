package com.dataminersconsult.fbninsurance;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private LinearLayout btnOnBoarding;
    private LinearLayout btnCustomer;
    private LinearLayout btnQuotation;
    private LinearLayout btnProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnOnBoarding = (LinearLayout) findViewById(R.id.linear_button_onboarding);
        btnCustomer = (LinearLayout) findViewById(R.id.linear_button_view_customers);
        btnQuotation = (LinearLayout) findViewById(R.id.linear_button_get_quotation);
        btnProduct = (LinearLayout) findViewById(R.id.linear_button_view_product);
        
        // set onClick listeners
        btnOnBoarding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentOnBoarding = new Intent(MainActivity.this, OnboardingActivity.class);
                startActivity(intentOnBoarding);
            }
        });

        btnCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentCustomerManagement = new Intent(MainActivity.this, CustomerManagementActivity.class);
                startActivity(intentCustomerManagement);
            }
        });

        btnQuotation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentQuotation = new Intent(MainActivity.this, QuotationActivity.class);
                startActivity(intentQuotation);
            }
        });
    }

}

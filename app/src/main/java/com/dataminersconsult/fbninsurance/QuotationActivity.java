package com.dataminersconsult.fbninsurance;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;

import com.dataminersconsult.customviews.EditTextStyleA;
import com.dataminersconsult.customviews.TextViewStyleA;
import com.dataminersconsult.fbninsurance.lib_quotation.QuotationFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class QuotationActivity extends AppCompatActivity {

    private EditTextStyleA dob;
    private final Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotation);

        // policy categories spinner
        ArrayList<String> spinnerCategoryArray = new ArrayList<String>();
        for (QuotationFactory.POLICY_CATEGORIES title : QuotationFactory.POLICY_CATEGORIES.values()) {
            spinnerCategoryArray.add(title.toString());
        }
        ArrayAdapter<String> spinnerAdapterCategory =
                new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerCategoryArray);
        Spinner spinnerCategory = (Spinner) findViewById(R.id.spinner_q_policy_category);
        spinnerCategory.setAdapter(spinnerAdapterCategory);

        // policy types spinner
        ArrayList<String> spinnerTypeArray = new ArrayList<String>();
        for (QuotationFactory.POLICY_TYPES title : QuotationFactory.POLICY_TYPES.values()) {
            spinnerTypeArray.add(title.toString());
        }
        ArrayAdapter<String> spinnerAdapterType =
                new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerTypeArray);
        Spinner spinnerType = (Spinner) findViewById(R.id.spinner_q_policy_type);
        spinnerType.setAdapter(spinnerAdapterType);

        // datepicker
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        dob = (EditTextStyleA) findViewById(R.id.edittext_q_dob);
        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(QuotationActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        // payment frequency spinner
        ArrayList<String> spinnerFrequencyArray = new ArrayList<String>();
        for (QuotationFactory.PAYMENT_FREQUENCY title : QuotationFactory.PAYMENT_FREQUENCY.values()) {
            spinnerFrequencyArray.add(title.toString());
        }
        ArrayAdapter<String> spinnerAdapterFrequency =
                new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerFrequencyArray);
        Spinner spinnerFrequency = (Spinner) findViewById(R.id.spinner_q_frequency);
        spinnerFrequency.setAdapter(spinnerAdapterFrequency);

        // payment duration spinner
        ArrayList<String> spinnerDurationArray = new ArrayList<String>();
        for (int i : QuotationFactory.POLICY_DURATION) {
            spinnerDurationArray.add(String.format("%s", i));
        }
        ArrayAdapter<String> spinnerAdapterDuration =
                new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerDurationArray);
        final Spinner spinnerDuration = (Spinner) findViewById(R.id.spinner_q_duration);
        spinnerDuration.setAdapter(spinnerAdapterDuration);

        // add on click listener to button
        TextViewStyleA getQuotation = (TextViewStyleA) findViewById(R.id.q_get_quotation);

        getQuotation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double result = 0;
                int duration = Integer.parseInt(spinnerDuration.getSelectedItem().toString());
                result = duration * 1594.56;
                String resultStr = String.format(Locale.ENGLISH, "%,.2f", result);
                TextViewStyleA resultLabel = (TextViewStyleA) findViewById(R.id.label_q_amount_result);
                resultLabel.setText(String.format("%s", result));
            }
        });
    }

    private void updateLabel() {
        String myFormat = "dd/MM/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        dob.setText(sdf.format(myCalendar.getTime()));
    }
}

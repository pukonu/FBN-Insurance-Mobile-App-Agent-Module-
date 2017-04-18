package com.dataminersconsult.fbninsurance.lib_onboarding;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.dataminersconsult.fbninsurance.OnboardingActivity;
import com.dataminersconsult.fbninsurance.R;

import java.util.ArrayList;

public class FragStep1 extends Fragment {

    private static final String TAG = "FragStep1";

    public static final int LISTENER_ON = 1;
    public static final int LISTENER_OFF = 0;
    public static final int FORM_ROW_SINGLE = R.layout.form_row_single;
    public static final int FORM_ROW_SELECT = R.layout.form_row_select;
    private EditText[] et = new EditText[100];
    private Spinner[] spinner = new Spinner[100];
    private SharedPreferences prefs;

    private CustomerFactory customerFactory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        OnboardingActivity activity = (OnboardingActivity) getActivity();
        customerFactory = activity.mCustomerFactory;
        prefs = getContext().getSharedPreferences("ON_BOARDING", Context.MODE_PRIVATE);

        return buildViews(inflater);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        OnboardingActivity activity = (OnboardingActivity) getActivity();
        customerFactory = activity.mCustomerFactory;
        outState.putSerializable("CUSTOMER_FACTORY", customerFactory);
        prefs = getContext().getSharedPreferences("ON_BOARDING", Context.MODE_PRIVATE);

        for (FormFactory.FIELDS fields : FormFactory.FIELDS.values()) {
            String[] propertyBuild = FormFactory.getFieldProperties(fields.toString());
            String slug = fields.toString();
            int id = Integer.parseInt(propertyBuild[0]);
            int formType = Integer.parseInt(propertyBuild[3]);

            String value = "";

            if (formType == FormFactory.FORM_TYPE_SELECT) {
                value = spinner[id].getSelectedItem().toString();
            } else {
                value = et[id].getText().toString();
            }

            customerFactory.generic(value, slug, CustomerFactory.PUT, prefs);
        }
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        LayoutInflater layoutInflater = getLayoutInflater(savedInstanceState);
        if (savedInstanceState != null){
            customerFactory = (CustomerFactory) savedInstanceState.getSerializable("CUSTOMER_FACTORY");
            if (customerFactory != null) {
                prefs = getContext().getSharedPreferences("ON_BOARDING", Context.MODE_PRIVATE);
                for (FormFactory.FIELDS fields : FormFactory.FIELDS.values()) {
                    String[] propertyBuild = FormFactory.getFieldProperties(fields.toString());
                    String slug = fields.toString();
                    int id = Integer.parseInt(propertyBuild[0]);
                    int formType = Integer.parseInt(propertyBuild[3]);
                    String value = customerFactory.generic(null, slug, CustomerFactory.GET, prefs);

                    if (formType == FormFactory.FORM_TYPE_SELECT) {
                        spinner[id].setSelection(FormFactory.SELECT_TITLE.valueOf(value).ordinal());
                    } else {
                        et[id].setText(value);
                    }
                }
            }
        }
    }

    public ScrollView buildViews(LayoutInflater inflater){
        LinearLayout layout = new LinearLayout(getActivity());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layout.setScrollY(LinearLayout.VERTICAL);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(layoutParams);

        for (FormFactory.FIELDS fields : FormFactory.FIELDS.values()) {
            String[] propertyBuild = FormFactory.getFieldProperties(fields.toString());
            String slug = fields.toString();

            layout.addView(buildFormRow(inflater, slug, propertyBuild));
        }

        ScrollView scrollView = new ScrollView(getActivity());
        scrollView.setLayoutParams(layoutParams);
        scrollView.addView(layout);

        return scrollView;
    }

    public View buildFormRow(LayoutInflater inflater, final String slug, String[] propertyBuild ) {

        int id = Integer.parseInt(propertyBuild[0]);
        String label = propertyBuild[1];
        String hintText = propertyBuild[2];
        int formType = Integer.parseInt(propertyBuild[3]);

        View view;
        TextView tv;

        if (formType == FormFactory.FORM_TYPE_SELECT) {
            view = inflater.inflate(FORM_ROW_SELECT, null);
            tv = (TextView) view.findViewById(R.id.label_oa_frag1_select);
            tv.setText(label);

            ArrayList<String> spinnerArray = new ArrayList<String>();
            for (FormFactory.SELECT_TITLE title : FormFactory.SELECT_TITLE.values()) {
                spinnerArray.add(title.toString());
            }

            ArrayAdapter<String> spinnerAdapter =
                    new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, spinnerArray);

            spinner[id] = (Spinner) view.findViewById(R.id.spinner_oa_frag1_select);
            spinner[id].setAdapter(spinnerAdapter);

            String value = customerFactory.generic(null, slug, CustomerFactory.GET, prefs);
            try {
                spinner[id].setSelection(FormFactory.SELECT_TITLE.valueOf(value).ordinal());
            } catch (IllegalArgumentException e) {
//                Log.e(TAG, "buildFormRow: " + e.toString(), e);
            }
        } else {
                view = inflater.inflate(FORM_ROW_SINGLE, null);
                tv = (TextView) view.findViewById(R.id.label_oa_frag1_generic);
                tv.setText(label);
                et[id] = (EditText) view.findViewById(R.id.edittext_oa_frag1_generic);
                et[id].setHint(hintText);

                CharSequence value = customerFactory.generic(null, slug, CustomerFactory.GET, prefs);
                et[id].setText(value);
        }

        return view;
    }
}

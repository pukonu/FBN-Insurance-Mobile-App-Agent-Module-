package com.dataminersconsult.fbninsurance.lib_onboarding;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dataminersconsult.customviews.EditTextStyleA;
import com.dataminersconsult.customviews.TextViewStyleA;
import com.dataminersconsult.fbninsurance.OnboardingActivity;
import com.dataminersconsult.fbninsurance.R;

import co.paystack.android.Paystack;
import co.paystack.android.PaystackSdk;
import co.paystack.android.model.Card;
import co.paystack.android.model.Charge;
import co.paystack.android.model.Transaction;

public class FragStep4 extends Fragment {

    private static final String TAG = "FragStep2";
    private CustomerFactory customerFactory;
    private SharedPreferences prefs;

    // TODO remove these default values
    private String cardNumber = "4123450131001381";
    private int month = 9;
    private int year = 2019;
    private String cvv2 = "883";
    private String email = "pukonu@gmail.com";
    private int amount = 10000;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_onboarding_frag4, container, false);

        final OnboardingActivity activity = (OnboardingActivity) getActivity();
        customerFactory = activity.mCustomerFactory;
        prefs = getContext().getSharedPreferences("ON_BOARDING", Context.MODE_PRIVATE);

        EditTextStyleA cardNumberView = (EditTextStyleA) view.findViewById(R.id.edittext_oa_frag4_card);
        EditTextStyleA monthView = (EditTextStyleA) view.findViewById(R.id.edittext_oa_frag4_edittext_oa_frag4_month);
        EditTextStyleA yearView = (EditTextStyleA) view.findViewById(R.id.edittext_oa_frag4_year);
        EditTextStyleA cvv2View = (EditTextStyleA) view.findViewById(R.id.edittext_oa_frag4_cvv2);
        EditTextStyleA emailView = (EditTextStyleA) view.findViewById(R.id.edittext_oa_frag4_email);
        EditTextStyleA amountView = (EditTextStyleA) view.findViewById(R.id.edittext_oa_frag4_amount);

        cardNumberView.setText(cardNumber);
        monthView.setText(String.format("%s", month));
        yearView.setText(String.format("%s", year));
        cvv2View.setText(cvv2);
        emailView.setText(email);
        amountView.setText(String.format("%s", amount));

        TextViewStyleA save = (TextViewStyleA) view.findViewById(R.id.oa_save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customerFactory.saveCustomerInformation(getActivity(), prefs);
                getActivity().finish();
            }
        });

        return view;
    }
}

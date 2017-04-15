package com.dataminersconsult.fbninsurance.OnboardingFragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.dataminersconsult.customfonts.TextViewStyleA;
import com.dataminersconsult.fbninsurance.OnboardingActivity;
import com.dataminersconsult.fbninsurance.R;

public class FragStep4 extends Fragment {

    private static final String TAG = "FragStep2";
    private CustomerFactory customerFactory;
    private SharedPreferences prefs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: started");
        View view = inflater.inflate(R.layout.activity_onboarding_frag4, container, false);

        OnboardingActivity activity = (OnboardingActivity) getActivity();
        customerFactory = activity.mCustomerFactory;
        prefs = getContext().getSharedPreferences("ON_BOARDING", Context.MODE_PRIVATE);

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

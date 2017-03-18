package com.dataminersconsult.fbninsurance.OnboardingFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dataminersconsult.fbninsurance.R;

public class FragStep2 extends Fragment {

    private static final String TAG = "FragStep2";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: started");
        return inflater.inflate(R.layout.activity_onboarding_frag2, container, false);
    }
}

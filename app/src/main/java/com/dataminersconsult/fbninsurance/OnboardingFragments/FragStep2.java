package com.dataminersconsult.fbninsurance.OnboardingFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.dataminersconsult.fbninsurance.R;

public class FragStep2 extends Fragment {

    private static final String TAG = "FragStep2";

    public static final int FORM_TYPE_SINGLE = 1;
    public static final int FORM_ROW_SINGLE = R.layout.form_row_single;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        Log.d(TAG, "onCreateView: started");

        LinearLayout layout = new LinearLayout(getActivity());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layout.setScrollY(LinearLayout.VERTICAL);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(layoutParams);

        // first name
        View editView01 = buildFormRow(inflater, FORM_TYPE_SINGLE, "Duration");
        View editView02 = buildFormRow(inflater, FORM_TYPE_SINGLE, "Premium Accompanying Proposal");
//        View editView03 = buildFormRow(inflater, FORM_TYPE_SINGLE, "Sum Assured");
//        View editView04 = buildFormRow(inflater, FORM_TYPE_SINGLE, "Premium");
//        View editView05 = buildFormRow(inflater, FORM_TYPE_SINGLE, "Inflation Protector");
//        View editView06 = buildFormRow(inflater, FORM_TYPE_SINGLE, "Method of Regular Premium Payment");
//        View editView07 = buildFormRow(inflater, FORM_TYPE_SINGLE, "Mode of Payment");
//        View editView08 = buildFormRow(inflater, FORM_TYPE_SINGLE, "Have you ever been denied a life assurance policy");
//        View editView09 = buildFormRow(inflater, FORM_TYPE_SINGLE, "If yes please provide details");
//        View editView10 = buildFormRow(inflater, FORM_TYPE_SINGLE, "Are you in good health");
//        View editView11 = buildFormRow(inflater, FORM_TYPE_SINGLE, "Have you been to the hospital in the last 6 months");
//        View editView12 = buildFormRow(inflater, FORM_TYPE_SINGLE, "What is your height");
//        View editView13 = buildFormRow(inflater, FORM_TYPE_SINGLE, "What is your weight");
//        View editView14 = buildFormRow(inflater, FORM_TYPE_SINGLE, "What is your daily consumption of Tabacco/Alcohol");
//        View editView15 = buildFormRow(inflater, FORM_TYPE_SINGLE, "Have your ever suffered from any infections, the kidney, hear, urinary organs, or blood in urine");
//        View editView16 = buildFormRow(inflater, FORM_TYPE_SINGLE, "Are you suffering from any genetic disease (cancer, sickly and/or diabeties");
//        View editView17 = buildFormRow(inflater, FORM_TYPE_SINGLE, "If you have answered YES to all answers above please provide details");

        layout.addView(editView01);
        layout.addView(editView02);
//        layout.addView(editView03);
//        layout.addView(editView04);
//        layout.addView(editView05);
//        layout.addView(editView06);
//        layout.addView(editView07);
//        layout.addView(editView08);
//        layout.addView(editView09);
//        layout.addView(editView10);
//        layout.addView(editView11);
//        layout.addView(editView12);
//        layout.addView(editView13);
//        layout.addView(editView14);
//        layout.addView(editView15);
//        layout.addView(editView16);
//        layout.addView(editView17);

        ScrollView scrollView = new ScrollView(getActivity());
        scrollView.setLayoutParams(layoutParams);
        scrollView.addView(layout);

        return scrollView;
    }

    public View buildFormRow(LayoutInflater inflater, int type, String label) {
        View view = inflater.inflate(FORM_ROW_SINGLE, null);
        TextView tv = (TextView) view.findViewById(R.id.label_oa_frag1_generic);
        EditText et = (EditText) view.findViewById(R.id.edittext_oa_frag1_generic);
        tv.setText(label);

        return view;
    }
}

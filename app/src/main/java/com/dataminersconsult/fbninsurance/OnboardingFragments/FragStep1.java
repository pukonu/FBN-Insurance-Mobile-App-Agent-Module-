package com.dataminersconsult.fbninsurance.OnboardingFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.dataminersconsult.customfonts.TextViewStyleA;
import com.dataminersconsult.fbninsurance.R;

import org.w3c.dom.Text;

public class FragStep1 extends Fragment {

    private static final String TAG = "FragStep1";

    public static final int FORM_TYPE_SINGLE = 1;
    public static final int FORM_ROW_SINGLE = R.layout.form_row_single;
    private EditText firstnameEditText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: started");

        LinearLayout layout = new LinearLayout(getActivity());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layout.setScrollY(LinearLayout.VERTICAL);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(layoutParams);

        // first name
        View firstnameView = buildFormRow(inflater, FORM_TYPE_SINGLE, "First Name");
        View lastnameView = buildFormRow(inflater, FORM_TYPE_SINGLE, "Last Name");
        View middlenameView = buildFormRow(inflater, FORM_TYPE_SINGLE, "Middle Name");
        View titleView = buildFormRow(inflater, FORM_TYPE_SINGLE, "Title");
        View occupationView = buildFormRow(inflater, FORM_TYPE_SINGLE, "Occupation");
        View businessTypeView = buildFormRow(inflater, FORM_TYPE_SINGLE, "Business Type");
        View poBoxView = buildFormRow(inflater, FORM_TYPE_SINGLE, "P. O. Box");
        View buildingNumberView = buildFormRow(inflater, FORM_TYPE_SINGLE, "Building Number");
        View streetNameView = buildFormRow(inflater, FORM_TYPE_SINGLE, "Street Name");
        View cityView = buildFormRow(inflater, FORM_TYPE_SINGLE, "City");
        View stateView = buildFormRow(inflater, FORM_TYPE_SINGLE, "State");
        View emailView = buildFormRow(inflater, FORM_TYPE_SINGLE, "Email");
        View phoneMobileView = buildFormRow(inflater, FORM_TYPE_SINGLE, "Phone (Mobile)");
        View phoneWorkView = buildFormRow(inflater, FORM_TYPE_SINGLE, "Phone (Work)");
        View identificationTypeView = buildFormRow(inflater, FORM_TYPE_SINGLE, "Identification Type");
        View evidenceOfAddressView = buildFormRow(inflater, FORM_TYPE_SINGLE, "Evidence of Address");
        View idNumberView = buildFormRow(inflater, FORM_TYPE_SINGLE, "ID Number");
        View dateOfBirthView = buildFormRow(inflater, FORM_TYPE_SINGLE, "Date of Birth");
        View maritalStatusView = buildFormRow(inflater, FORM_TYPE_SINGLE, "Marital Status");
        View nationalityView = buildFormRow(inflater, FORM_TYPE_SINGLE, "Nationality");
        View stateOfOriginView = buildFormRow(inflater, FORM_TYPE_SINGLE, "State of Origin");
        View lgaView = buildFormRow(inflater, FORM_TYPE_SINGLE, "Local Government Area");
        View religionView = buildFormRow(inflater, FORM_TYPE_SINGLE, "Religion");

        layout.addView(firstnameView);
        layout.addView(lastnameView);
        layout.addView(middlenameView);
        layout.addView(titleView);
        layout.addView(occupationView);
        layout.addView(businessTypeView);
        layout.addView(poBoxView);
        layout.addView(buildingNumberView);
        layout.addView(streetNameView);
        layout.addView(cityView);
        layout.addView(stateView);
        layout.addView(emailView);
        layout.addView(phoneMobileView);
        layout.addView(phoneWorkView);
        layout.addView(identificationTypeView);
        layout.addView(evidenceOfAddressView);
        layout.addView(idNumberView);
        layout.addView(dateOfBirthView);
        layout.addView(maritalStatusView);
        layout.addView(nationalityView);
        layout.addView(stateOfOriginView);
        layout.addView(lgaView);
        layout.addView(religionView);

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

    private View buildFormRow(View view, String label) {
        TextView tv = (TextView) view.findViewById(R.id.label_oa_frag1_generic);
        EditText et = (EditText) view.findViewById(R.id.edittext_oa_frag1_generic);
        tv.setText(label);

        return view;
    }
}

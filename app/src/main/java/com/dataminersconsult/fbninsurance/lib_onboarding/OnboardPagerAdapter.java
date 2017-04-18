package com.dataminersconsult.fbninsurance.lib_onboarding;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import java.util.List;

public class OnboardPagerAdapter extends FragmentStatePagerAdapter {
    private static final String TAG = "OnboardPagerAdapter";

    private int mNumOfTabs;

    private Fragment currentFragment;
    private List<Fragment> mViewList;

    private static final int FRAGMENT1 = 0;
    private static final int FRAGMENT2 = 1;
    private static final int FRAGMENT3 = 2;
    private static final int FRAGMENT4 = 3;

    public OnboardPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        mNumOfTabs = NumOfTabs;
    }

    public void setFragment (Fragment fragment) {
        currentFragment = fragment;
        mViewList.add(fragment);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case FRAGMENT1:
                return new FragStep1();

            case FRAGMENT2:
                return new FragStep2();

            case FRAGMENT3:
                return new FragStep3();

            case FRAGMENT4:
                return new FragStep4();

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
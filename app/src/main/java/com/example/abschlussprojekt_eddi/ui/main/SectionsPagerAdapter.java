package com.example.abschlussprojekt_eddi.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.abschlussprojekt_eddi.R;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.Startseite, R.string.Kalender, R.string.Statistik };
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return Startseite_Fragment.newInstance("String1", "String2");
            case 1:
                return Kalender_Fragment.newInstance("String1", "String2");
            case 2:
                return Statistik_Fragment.newInstance("String1", "String2");
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        switch (position){
            case 0:
                return mContext.getResources().getString(TAB_TITLES[0]);
            case 1:
                return mContext.getResources().getString(TAB_TITLES[1]);
            case 2:
                return mContext.getResources().getString(TAB_TITLES[2]);
            default:
                return  null;
        }
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return TAB_TITLES.length;
    }
}
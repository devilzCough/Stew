package org.androidtown.stew;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by limjeonghyun on 2017. 12. 1..
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {

    FragmentManager fragmentManager;
    String[] tabs;

    public ViewPagerAdapter(FragmentManager fm, String[] tabs) {
        super(fm);
        fragmentManager = fm;
        this.tabs = tabs;
    }
    @Override
    public Fragment getItem(int i) {
        System.out.println(i);

        return FloorFragment.newInstance(1);
    }

    @Override
    public int getCount() {
        return tabs.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs[position];
    }
}
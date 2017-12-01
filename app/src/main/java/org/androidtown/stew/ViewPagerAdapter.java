package org.androidtown.stew;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by limjeonghyun on 2017. 12. 1..
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {

    String[] tabs;

    public ViewPagerAdapter(FragmentManager fm, String[] tabs) {
        super(fm);
        this.tabs = tabs;
    }

    @Override
    public Fragment getItem(int i) {
        return FloorFragment.newInstance(String.format("%s Fragment", tabs[i]));
    }

    @Override
    public int getCount() {
        return tabs.length;
    }
}

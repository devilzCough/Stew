package org.androidtown.stew;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabWidget;

/**
 * Created by limjeonghyun on 2017. 11. 30..
 */

public class RoomListFragment extends Fragment {

    private String[] tabs;
    FragmentTabHost tabHost;
    ViewPagerAdapter pagerAdapter;
    ViewPager viewPager;
    private TabWidget tabWidget;
    private HorizontalScrollView horizontalScrollView;


    //Mandatory Constructor
    public RoomListFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_room_list, container, false);

        viewPager = (ViewPager)view.findViewById(R.id.pager);
        tabHost = (FragmentTabHost)view.findViewById(android.R.id.tabhost);
        tabWidget = (TabWidget)view.findViewById(android.R.id.tabs);
        tabHost.setup(getActivity(), getChildFragmentManager(), R.id.realTabContent);

        initializeHorizontalTabs();
        initializeTabs();
        setupTabHost();

        pagerAdapter = new ViewPagerAdapter(getChildFragmentManager(), tabs);
        viewPager.setAdapter(pagerAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            /**
             * on swipe select the respective tab
             * */
            @Override
            public void onPageSelected(int position) {

                getActivity().invalidateOptionsMenu();
                tabHost.setCurrentTab(position);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                getActivity().invalidateOptionsMenu();
            }
        });

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                viewPager.setCurrentItem(tabHost.getCurrentTab());
                scrollToCurrentTab();
            }
        });
        return view;
    }

    private void initializeHorizontalTabs() {
        LinearLayout ll = (LinearLayout) tabWidget.getParent();
        horizontalScrollView = new HorizontalScrollView(getActivity());
        horizontalScrollView.setLayoutParams(new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT));
        ll.addView(horizontalScrollView, 0);
        ll.removeView(tabWidget);
        horizontalScrollView.addView(tabWidget);
        horizontalScrollView.setHorizontalScrollBarEnabled(false);
    }

    private void scrollToCurrentTab() {
        final int screenWidth = getActivity().getWindowManager().getDefaultDisplay().getWidth();
        final int leftX = tabWidget.getChildAt(tabHost.getCurrentTab()).getLeft();
        int newX = 0;

        newX = leftX + (tabWidget.getChildAt(tabHost.getCurrentTab()).getWidth() / 2) - (screenWidth / 2);
        if (newX < 0) {
            newX = 0;
        }
        horizontalScrollView.scrollTo(newX, 0);
    }
    private void initializeTabs() {
        tabs = new String[] { "7층", "4층", "1층", "세미나룸"};
    }

    private void setupTabHost() {

        for(int i=0; i<tabs.length; i++) {
            tabHost.addTab(tabHost.newTabSpec(String.format("%sTab", tabs[i].replace(" ","").toLowerCase())).setIndicator(tabs[i]), FloorFragment.class, null);
        }
    }
}

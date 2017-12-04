package org.androidtown.stew;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by limjeonghyun on 2017. 11. 30..
 */

public class InfoFragment extends Fragment {

    public ExpandableListView expandableListView; // ExpandableListView 변수 선언
    public StewInfoAdapter stewInfoAdapter; // 위 ExpandableListView를 받을 CustomAdapter(2번 class에 해당)를 선언

    public ArrayList<String> groupList; // ExpandableListView의 Parent 항목이 될 List 변수 선언
    // public ArrayList<ImageView> thumbnail;
    public ArrayList<String> manual, timeTable, contactUs; // ExpandableListView의 Child 항목이 될 List를 각각 선언
    public HashMap<String, ArrayList<String>> childList;
    // public String, ArrayList<String> childList;

    public InfoFragment(){
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_info, container, false);

        groupList = new ArrayList<String>();
        groupList.add("STEW 사용법");
        groupList.add("도서관 이용시간");
        groupList.add("STEW 이용문의");

        manual = new ArrayList<String>();
        manual.add(StewConstant.MANUAL);

        timeTable = new ArrayList<String>();
        timeTable.add(StewConstant.SEVENTH_F);
        timeTable.add(StewConstant.FOURTH_F);
        timeTable.add(StewConstant.FIRST_F);
        timeTable.add(StewConstant.SECOND_F);
        timeTable.add(StewConstant.JINGWAN);

        contactUs = new ArrayList<String>();
        contactUs.add(StewConstant.CONTACT_US);

        childList = new HashMap<String, ArrayList<String>>();
        childList.put(groupList.get(0), manual);
        childList.put(groupList.get(1), timeTable);
        childList.put(groupList.get(2), contactUs);
        /*childList.put(parentList.get(1), vegetables);
        childList.put(parentList.get(2), etc);*/

        expandableListView = (ExpandableListView) rootView.findViewById(R.id.elv_list);
        stewInfoAdapter = new StewInfoAdapter(getActivity().getApplicationContext(), groupList, childList);
        expandableListView.setAdapter(stewInfoAdapter);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
            }
        });
        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
            }
        });
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                return false;
            }
        });



        /*mGroupList = new ArrayList<String>();
        mChildList = new ArrayList<ArrayList<String>>();
        mChildListContent = new ArrayList<String>();

        mGroupList.add("STEW 사용법");
        mGroupList.add("도서관 이용시간");
        mGroupList.add("STEW 이용문의");

        mChildListContent.add("1");
        mChildListContent.add("2");
        mChildListContent.add("3");

        mChildList.add(mChildListContent);
        mChildList.add(mChildListContent);
        mChildList.add(mChildListContent);

        mListView.setAdapter(new BaseExpandableAdapter(this, mGroupList, mChildList));*/



        return rootView;
    }
}

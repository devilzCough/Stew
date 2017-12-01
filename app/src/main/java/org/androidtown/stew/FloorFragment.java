package org.androidtown.stew;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by limjeonghyun on 2017. 11. 30..
 */

public class FloorFragment extends Fragment{
    String label;
    public FloorFragment(){

    }

    public static FloorFragment newInstance(String fragmentLabel) {
        FloorFragment fragment = new FloorFragment();
        Bundle args = new Bundle();
        args.putString("label", fragmentLabel);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_floor, container, false);
        TextView tv = (TextView)view.findViewById(R.id.label);

        //If the fragment was created by the TabHost, return empty view
        if(getArguments() == null) return view;

        label = getArguments().getString("label", "");

        tv.setText(label);
        return view;
    }
}

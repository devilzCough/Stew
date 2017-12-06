package org.androidtown.stew;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

/**
 * Created by limjeonghyun on 2017. 11. 30..
 */

public class FloorFragment extends Fragment {

    int floorOrder;
    int nRoom[] = {13,11,6,4};
    int startNum[] = {1,14,25,1};
    String label;

    public FloorFragment() {
    }

    public static FloorFragment newInstance(int floor) {
        FloorFragment fragment = new FloorFragment();
        Bundle args = new Bundle();
        args.putInt("floor_num",floor);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        int col = 0;
        int roomNum;

        View view = inflater.inflate(R.layout.fragment_floor, container, false);
        TableLayout tableLayout = (TableLayout)view.findViewById(R.id.tableLayout);

        if (getArguments() == null) return view;

        floorOrder = getArguments().getInt("floor_num",0);

        TableRow.LayoutParams buttonLayout = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        buttonLayout.setMargins(15,10,15,10);

        for (int i=0;i<(nRoom[floorOrder]/3)+1;i++){
            TableRow tableRow = new TableRow(getActivity());
            tableRow.setGravity(View.TEXT_ALIGNMENT_CENTER);
            if(i == (nRoom[floorOrder]/3))
                col = nRoom[floorOrder]%3;
            else col = 3;

            for(int j=0;j<col;j++){
                roomNum = startNum[floorOrder]+(i*3)+j;

                Button button = new Button(getActivity());
                button.setText("Room "+roomNum);
                button.setTextSize(13);
                button.setHeight(120);
                button.setPadding(40,10,40,10);
                button.setTextColor(getResources().getColor(R.color.stewColor));
                button.setBackgroundResource(R.drawable.room_button);
                button.setLayoutParams(buttonLayout);
                tableRow.addView(button);
            }
            tableLayout.addView(tableRow);
        }

        Button goResvBtn = (Button) view.findViewById(R.id.btnGoResv);
        goResvBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ReservationActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

}

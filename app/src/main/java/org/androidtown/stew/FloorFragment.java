package org.androidtown.stew;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by limjeonghyun on 2017. 11. 30..
 */

public class FloorFragment extends Fragment {

    int floorOrder;
    int nRoom[] = {13,11,6,4};
    int startNum[] = {1,14,25,1};
    int roomID;
    JsoupProcess jsoupProcess;
    TableLayout tableLayout;
    Button goResvBtn;
    View view;

    Map<String,Integer> roomMap = new HashMap<String,Integer>();

    public FloorFragment(){
        System.out.println("1111");
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
        int col=0;
        int roomNum;

        view = inflater.inflate(R.layout.fragment_floor, container, false);
        if(getArguments() == null) return view;

        AppManager.getInstance().setFloorFragment(this);
        tableLayout = (TableLayout)view.findViewById(R.id.tableLayout);
        tableLayout.setVerticalScrollBarEnabled(true);

        goResvBtn = (Button) view.findViewById(R.id.btnGoResv);
        goResvBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ReservationActivity.class);
                startActivity(intent);
            }
        });

        final Spinner roomSpinner = (Spinner)view.findViewById(R.id.roomSpinner);
        jsoupProcess = AppManager.getInstance().getJsoupProcess();

        floorOrder = getArguments().getInt("floor_num",0);
        System.out.println(floorOrder);

        final List<String> roomList = new ArrayList<String>();
        roomList.add("스터디룸 선택");

        if(floorOrder == 3){
            roomList.add("교육실");
            roomList.add("진관홀 1");
            roomList.add("진관홀 2");
            roomList.add("진관홀 3");
        }
        else{
            for(int i=0;i<nRoom[floorOrder];i++){
                String roomNumString = "Room "+(startNum[floorOrder]+i);
                roomList.add(roomNumString);
            }
        }
        mapInit();
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(),R.layout.spin, roomList);
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spin_dropdown);
        roomSpinner.setAdapter(spinnerArrayAdapter);

        roomSpinner.setSelection(0);
        tableLayout.removeAllViews();

        roomSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                Toast.makeText(getActivity(),""+roomSpinner.getItemAtPosition(i), Toast.LENGTH_SHORT).show();
                if(i != 0 ) {
                    roomID = roomMap.get(roomSpinner.getItemAtPosition(i));
                    System.out.println(roomID);

                    tableLayout.removeAllViews();
                    jsoupProcess.tableInfo(roomID,7);
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        return view;
    }
    public void createTable(ArrayList<String> time,ArrayList<String> user){

        TableRow.LayoutParams buttonLayout = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        TableRow.LayoutParams textViewLayout = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        tableLayout.setBackgroundColor(getActivity().getResources().getColor(R.color.stew_back));
        ArrayList<String[]> userArrList = new ArrayList<String[]>();

        for(String s : user){
            s = s.replaceAll(" \\( ","\\(");
            s = s.replaceAll(" \\)","\\)");
            String[] userArr = s.split(" ");
            userArrList.add(userArr);
        }

        System.out.println(time);

        for(int i=0;i<time.size();i++){

            TableRow tableRow = new TableRow(getActivity());
            if(i==0) tableRow.setBackgroundColor(getActivity().getResources().getColor(R.color.stewColor));
            tableRow.setGravity(Gravity.CENTER);

            TextView textTime = new TextView(getActivity());
            if(i ==0) textTime.setText(time.get(i));
            else textTime.setText(time.get(i)+"시");
            textTime.setLayoutParams(textViewLayout);
            textTime.setGravity(Gravity.CENTER);
            tableRow.addView(textTime);

            for(int j=0;j<7;j++){
                if(i==0){
                    TextView textDay = new TextView(getActivity());
                    textDay.setText(userArrList.get(j)[i]);
                    textDay.setLayoutParams(textViewLayout);
                    textDay.setPadding(5,3,3,2);
                    tableRow.addView(textDay);
                }
                else if(userArrList.get(j).length < time.size()){
                    TextView textNot = new TextView(getActivity());
                    textNot.setText("X");
                    textNot.setBackgroundColor(getActivity().getResources().getColor(R.color.stew_back));
                    textNot.setLayoutParams(textViewLayout);
                    textNot.setGravity(Gravity.CENTER);
                    tableRow.addView(textNot);
                }
                else if(userArrList.get(j)[i].equals(time.get(i))){
                    Button button = new Button(getActivity());
                    button.setText("O");
                    button.setBackgroundDrawable(null);
                    button.setMinWidth(0);
                    button.setMinimumWidth(0);
                    button.setMinHeight(0);
                    button.setMinimumHeight(0);
                    button.setTextColor(getResources().getColor(R.color.stewColor));
                    button.setLayoutParams(buttonLayout);
                    tableRow.addView(button);
                }
                else{
                    TextView textNot = new TextView(getActivity());
                    textNot.setText("X");
                    textNot.setBackgroundColor(getActivity().getResources().getColor(R.color.stew_back));
                    textNot.setLayoutParams(textViewLayout);
                    textNot.setGravity(Gravity.CENTER);
                    tableRow.addView(textNot);
                }
            }
            tableLayout.addView(tableRow);
        }

        goResvBtn.setVisibility(View.VISIBLE);
        System.out.println("TEST");

        view.invalidate();
    }

    private void mapInit() {
        //7층
        roomMap.put("Room 1",11);
        roomMap.put("Room 2",10);
        roomMap.put("Room 3",12);
        roomMap.put("Room 4",13);
        roomMap.put("Room 5",14);
        roomMap.put("Room 6",15);
        roomMap.put("Room 7",1);
        roomMap.put("Room 8",2);
        roomMap.put("Room 9",3);
        roomMap.put("Room 10",9);
        roomMap.put("Room 11",4);
        roomMap.put("Room 12",5);
        roomMap.put("Room 13",16);
        //4층
        roomMap.put("Room 14",23);
        roomMap.put("Room 15",24);
        roomMap.put("Room 16",25);
        roomMap.put("Room 17",26);
        roomMap.put("Room 18",27);
        roomMap.put("Room 19",28);
        roomMap.put("Room 20",29);
        roomMap.put("Room 21",30);
        roomMap.put("Room 22",31);
        roomMap.put("Room 23",32);
        roomMap.put("Room 24",33);
        //1층
        roomMap.put("Room 25",17);
        roomMap.put("Room 26",18);
        roomMap.put("Room 27",19);
        roomMap.put("Room 28",20);
        roomMap.put("Room 29",21);
        roomMap.put("Room 30",22);
        //세미나
        roomMap.put("교육실",8);
        roomMap.put("진관홀 1",34);
        roomMap.put("진관홀 2",35);
        roomMap.put("진관홀 3",36);

        AppManager.getInstance().setRoomMap(roomMap);
    }

}

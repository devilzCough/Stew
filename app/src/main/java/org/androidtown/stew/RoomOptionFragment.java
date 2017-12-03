package org.androidtown.stew;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by limjeonghyun on 2017. 11. 30..
 */

public class RoomOptionFragment extends Fragment {

    final String[] strDate = new String[7];
    final String[] strHour = {"1시간", "2시간"};
    final String[] strTimeBound = {};


    Spinner dateSpin, hourSpin, timeSpin;

    long nowDate;
    Date date;
    SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM dd일");

    /*Calendar c = Calendar.getInstance();
    SimpleDateFormat _clockDateFormat = new SimpleDateFormat("MMM ddd, EEE");*/
    // _clockDateFormat.format(c.getTime());

    public RoomOptionFragment(){
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_room_option, container, false);

        // Date Spinner
        long oneDay = 24 * 60 * 60 * 1000;
        // System.out.println(_clockDateFormat.format(c.getTime()));
        for (int i = 0; i < 7; i++) {

            nowDate = System.currentTimeMillis() + i * oneDay;

            date = new Date(nowDate);
            strDate[i] = "" + dateFormat.format(date);
        }
        dateSpin = rootView.findViewById(R.id.spinDate);
        ArrayAdapter dateAdapter = new ArrayAdapter( getActivity().getApplicationContext(), R.layout.spin, strDate );
        dateAdapter.setDropDownViewResource(
                R.layout.spin_dropdown);
        dateSpin.setAdapter(dateAdapter);

        // Hour Spinner
        hourSpin = rootView.findViewById(R.id.spinHour);
        ArrayAdapter hourAdapter = new ArrayAdapter( getActivity().getApplicationContext(), R.layout.spin, strHour);
        hourAdapter.setDropDownViewResource(
                R.layout.spin_dropdown);
        hourSpin.setAdapter(hourAdapter);

        // Time Spinner


        return rootView;
    }
}

package org.androidtown.stew;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class ReservationActivity extends AppCompatActivity {

    TextView txtRoom, txtDate;
    ArrayList<CustomReservCard> items;
    ImageButton btnAdd, btnSub;
    EditText etPurpose;
    Button btnResv;

    int nCount;
    ReservationInfo info;

    String floor,room,time,date;

    RecyclerView recyclerView;
    BackgroundWebview webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        Intent receivedIntent = getIntent();
        floor = receivedIntent.getStringExtra("floor");
        room = receivedIntent.getStringExtra("room");
        time = receivedIntent.getStringExtra("time");
        date = receivedIntent.getStringExtra("date");

        nCount = 1;
        txtRoom = (TextView) findViewById(R.id.txtRoom);
        txtDate = (TextView) findViewById(R.id.txtDate);
        etPurpose = (EditText) findViewById(R.id.txtPurpose);

        /* set room, date, time info */
        // test code
        info = new ReservationInfo(11);
        info.setYear(2017);
        info.setMonth(12);
        info.setDay(14);
        info.setStartHour(10);
        info.setHours(1);
        info.setPurpose("dd");


        txtRoom.setText("위  치 :  "+floor+" "+room);
        txtDate.setText("일  시 :  "+date + " "+time);

        recyclerView = (RecyclerView) findViewById(R.id.resvRecycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        items = new ArrayList<>();

        btnAdd = (ImageButton) findViewById(R.id.btnAdd);
        btnSub = (ImageButton) findViewById(R.id.btnSub);
        btnResv = (Button) findViewById(R.id.btnReservation);

        webView = new BackgroundWebview(this);
        AppManager.getInstance().setWebView(webView);

        // test code :: we have to get room id
        webView.load_reservationPage(11);

        createUserList();
        recyclerView.setAdapter(new ReservCardRecyclerAdapter(getApplicationContext(), items, R.id.resvView));
    }

    public void createUserList() {

        CustomReservCard item = new CustomReservCard(nCount);
        nCount++;

        /// 여기서 학번이랑 이름 입력정보를 가져올수있는가. item 에 데이터 저장 객체 체크해야겟
        items.add(item);
    }

    public void onReservBtnClicked(View v) {

        CustomDialog alert = new CustomDialog(this,0,"스터디룸 예약이 완료되었습니다.");
        alert.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                finish();
            }
        });
        // test code
        /*int year = 2017;
        int month = 12;
        int day = 7;
        int startHour = 10;
        int hours = 1;
        String purpose = etPurpose.getText().toString();*/

        // minimum input num check?
        // webView.web_reservation(info, items);
    }

    public void onAddBtnClicked(View v) {

        if (nCount == 2) {
            btnSub.setVisibility(View.VISIBLE);
        }
        createUserList();
        recyclerView.setAdapter(new ReservCardRecyclerAdapter(getApplicationContext(), items, R.id.resvView));
        // System.out.println(nCount);
    }

    public void onSubBtnClicked(View v) {

        int size = items.size();
        items.remove(size - 1);

        nCount--;
        if (nCount == 2) {
            btnSub.setVisibility(View.GONE);
        }
        recyclerView.setAdapter(new ReservCardRecyclerAdapter(getApplicationContext(), items, R.id.resvView));
        // System.out.println(nCount);
    }
}

package org.androidtown.stew;

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

    RecyclerView recyclerView;
    BackgroundWebview webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        nCount = 1;
        txtRoom = (TextView) findViewById(R.id.txtRoom);
        txtDate = (TextView) findViewById(R.id.txtDate);
        etPurpose = (EditText) findViewById(R.id.txtPurpose);

        /* set room, date, time info */
        // test code
        txtRoom.setText("4층 19번 스터디룸");
        txtDate.setText("2017. 12. 07 15:00 ~ 16:00");

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

        // test code
        int year = 2017;
        int month = 12;
        int day = 7;
        int startHour = 10;
        int hours = 1;
        String purpose = etPurpose.getText().toString();

        // minimum input num check?
        webView.web_reservation(year, month, day, startHour, hours, purpose, items);
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

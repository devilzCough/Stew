package org.androidtown.stew;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class RoomInformationActivity extends AppCompatActivity {

    String floor_order;
    String room;

    TextView txtLocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_information);

        txtLocation = (TextView)findViewById(R.id.txtLocation);
        Intent receievedIntent = getIntent();
        floor_order = receievedIntent.getStringExtra("floor");
        room = receievedIntent.getStringExtra("room");

        txtLocation.setText(floor_order+" "+room);

    }
}

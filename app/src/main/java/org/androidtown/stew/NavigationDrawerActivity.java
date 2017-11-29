package org.androidtown.stew;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class NavigationDrawerActivity extends AppCompatActivity {

    MainFragment mainFragment;
    SelectRoomOptionFragment selectRoomOptionFragment;


    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    String[] myDataset = {"aaa"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);

        mainFragment = (MainFragment) getSupportFragmentManager().findFragmentById(R.id.drawer_layout);
        selectRoomOptionFragment = new SelectRoomOptionFragment();

        mRecyclerView = (RecyclerView) findViewById(R.id.left_drawer);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new StewAdapter(myDataset);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void onFragmentChanged(int index) {
        if (index == 0) {
            getSupportFragmentManager().beginTransaction().replace(R.id.drawer_layout, selectRoomOptionFragment).commit();
        } else if (index == 1) {
            getSupportFragmentManager().beginTransaction().replace(R.id.drawer_layout, mainFragment).commit();
        }
    }
}

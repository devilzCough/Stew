package org.androidtown.stew;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

public class MenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private Toolbar toolbar;
    private TextView toolbarTitle;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;
    private BottomBar bottomBar;
    private String userID,userPW;

    InfoFragment infoFragment;
    RoomOptionFragment roomOptionFragment;
    RoomListFragment roomListFragment;
    MyListFragment myListFragment;
    FragmentManager fragmentManager;
    JsoupProcess jsoupProcess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Intent recievedIntent = getIntent();
        userID = recievedIntent.getStringExtra("user_id");
        userPW = recievedIntent.getStringExtra("user_pw");

        jsoupProcess = new JsoupProcess();
        AppManager.getInstance().setJsoupProcess(jsoupProcess);
        jsoupProcess.userInfo();

        AppManager.getInstance().setUserID(userID);
        AppManager.getInstance().setUserPW(userPW);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("");

        toolbarTitle = (TextView)toolbar.findViewById(R.id.toolbar_title);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        infoFragment = new InfoFragment();
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.content, infoFragment).commit();

        bottomBar = (BottomBar)findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(int tabId) {
                Fragment fragment = null;

                int id = tabId;
                if (id == R.id.tab_reserve) {
                    Log.d("msg","reserve");
                    roomOptionFragment = new RoomOptionFragment();
                    fragment = roomOptionFragment;
                    toolbarTitle.setText("예약하기");
                }
                else if (id == R.id.tab_status) {
                    Log.d("msg","status");
                    roomListFragment = new RoomListFragment();
                    fragment = roomListFragment;
                    toolbarTitle.setText("전체현황");
                }
                else if (id == R.id.tab_info) {
                    Log.d("msg","info");
                    infoFragment = new InfoFragment();
                    fragment = infoFragment;
                    toolbarTitle.setText("이용안내");
                }else if (id == R.id.tab_mylist) {
                    Log.d("msg","mylist");
                    myListFragment = new MyListFragment();
                    fragment = myListFragment;
                    toolbarTitle.setText("마이페이지");
                }

                fragmentManager.beginTransaction().replace(R.id.content,fragment).commit();
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Fragment fragment = null;

        int id = item.getItemId();
        if (id == R.id.nav_reserve) {
            Log.d("msg","reserve");
            roomOptionFragment = new RoomOptionFragment();
            fragment = roomOptionFragment;

        } else if (id == R.id.nav_status) {
            Log.d("msg","status");
            roomListFragment = new RoomListFragment();
            fragment = roomListFragment;

        } else if (id == R.id.nav_mylist) {
            Log.d("msg","mylist");
            myListFragment = new MyListFragment();
            fragment = myListFragment;

        } else if (id == R.id.nav_logout) {
            Log.d("msg","logout");
            infoFragment = new InfoFragment();
            fragment = infoFragment;
        }

        fragmentManager.beginTransaction().replace(R.id.content,fragment).commit();
        item.setChecked(true);
        setTitle(item.getTitle());

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}


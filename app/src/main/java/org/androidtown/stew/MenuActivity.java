package org.androidtown.stew;

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
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

public class MenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;

    MenuFragment menuFragment;
    ReservationFragment reservationFragment;
    StatusFragment statusFragment;
    MyListFragment myListFragment;
    FragmentManager fragmentManager;

    BottomBar bottomBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        menuFragment = new MenuFragment();
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.content,menuFragment).commit();

        bottomBar = (BottomBar)findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(int tabId) {
                Fragment fragment = null;

                int id = tabId;
                if (id == R.id.tab_home) {
                    Log.d("msg","home");
                    menuFragment = new MenuFragment();
                    fragment = menuFragment;
                }
                else if (id == R.id.tab_reserve) {
                    Log.d("msg","reserve");
                    reservationFragment = new ReservationFragment();
                    fragment = reservationFragment;

                } else if (id == R.id.tab_status) {
                    Log.d("msg","status");
                    statusFragment = new StatusFragment();
                    fragment = statusFragment;

                } else if (id == R.id.tab_mylist) {
                    Log.d("msg","mylist");
                    myListFragment = new MyListFragment();
                    fragment = myListFragment;

                }

                fragmentManager.beginTransaction().replace(R.id.content,fragment).commit();
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.menu_logout) {
            Toast.makeText(this, "logout Clicked", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
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
            reservationFragment = new ReservationFragment();
            fragment = reservationFragment;

        } else if (id == R.id.nav_status) {
            Log.d("msg","status");
            statusFragment = new StatusFragment();
            fragment = statusFragment;

        } else if (id == R.id.nav_mylist) {
            Log.d("msg","mylist");
            myListFragment = new MyListFragment();
            fragment = myListFragment;

        } else if (id == R.id.nav_logout) {
            Log.d("msg","logout");
            menuFragment = new MenuFragment();
            fragment = menuFragment;

        }

        fragmentManager.beginTransaction().replace(R.id.content,fragment).commit();
        item.setChecked(true);
        setTitle(item.getTitle());

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}


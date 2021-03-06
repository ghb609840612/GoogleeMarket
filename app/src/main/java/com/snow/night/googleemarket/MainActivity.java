package com.snow.night.googleemarket;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.snow.night.googleemarket.adapter.MainAdapterSelf;
import com.snow.night.googleemarket.base.BaseFragment;
import com.snow.night.googleemarket.fragment.AppFragment;
import com.snow.night.googleemarket.fragment.CatogaryFragment;
import com.snow.night.googleemarket.fragment.GameFragment;
import com.snow.night.googleemarket.fragment.HomeFragment;
import com.snow.night.googleemarket.fragment.HotFragment;
import com.snow.night.googleemarket.fragment.SubjectFragment;
import com.snow.night.googleemarket.fragment.TopFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    List<BaseFragment> fragments;
    private MainAdapterSelf mainAdapter;
    private ArrayList<Integer> positions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        toolbar.setNavigationIcon(R.mipmap.ic_launcher1);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ViewPager vpMainActivity = (ViewPager) findViewById(R.id.vp_mainactivity);
        PagerTab indicator = (PagerTab)findViewById(R.id.indicator_mainactivity);
        fragments = new ArrayList<BaseFragment>();
        fragments.add(new HomeFragment());
        fragments.add(new AppFragment());
        fragments.add(new GameFragment());
        fragments.add(new CatogaryFragment());
        fragments.add(new HotFragment());
        fragments.add(new TopFragment());

        if(mainAdapter == null){
            mainAdapter = new MainAdapterSelf(getSupportFragmentManager(), fragments);
            vpMainActivity.setAdapter(mainAdapter);
        }else
        {
            mainAdapter.notifyDataSetChanged();
        }
        indicator.setIndicatorColor(Color.parseColor("#B95BF1"));
        indicator.setTabTextColor(Color.parseColor("#B95BF1"),Color.parseColor("#AA93FA"));
        indicator.setViewPager(vpMainActivity);
        positions = new ArrayList<>();
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            /**
             * 当页面被第一次选择的时候 调用网络请求数据的方法
             * @param position
             */
            @Override
            public void onPageSelected(int position) {
                if(!positions.contains(position))
                {
                    BaseFragment baseFragment =fragments.get(position);
                    baseFragment.initdata();

                    positions.add(position);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

package com.paxcel.paxcel.bustarckingsystem.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.paxcel.paxcel.bustarckingsystem.R;
import com.paxcel.paxcel.bustarckingsystem.fragments.CalenderFragment;
import com.paxcel.paxcel.bustarckingsystem.fragments.CurrentCityFragment;
import com.paxcel.paxcel.bustarckingsystem.fragments.DestinationFragment;
import com.paxcel.paxcel.bustarckingsystem.fragments.ImagesFragment;
import com.paxcel.paxcel.bustarckingsystem.utils.SharedPreferencesUtils;
import com.paxcel.paxcel.bustarckingsystem.utils.StepView;

public class TabActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    int indexSelected, indexUnselected;
    ViewPagerAdapter viewPagerAdapter;
    StepView stepView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        viewPager = findViewById(R.id.viewPager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        stepView = findViewById(R.id.stepperView);

        stepView.setSelected(0); // stepper view


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                indexSelected = tab.getPosition();
                Log.e("currentIndex", "Tab Selected" + indexSelected);
                Log.e("currentIndex", "Tab Unselected" + indexUnselected);

                stepView.setSelected(indexSelected); // stepper view


                if (indexUnselected > indexSelected) {

                    stepView.setSelected(indexSelected);

                }


            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

                indexUnselected = tab.getPosition();
                Log.e("currentIndex", "onTabUnselected" + indexUnselected);


            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                Log.e("currentIndex", "onTabReselected" + indexUnselected);

            }
        });

        findViewById(R.id.btn_logOut).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferencesUtils.getInstance(TabActivity.this).clear();
                startActivity(new Intent(TabActivity.this, LoginActivity.class));
            }
        });


    }



    public class ViewPagerAdapter extends FragmentPagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            if (position == 0) {
                fragment = new CurrentCityFragment();
            } else if (position == 1) {
                fragment = new DestinationFragment();
            } else if (position == 2) {
                fragment = new CalenderFragment();
            }else if (position == 3) {
                fragment = new ImagesFragment();
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String title = null;
            if (position == 0) {
                title = "CURRENT CITY";
            } else if (position == 1) {
                title = "DESTINATION";
            } else if (position == 2) {
                title = "CALENDER";
            }else if (position == 3) {
                title = "IMAGES";
            }
            return title;
        }
    }
}


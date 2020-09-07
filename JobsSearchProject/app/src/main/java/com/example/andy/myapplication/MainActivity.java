package com.example.andy.myapplication;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;
  //  private Toolbar toolbar;
    private TabLayout tabLayout;

    @TargetApi(Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      //  toolbar = (Toolbar) findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);
      //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tabLayout = findViewById(R.id.tabs);

        viewPager = findViewById(R.id.viewpager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new OneFragment(), "Home");
        adapter.addFragment(new TwoFragment(), "IT");
        adapter.addFragment(new ThreeFragment(), "Garments");
        adapter.addFragment(new FourFragment(), "HR");
        adapter.addFragment(new FiveFragment(), "NGO");
        adapter.addFragment(new SixFragment(), "Tourism");
        adapter.addFragment(new SevenFragment(), "Bank");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        final EditText KeySearchEditText = (EditText)findViewById(R.id.KeySearchEditText);


        Button btn = (Button)findViewById(R.id.KeySearchBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(KeySearchEditText.getText().toString()=="")
                {
                    Toast.makeText(MainActivity.this,"Enter Search Key!",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Intent idn = new Intent(MainActivity.this,DashboardActivity.class);
                    idn.putExtra("KeySearch",KeySearchEditText.getText().toString());
                    startActivity(idn);

                }

            }
        });


    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mList = new ArrayList<>();
        private final List<String> mTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager supportFragmentManager) {
            super(supportFragmentManager);
        }

        @Override
        public Fragment getItem(int i) {
            return mList.get(i);
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mList.add(fragment);
            mTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitleList.get(position);
        }
    }
}

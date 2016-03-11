package edu.lclark.githubfragmentapplication.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import edu.lclark.githubfragmentapplication.R;
import edu.lclark.githubfragmentapplication.fragments.MainActivityFragment;
import edu.lclark.githubfragmentapplication.fragments.UserFragment;
import edu.lclark.githubfragmentapplication.models.GithubUser;
import edu.lclark.githubfragmentapplication.models.TabbedDetailAdapter;

public class TabbedDetailActivity extends AppCompatActivity implements UserFragment.UserListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed_detail);
        ViewPager viewPager = (ViewPager) findViewById(R.id.activity_main_viewpager);

        TabbedDetailAdapter adapter = new TabbedDetailAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.activity_main_tablayout);

        tabLayout.setupWithViewPager(viewPager);

        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        setSupportActionBar(toolbar);

    }

    @Override
    public void onTabbedFollowerButtonClicked(GithubUser user) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.activity_main_framelayout, MainActivityFragment.newInstance(user));
        transaction.addToBackStack(null);
        transaction.commit();
    }

}

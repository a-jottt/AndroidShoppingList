package com.example.androidshoppinglist;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.androidshoppinglist.views.fragments.ArchivedFragment;
import com.example.androidshoppinglist.views.fragments.CurrentFragment;
import com.example.androidshoppinglist.views.adapters.ViewPagerAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @ViewById(R.id.toolbar) Toolbar toolbar;
    @ViewById(R.id.viewpager) ViewPager viewPager;
    @ViewById(R.id.tabs) TabLayout tabLayout;

    private int[] tabIcons = {
            R.drawable.basket,
            R.drawable.av_timer
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @AfterViews
    public void prepareView() {
        setSupportActionBar(toolbar);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new CurrentFragment(), getString(R.string.current_tab_title));
        adapter.addFragment(new ArchivedFragment(), getString(R.string.archived_tab_title));
        viewPager.setAdapter(adapter);
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
    }
}

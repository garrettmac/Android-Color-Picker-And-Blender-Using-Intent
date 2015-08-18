package com.example.garrettmacmaolain;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.model.GraphUser;
import com.parse.Parse;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;
import com.parse.ui.ParseLoginBuilder;

import java.util.Locale;


public class TabbedActivity extends FragmentActivity implements ActionBar.TabListener{
private static final int REQUEST_CODE = 1337;

    SectionsPagerAdapter mSectionsPagerAdapter;
    public static ParseUser currentUser;
    ViewPager mViewPager;
    public static User user;
    ParseLoginBuilder parseLoginBuilder;
    MyClothesFragment myClothesFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myClothesFragment = new MyClothesFragment();

        setContentView(R.layout.activity_tabbed);
        Parse.initialize(this, ParseStrings.APPLICATION_ID, ParseStrings.CLIENT_KEY);
        ParseFacebookUtils.initialize(getString(R.string.facebook_app_id));

        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        final int abTitleId = getResources().getIdentifier("action_bar_title", "id", "android");

        /**
         * Parse init
        */
        parseLoginBuilder = new ParseLoginBuilder(TabbedActivity.this);
        ParseUser.logOut();
        if(!isLoggedIn()) {
            startActivityForResult(parseLoginBuilder.build(), REQUEST_CODE);
        }

        mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setIcon(getResources().getDrawable(mSectionsPagerAdapter.getPageIcon(i)))
                            //.setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE && ParseFacebookUtils.getSession() != null) {
            Log.v("FB", "" + ParseFacebookUtils.getSession().toString());
            ParseFacebookUtils.finishAuthentication(requestCode, resultCode, data);
            currentUser = ParseUser.getCurrentUser();
            if(isLoggedIn()){


                // Request user data and show the results
                Request.executeMeRequestAsync(ParseFacebookUtils.getSession(), new Request.GraphUserCallback() {

                    @Override
                    public void onCompleted(GraphUser graphUser, Response response) {
                        if (graphUser != null) {
                            TabbedActivity.user = new User(graphUser,currentUser);

                            TabbedActivity.user.setObjectId(currentUser.getObjectId());
                            myClothesFragment.setUser(TabbedActivity.user);
                            Log.v("FB","USER IS LINKED: "+TabbedActivity.user.toString());
                            myClothesFragment.getMyCloths();
                            //TabbedActivity.user.sendToParse(currentUser);

                        }
                    }
                });
              //  myClothesFragment.getMyCloths();
              //  ParseFacebookUtils.getSession().
            }
        }else if(requestCode == REQUEST_CODE){
            startActivityForResult(parseLoginBuilder.build(), REQUEST_CODE);
        }
    }
    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        //Toast.makeText(TabbedActivity.this, "" + tab.getPosition(), Toast.LENGTH_SHORT).show();
         mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position) {
                case 0:
                    return new FullFeedFragment();
                case 1:
                    return myClothesFragment;
                case 2:
                    return new FullFeedFragment();
                case 3:
                    return new FullFeedFragment();
                default:
                    return null;
            }
        }


        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
                case 2:
                    return getString(R.string.title_section2).toUpperCase(l);
                case 3:
                    return getString(R.string.title_section2).toUpperCase(l);

            }
            return null;
        }

        public int getPageIcon(int i) {

            switch (i){
                case 0:
                    return R.drawable.plus_icon;
                default:
                    return R.drawable.plus_icon;
            }
        }
    }
    private boolean isLoggedIn(){
        ParseUser currentUser = ParseUser.getCurrentUser();
        if(currentUser != null){
            return true;
        } else{
            return false;
        }
    }
}

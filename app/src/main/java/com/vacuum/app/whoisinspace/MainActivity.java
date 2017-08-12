package com.vacuum.app.whoisinspace;

import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.LinearLayout;

import com.vacuum.app.whoisinspace.Fragment.AboutFragment;
import com.vacuum.app.whoisinspace.Fragment.FragmentOne;
import com.vacuum.app.whoisinspace.Fragment.FragmentThree;
import com.vacuum.app.whoisinspace.Fragment.FragmentTwo;

import java.util.ArrayList;
import java.util.List;

import yalantis.com.sidemenu.interfaces.Resourceble;
import yalantis.com.sidemenu.interfaces.ScreenShotable;
import yalantis.com.sidemenu.model.SlideMenuItem;
import yalantis.com.sidemenu.util.ViewAnimator;

import io.codetail.animation.SupportAnimator;
import io.codetail.animation.ViewAnimationUtils;

public class MainActivity extends AppCompatActivity  implements ViewAnimator.ViewAnimatorListener{

    String url  = "http://api.open-notify.org/astros.json";
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private List<SlideMenuItem> list = new ArrayList<>();
    private ViewAnimator viewAnimator;
    private LinearLayout linearLayout;
    private FragmentOne fragmentOne;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentOne = FragmentOne.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, fragmentOne)
                .commit();
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.setScrimColor(Color.TRANSPARENT);
        linearLayout = (LinearLayout) findViewById(R.id.left_drawer);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawers();
            }
        });


        setActionBar();
        createMenuList();
        viewAnimator = new ViewAnimator<>(this, list, fragmentOne, drawerLayout, this);

    }
    private void createMenuList() {
        SlideMenuItem menuItem = new SlideMenuItem("close", R.drawable.icn_close);
        list.add(menuItem);
        SlideMenuItem menuItem1 = new SlideMenuItem("one", R.drawable.home_vector);
        list.add(menuItem1);
        SlideMenuItem menuItem2 = new SlideMenuItem("two", R.drawable.if_misc__message_1276855);
        list.add(menuItem2);
        SlideMenuItem menuItem3 = new SlideMenuItem("three", R.drawable.configuration);
        list.add(menuItem3);
        SlideMenuItem menuItem4 = new SlideMenuItem("about", R.drawable.if_misc__alert_1276878);
        list.add(menuItem4);
    }

    private void setActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        drawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                drawerLayout,         /* DrawerLayout object */
                /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                linearLayout.removeAllViews();
                linearLayout.invalidate();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                if (slideOffset > 0.6 && linearLayout.getChildCount() == 0)
                    viewAnimator.showMenuContent();
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public ScreenShotable onSwitch(Resourceble slideMenuItem, ScreenShotable screenShotable, int position) {
        switch (slideMenuItem.getName()) {
            case "close":
                return screenShotable;
            default:
                return replaceFragment(slideMenuItem,screenShotable, position);

        }
    }

    private ScreenShotable replaceFragment(Resourceble slideMenuItem,ScreenShotable screenShotable, int topPosition) {
        View view = findViewById(R.id.content_frame);
        int finalRadius = Math.max(view.getWidth(), view.getHeight());
        SupportAnimator animator = ViewAnimationUtils.createCircularReveal(view, 0, topPosition, 0, finalRadius);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(ViewAnimator.CIRCULAR_REVEAL_ANIMATION_DURATION);
        findViewById(R.id.content_overlay).setBackgroundDrawable(new BitmapDrawable(getResources(), screenShotable.getBitmap()));
        animator.start();

        switch (slideMenuItem.getName()){
            case "one":
                FragmentOne fragmentOne  = FragmentOne.newInstance();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragmentOne ).commit();
                break;
            case "two":
                FragmentTwo fragmentTwo = FragmentTwo .newInstance();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragmentTwo ).commit();
                break;
            case "three":
                FragmentThree threeFragment = FragmentThree .newInstance();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, threeFragment ).commit();
                break;
            case "about":
                AboutFragment aboutFragment = AboutFragment.newInstance();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, aboutFragment).commit();
                break;
            default:
                break;

        }
        return fragmentOne;
    }




    @Override
    public void disableHomeButton() {
        getSupportActionBar().setHomeButtonEnabled(false);

    }

    @Override
    public void enableHomeButton() {
        getSupportActionBar().setHomeButtonEnabled(true);
        drawerLayout.closeDrawers();

    }

    @Override
    public void addViewToContainer(View view) {
        linearLayout.addView(view);
    }


}
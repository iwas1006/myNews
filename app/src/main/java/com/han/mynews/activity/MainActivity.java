package com.han.mynews.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import com.han.mynews.R;
import com.han.mynews.dto.Book;
import com.han.mynews.fragment.BookListFragment;
import com.han.mynews.fragment.BookMarkFragment;
import com.han.mynews.fragment.HomeFragment;
import com.han.mynews.fragment.SaveFragment;
import com.han.mynews.fragment.WebViewFragment;
import com.han.mynews.handler.BackPressCloseHandler;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    BackPressCloseHandler backPressCloseHandler = new BackPressCloseHandler(this);

    private static List<Book> bookList  = new ArrayList<Book>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Fragment newsListFragment   = new HomeFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_fragment_layout, newsListFragment, newsListFragment.getClass().getName());
        ft.commit();

    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.content_fragment_layout);

            if(fragment instanceof HomeFragment) {

            } else if(fragment instanceof WebViewFragment) {

                if(WebViewFragment.webView.canGoBack()) {
                    WebViewFragment.webView.goBack();
                    if(WebViewFragment.webView.getOriginalUrl().equalsIgnoreCase("about:blank") ) {
                        Fragment f   = new BookListFragment();
                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.content_fragment_layout, f, f.getClass().getName());
                        ft.commit();
                    }
                    return;
                } else {
                    backPressCloseHandler.onBackPressed();
                    return;
                }

            }

            backPressCloseHandler.onBackPressed();
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
        Fragment fragment = null;
        String title = getString(R.string.app_name);

        if (id == R.id.nav_camera) {
            fragment = new HomeFragment();
        } else if (id == R.id.nav_gallery) {
            fragment = new BookListFragment();
        } else if (id == R.id.nav_slideshow) {
            fragment = new BookMarkFragment();
        } else if (id == R.id.nav_manage) {
            fragment = new SaveFragment();
        } else if (id == R.id.nav_share) {
        } else if (id == R.id.nav_send) {
        }

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_fragment_layout, fragment);
            ft.commit();
        }

        // set the toolbar title
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

}

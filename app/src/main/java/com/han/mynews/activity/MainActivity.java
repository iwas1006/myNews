package com.han.mynews.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.han.mynews.R;
import com.han.mynews.dto.NewsItem;
import com.han.mynews.handler.BackPressCloseHandler;
import com.han.mynews.view.NewsItemView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    NewsAdapter adapter;
    ListView listView;
    BackPressCloseHandler backPressCloseHandler = new BackPressCloseHandler(this);
    WebView webView;
    ViewFlipper vf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        vf = (ViewFlipper)findViewById(R.id.vf);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        listView = (ListView) findViewById(R.id.mainList);

        adapter = new NewsAdapter();

        addList(adapter);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                NewsItem item = (NewsItem) adapter.getItem(position);
                Toast.makeText(getApplicationContext(), "선택 : " + item.getTitle(), Toast.LENGTH_LONG).show();

               // Intent intent = new Intent(getApplicationContext(), WebViewActivity.class);

               // intent.putExtra("id", item.getId());
               // intent.putExtra("url", item.getUrl());

               // startActivityForResult(intent, 100);

                webView = (WebView) findViewById(R.id.webView);
                webView.setWebViewClient(new WebViewClient());

                webView.clearFormData();
                webView.clearHistory();

                WebSettings webSettings = webView.getSettings();
                webSettings.setJavaScriptEnabled(true);

                webView.loadUrl(item.getUrl());

                vf.setDisplayedChild(1);

            }
        });

        adapter.notifyDataSetChanged();

        vf.setDisplayedChild(0);
    }

    private void addList(NewsAdapter adapter) {
        adapter.addItem(new NewsItem(0, "네이버", "네이버신문", "https://m.news.naver.com", 0));
        adapter.addItem(new NewsItem(1, "다음", "다음신문", "http://m.media.daum.net", 0));
        adapter.addItem(new NewsItem(2, "구글", "구글신문", "https://news.google.com", 0));
        adapter.addItem(new NewsItem(3, "네이트", "네이트뉴스", "http://m.news.nate.com/?", 0));
        adapter.addItem(new NewsItem(4, "국민일보", "국민일보", "http://m.kmib.co.kr/", 0));
    }

    @Override
    public void onBackPressed() {

//        Log.d("aaaaaaaaa", "현재 : " + webView.getOriginalUrl()+" / "+webViewFirstUrl);
        Log.d("aaaaaaaaa2", "현재 : " + vf.getDisplayedChild());
        //Log.d("aaaaaaaaa2", "webView.canGoBack() : " + mainWebView.canGoBack());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if(vf.getDisplayedChild() == 1) {
            if(this.webView.canGoBack()) {
                this.webView.goBack();
            } else {
                vf.setDisplayedChild(0);
            }
        } else {
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

    class NewsAdapter extends BaseAdapter {
        ArrayList<NewsItem> items = new ArrayList<NewsItem>();

        @Override
        public int getCount() {
            return items.size();
        }

        public void addItem(NewsItem item) {
            items.add(item);
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            NewsItemView view = new NewsItemView(getApplicationContext());

            NewsItem item = items.get(position);
            view.setTitle(item.getTitle());
            view.setContent(item.getContent());
            view.setImage(item.getIcon());

            return view;
        }
    }
}

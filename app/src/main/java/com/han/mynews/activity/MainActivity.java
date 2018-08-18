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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.han.mynews.R;
import com.han.mynews.db.DBHelper;
import com.han.mynews.dto.Item;
import com.han.mynews.dto.NewsItem;
import com.han.mynews.extend.MovableFloatingActionButton;
import com.han.mynews.handler.BackPressCloseHandler;
import com.han.mynews.view.NewsItemView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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


        final DBHelper dbHelper = new DBHelper(getApplicationContext(), "MoneyBook.db", null, 1);
        final TextView result = (TextView) findViewById(R.id.result);
        final EditText etDate = (EditText) findViewById(R.id.date);
        final EditText etItem = (EditText) findViewById(R.id.item);
        final EditText etPrice = (EditText) findViewById(R.id.price);
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        // 출력될 포맷 설정
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy년 MM월 dd일");
        etDate.setText(simpleDateFormat.format(date));

        // DB에 데이터 추가
        Button insert = (Button) findViewById(R.id.insert);
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = etDate.getText().toString();
                String item = etItem.getText().toString();
                int price = Integer.parseInt(etPrice.getText().toString());

                dbHelper.insert(date, item, price);
                result.setText(dbHelper.getResult());
            }
        });

        // DB에 있는 데이터 수정
        Button update = (Button) findViewById(R.id.update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item = etItem.getText().toString();
                int price = Integer.parseInt(etPrice.getText().toString());

                dbHelper.update(item, price);
                result.setText(dbHelper.getResult());
            }
        });

        // DB에 있는 데이터 삭제
        Button delete = (Button) findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item = etItem.getText().toString();

                dbHelper.delete(item);
                result.setText(dbHelper.getResult());
            }
        });

        // DB에 있는 데이터 조회
        Button select = (Button) findViewById(R.id.select);
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result.setText(dbHelper.getResult());
            }
        });












        vf = (ViewFlipper)findViewById(R.id.vf);

        MovableFloatingActionButton snsBtn = (MovableFloatingActionButton) findViewById(R.id.snsBtn);
        snsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "share sns : " + webView.getOriginalUrl(), Snackbar.LENGTH_LONG).setAction("Action", null).show();
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

                webView = (WebView) findViewById(R.id.webView);
                webView.setWebViewClient(new WebViewClient());

                webView.loadUrl("about:blank");
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
    private void addSavedList(NewsAdapter adapter) {
        final DBHelper dbHelper = new DBHelper(getApplicationContext(), "MoneyBook.db", null, 1);
        List<NewsItem> itemList = dbHelper.getItems();
        for(NewsItem item : itemList) {
            adapter.addItem(item);
        }
    }
    private void addList(NewsAdapter adapter) {

        adapter.addItem(new NewsItem(0, "네이버", "네이버신문", "https://m.news.naver.com", 0));
        adapter.addItem(new NewsItem(1, "다음", "다음신문", "http://m.media.daum.net", 0));
        adapter.addItem(new NewsItem(2, "구글", "구글신문", "https://news.google.com", 0));
        adapter.addItem(new NewsItem(3, "네이트", "네이트뉴스", "http://m.news.nate.com/?", 0));
        adapter.addItem(new NewsItem(4, "국민일보", "국민일보", "http://m.kmib.co.kr/", 0));
        adapter.addItem(new NewsItem(0, "네이버", "네이버신문", "https://m.news.naver.com", 0));
        adapter.addItem(new NewsItem(1, "다음", "다음신문", "http://m.media.daum.net", 0));
        adapter.addItem(new NewsItem(2, "구글", "구글신문", "https://news.google.com", 0));
        adapter.addItem(new NewsItem(3, "네이트", "네이트뉴스", "http://m.news.nate.com/?", 0));
        adapter.addItem(new NewsItem(4, "국민일보", "국민일보", "http://m.kmib.co.kr/", 0));
        adapter.addItem(new NewsItem(0, "네이버", "네이버신문", "https://m.news.naver.com", 0));
        adapter.addItem(new NewsItem(1, "다음", "다음신문", "http://m.media.daum.net", 0));
        adapter.addItem(new NewsItem(2, "구글", "구글신문", "https://news.google.com", 0));
        adapter.addItem(new NewsItem(3, "네이트", "네이트뉴스", "http://m.news.nate.com/?", 0));
        adapter.addItem(new NewsItem(4, "국민일보", "국민일보", "http://m.kmib.co.kr/", 0));
    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if(vf.getDisplayedChild() == 1) {
            if(this.webView.canGoBack()) {
                this.webView.goBack();
                if(this.webView.getOriginalUrl().equalsIgnoreCase("about:blank")) {
                    vf.setDisplayedChild(0);
                }
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
            //기본 뉴스 목록
            // Handle the camera action
            vf.setDisplayedChild(0);
            adapter = null;
            adapter = new NewsAdapter();
            addList(adapter);
            adapter.notifyDataSetChanged();
        } else if (id == R.id.nav_gallery) {
            //저장 목록
            vf.setDisplayedChild(0);
            adapter = null;
            adapter = new NewsAdapter();
            addSavedList(adapter);
            adapter.notifyDataSetChanged();
        } else if (id == R.id.nav_slideshow) {
            //저장하기
            vf.setDisplayedChild(2);
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

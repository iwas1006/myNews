package com.han.mynews.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import com.han.mynews.R;
import com.han.mynews.dao.NewsIconDaoImpl;
import com.han.mynews.dto.Book;
import com.han.mynews.fragment.BookListFragment;
import com.han.mynews.fragment.BookMarkFragment;
import com.han.mynews.fragment.HomeFragment;
import com.han.mynews.fragment.SaveFragment;
import com.han.mynews.fragment.WebViewFragment;
import com.han.mynews.handler.BackPressCloseHandler;
import com.han.mynews.task.BookImageTask;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    BackPressCloseHandler backPressCloseHandler = new BackPressCloseHandler(this);

    private static List<Book> bookList  = new ArrayList<Book>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        populate();

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

    private void populate() {

        BookListFragment.books.add(new Book("서울신문", "네이버신문", "http://www.seoul.co.kr"));
        BookListFragment.books.add(new Book("노컷뉴스", "네이버신문", "http://www.nocutnews.co.kr"));
        BookListFragment.books.add(new Book("조선비즈", "네이버신문", "http://biz.chosun.com"));
        BookListFragment.books.add(new Book("OSEN", "네이버신문", "http://osen.mt.co.kr"));
        BookListFragment.books.add(new Book("YTN", "네이버신문", "http://www.ytn.co.kr"));
        BookListFragment.books.add(new Book("디지털타임스", "네이버신문", "http://www.dt.co.kr"));
        BookListFragment.books.add(new Book("KBS World", "네이버신문", "http://world.kbs.co.kr/english"));
        BookListFragment.books.add(new Book("서울경제", "네이버신문", "http://www.sedaily.com"));
        BookListFragment.books.add(new Book("국민일보", "네이버신문", "http://www.kmib.co.kr/news/index.asp"));
        BookListFragment.books.add(new Book("파이낸셜뉴스", "네이버신문", "http://www.fnnews.com"));
        BookListFragment.books.add(new Book("오마이뉴스", "네이버신문", "http://www.ohmynews.com"));
        BookListFragment.books.add(new Book("중앙데일리", "네이버신문", "http://koreajoongangdaily.joins.com"));
        BookListFragment.books.add(new Book("KBS", "네이버신문", "http://news.kbs.co.kr"));
        BookListFragment.books.add(new Book("전자신문", "네이버신문", "http://www.etnews.com"));
        BookListFragment.books.add(new Book("일간스포츠", "네이버신문", "http://isplus.joins.com"));
        BookListFragment.books.add(new Book("뉴시스", "네이버신문", "http://www.newsis.com"));
        BookListFragment.books.add(new Book("머니투데이", "네이버신문", "http://www.mt.co.kr"));
        BookListFragment.books.add(new Book("시사인", "네이버신문", "http://www.sisain.co.kr"));
        BookListFragment.books.add(new Book("뉴스타파", "네이버신문", "http://www.newstapa.org"));
        BookListFragment.books.add(new Book("조선일보", "네이버신문", "http://www.chosun.com"));
        BookListFragment.books.add(new Book("MBN", "네이버신문", "http://mbn.mk.co.kr"));
        BookListFragment.books.add(new Book("지지통신", "네이버신문", "http://www.jiji.com"));
        BookListFragment.books.add(new Book("블로터", "네이버신문", "http://www.bloter.net"));
        BookListFragment.books.add(new Book("연합뉴스TV", "네이버신문", "http://www.yonhapnewstv.co.kr"));
        BookListFragment.books.add(new Book("세계일보", "네이버신문", "http://www.segye.com"));
        BookListFragment.books.add(new Book("중앙일보", "네이버신문", "http://joongang.joins.com"));
        BookListFragment.books.add(new Book("뉴데일리", "네이버신문", "http://www.newdaily.co.kr"));
        BookListFragment.books.add(new Book("지디넷코리아", "네이버신문", "http://www.zdnet.co.kr"));
        BookListFragment.books.add(new Book("데일리안", "네이버신문", "http://www.dailian.co.kr"));
        BookListFragment.books.add(new Book("SBS", "네이버신문", "http://news.sbs.co.kr/indexes/news_index.html"));
        BookListFragment.books.add(new Book("매일경제", "네이버신문", "http://www.mk.co.kr"));
        BookListFragment.books.add(new Book("동아일보", "네이버신문", "http://www.donga.com"));
        BookListFragment.books.add(new Book("미디어오늘", "네이버신문", "http://www.mediatoday.co.kr"));
        BookListFragment.books.add(new Book("스포츠조선", "네이버신문", "http://sports.chosun.com"));
        BookListFragment.books.add(new Book("스포츠동아", "네이버신문", "http://sports.donga.com"));
        BookListFragment.books.add(new Book("이데일리", "네이버신문", "http://www.edaily.co.kr"));
        BookListFragment.books.add(new Book("JTBC", "네이버신문", "http://news.jtbc.joins.com"));
        BookListFragment.books.add(new Book("경향신문", "네이버신문", "http://www.khan.co.kr"));
        BookListFragment.books.add(new Book("한국일보", "네이버신문", "http://www.hankookilbo.com"));
        BookListFragment.books.add(new Book("문화일보", "네이버신문", "http://www.munhwa.com"));
        BookListFragment.books.add(new Book("스포츠서울", "네이버신문", "http://www.sportsseoul.com"));
        BookListFragment.books.add(new Book("헤럴드경제", "네이버신문", "http://www.heraldbiz.com"));
        BookListFragment.books.add(new Book("스포탈코리아", "네이버신문", "http://sportalkorea.com"));
        BookListFragment.books.add(new Book("한국경제TV", "네이버신문", "http://news.wowtv.co.kr"));
        BookListFragment.books.add(new Book("코리아헤럴드", "네이버신문", "http://www.koreaherald.com/index_kr.php"));
        BookListFragment.books.add(new Book("아시아경제", "네이버신문", "http://www.asiae.co.kr"));
        BookListFragment.books.add(new Book("마이데일리", "네이버신문", "http://www.mydaily.co.kr"));
        BookListFragment.books.add(new Book("프레시안", "네이버신문", "http://www.pressian.com"));
        BookListFragment.books.add(new Book("한겨레", "네이버신문", "http://www.hani.co.kr"));
        BookListFragment.books.add(new Book("아이뉴스24", "네이버신문", "http://www.inews24.com"));
        BookListFragment.books.add(new Book("MBC", "네이버신문", "http://imnews.imbc.com"));
        BookListFragment.books.add(new Book("한국경제", "네이버신문", "http://www.hankyung.com"));


        BookListFragment.books.add(new Book("네이버", "네이버신문", "https://m.news.naver.com"));
        BookListFragment.books.add(new Book("다음", "다음신문", "http://m.media.daum.net"));
        BookListFragment.books.add(new Book("구글", "구글신문", "https://news.google.com"));
        BookListFragment.books.add(new Book("네이트", "네이트뉴스", "http://m.news.nate.com/?"));

        NewsIconDaoImpl newsIcon = new NewsIconDaoImpl(getApplicationContext());

        for(Book b : BookListFragment.books) {
            Log.d("___populate____", b.toString());
            String imageUrl = newsIcon.getImageUrl(b.getUrl());

            if(imageUrl != null && imageUrl.length() > 0) b.setImageUrl(imageUrl);
            Log.d("___populate2____", b.toString());
            if(b.getImageUrl() == null || b.getImageUrl().length() == 0) {
                new BookImageTask(getApplicationContext(), null).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, b);
            }
        }
    }
}

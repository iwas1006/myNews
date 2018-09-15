package com.han.mynews.fragment;

import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.han.mynews.R;
import com.han.mynews.adapter.BooksAdapter;
import com.han.mynews.dao.NewsIconDaoImpl;
import com.han.mynews.dto.Book;
import com.han.mynews.task.BookImageTask;

import java.util.ArrayList;
import java.util.List;

import co.dift.ui.SwipeToAction;

public class BookListFragment extends Fragment {

    RecyclerView recyclerView;
    BooksAdapter adapter;
    SwipeToAction swipeToAction;

    public static List<Book> books = new ArrayList<>();
   // List<Book> soruceBooks = new ArrayList<>();

    public BookListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.book_activity_main, container, false);

        // facebook image library
        Fresco.initialize(getContext());

        recyclerView = (RecyclerView) v.findViewById(R.id.recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        adapter = new BooksAdapter(this.books);
        recyclerView.setAdapter(adapter);

        swipeToAction = new SwipeToAction(recyclerView, new SwipeToAction.SwipeListener<Book>() {
            @Override
            public boolean swipeLeft(final Book itemData) {
                final int pos = removeBook(itemData);
                /*displaySnackbar(itemData.getTitle() + " removed", "Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addBook(pos, itemData);
                    }
                });*/
                return true;
            }

            @Override
            public boolean swipeRight(Book itemData) {
               // displaySnackbar(itemData.getTitle() + " loved", null, null);
                return true;
            }

            @Override
            public void onClick(Book itemData) {

               //NewsItem item = (NewsItem)getActivity().getadapterView.getAdapter().getItem(position);

                //Log.d("__________item toString", item.toString());

                Fragment fragment = new WebViewFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();

                Bundle bundle = new Bundle(1); // 파라미터는 전달할 데이터 개수
                bundle.putString("url", itemData.getUrl()); // key , value
                fragment.setArguments(bundle);

                ft.replace(R.id.content_fragment_layout, fragment, fragment.getClass().getName());
                ft.commit();

                Toast.makeText(getActivity(), itemData.getTitle(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(Book itemData) {
               // displaySnackbar(itemData.getTitle() + " long clicked", null, null);
            }
        });

       // initBookList();
      //  populate();

        // use swipeLeft or swipeRight and the elem position to swipe by code
       /* new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeToAction.swipeRight(2);
            }
        }, 3000);*/

        // Inflate the layout for this fragment
        return v;
    }

    private void populate() {

        this.books.add(new Book("서울신문", "네이버신문", "http://www.seoul.co.kr"));
        this.books.add(new Book("노컷뉴스", "네이버신문", "http://www.nocutnews.co.kr"));
        this.books.add(new Book("조선비즈", "네이버신문", "http://biz.chosun.com"));
        this.books.add(new Book("OSEN", "네이버신문", "http://osen.mt.co.kr"));
        this.books.add(new Book("YTN", "네이버신문", "http://www.ytn.co.kr"));
        this.books.add(new Book("디지털타임스", "네이버신문", "http://www.dt.co.kr"));
        this.books.add(new Book("KBS World", "네이버신문", "http://world.kbs.co.kr/english"));
        this.books.add(new Book("서울경제", "네이버신문", "http://www.sedaily.com"));
        this.books.add(new Book("국민일보", "네이버신문", "http://www.kmib.co.kr/news/index.asp"));
        this.books.add(new Book("파이낸셜뉴스", "네이버신문", "http://www.fnnews.com"));
        this.books.add(new Book("오마이뉴스", "네이버신문", "http://www.ohmynews.com"));
        this.books.add(new Book("중앙데일리", "네이버신문", "http://koreajoongangdaily.joins.com"));
        this.books.add(new Book("KBS", "네이버신문", "http://news.kbs.co.kr"));
        this.books.add(new Book("전자신문", "네이버신문", "http://www.etnews.com"));
        this.books.add(new Book("일간스포츠", "네이버신문", "http://isplus.joins.com"));
        this.books.add(new Book("뉴시스", "네이버신문", "http://www.newsis.com"));
        this.books.add(new Book("머니투데이", "네이버신문", "http://www.mt.co.kr"));
        this.books.add(new Book("시사인", "네이버신문", "http://www.sisain.co.kr"));
        this.books.add(new Book("뉴스타파", "네이버신문", "http://www.newstapa.org"));
        this.books.add(new Book("조선일보", "네이버신문", "http://www.chosun.com"));
        this.books.add(new Book("MBN", "네이버신문", "http://mbn.mk.co.kr"));
        this.books.add(new Book("지지통신", "네이버신문", "http://www.jiji.com"));
        this.books.add(new Book("블로터", "네이버신문", "http://www.bloter.net"));
        this.books.add(new Book("연합뉴스TV", "네이버신문", "http://www.yonhapnewstv.co.kr"));
        this.books.add(new Book("세계일보", "네이버신문", "http://www.segye.com"));
        this.books.add(new Book("중앙일보", "네이버신문", "http://joongang.joins.com"));
        this.books.add(new Book("뉴데일리", "네이버신문", "http://www.newdaily.co.kr"));
        this.books.add(new Book("지디넷코리아", "네이버신문", "http://www.zdnet.co.kr"));
        this.books.add(new Book("데일리안", "네이버신문", "http://www.dailian.co.kr"));
        this.books.add(new Book("SBS", "네이버신문", "http://news.sbs.co.kr/indexes/news_index.html"));
        this.books.add(new Book("매일경제", "네이버신문", "http://www.mk.co.kr"));
        this.books.add(new Book("동아일보", "네이버신문", "http://www.donga.com"));
        this.books.add(new Book("미디어오늘", "네이버신문", "http://www.mediatoday.co.kr"));
        this.books.add(new Book("스포츠조선", "네이버신문", "http://sports.chosun.com"));
        this.books.add(new Book("스포츠동아", "네이버신문", "http://sports.donga.com"));
        this.books.add(new Book("이데일리", "네이버신문", "http://www.edaily.co.kr"));
        this.books.add(new Book("JTBC", "네이버신문", "http://news.jtbc.joins.com"));
        this.books.add(new Book("경향신문", "네이버신문", "http://www.khan.co.kr"));
        this.books.add(new Book("한국일보", "네이버신문", "http://www.hankookilbo.com"));
        this.books.add(new Book("문화일보", "네이버신문", "http://www.munhwa.com"));
        this.books.add(new Book("스포츠서울", "네이버신문", "http://www.sportsseoul.com"));
        this.books.add(new Book("헤럴드경제", "네이버신문", "http://www.heraldbiz.com"));
        this.books.add(new Book("스포탈코리아", "네이버신문", "http://sportalkorea.com"));
        this.books.add(new Book("한국경제TV", "네이버신문", "http://news.wowtv.co.kr"));
        this.books.add(new Book("코리아헤럴드", "네이버신문", "http://www.koreaherald.com/index_kr.php"));
        this.books.add(new Book("아시아경제", "네이버신문", "http://www.asiae.co.kr"));
        this.books.add(new Book("마이데일리", "네이버신문", "http://www.mydaily.co.kr"));
        this.books.add(new Book("프레시안", "네이버신문", "http://www.pressian.com"));
        this.books.add(new Book("한겨레", "네이버신문", "http://www.hani.co.kr"));
        this.books.add(new Book("아이뉴스24", "네이버신문", "http://www.inews24.com"));
        this.books.add(new Book("MBC", "네이버신문", "http://imnews.imbc.com"));
        this.books.add(new Book("한국경제", "네이버신문", "http://www.hankyung.com"));
        

        this.books.add(new Book("네이버", "네이버신문", "https://m.news.naver.com"));
        this.books.add(new Book("다음", "다음신문", "http://m.media.daum.net"));
        this.books.add(new Book("구글", "구글신문", "https://news.google.com"));
        this.books.add(new Book("네이트", "네이트뉴스", "http://m.news.nate.com/?"));




        NewsIconDaoImpl newsIcon = new NewsIconDaoImpl(getContext());
        for(Book b : this.books) {
            Log.d("___populate____", b.toString());
            String imageUrl = newsIcon.getImageUrl(b.getUrl());

            if(imageUrl != null && imageUrl.length() > 0) b.setImageUrl(imageUrl);
            Log.d("___populate2____", b.toString());
            if(b.getImageUrl() == null || b.getImageUrl().length() == 0) {
                new BookImageTask(getContext(), adapter).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, b);
            }
        }

    }

//    private void populate() {
//
//        for(Book book : this.soruceBooks) {
//            new BookImageTask(getContext(), this.books).execute(book);
//        }
//    }

  /*  private void displaySnackbar(String text, String actionName, View.OnClickListener action) {
        Snackbar snack = Snackbar.make(getView().findViewById(android.R.id.content), text, Snackbar.LENGTH_LONG)
                .setAction(actionName, action);

        View v = snack.getView();
        v.setBackgroundColor(getResources().getColor(R.color.secondary));
        ((TextView) v.findViewById(android.support.design.R.id.snackbar_text)).setTextColor(Color.WHITE);
        ((TextView) v.findViewById(android.support.design.R.id.snackbar_action)).setTextColor(Color.BLACK);

        snack.show();
    }*/

    private int removeBook(Book book) {
        int pos = books.indexOf(book);
        books.remove(book);
        adapter.notifyItemRemoved(pos);
        return pos;
    }

    private void addBook(int pos, Book book) {
        books.add(pos, book);
        adapter.notifyItemInserted(pos);
    }
}

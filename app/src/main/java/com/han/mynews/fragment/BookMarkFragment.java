package com.han.mynews.fragment;

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
import com.han.mynews.dao.NewsDaoImpl;
import com.han.mynews.db.DBHelper;
import com.han.mynews.dto.Book;

import java.util.ArrayList;
import java.util.List;

import co.dift.ui.SwipeToAction;

public class BookMarkFragment extends Fragment {

    RecyclerView recyclerView;
    BooksAdapter adapter;
    SwipeToAction swipeToAction;

    List<Book> books = new ArrayList<>();
   // List<Book> soruceBooks = new ArrayList<>();

    public BookMarkFragment() {
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
                Log.d("Book_swipeLeft___", itemData.toString());

                NewsDaoImpl news = new NewsDaoImpl(getContext());
                news.delete(itemData.getId());
                Toast.makeText(getActivity(), itemData.getTitle()+"is deleted", Toast.LENGTH_SHORT).show();

                populate();
                adapter.notifyDataSetChanged();

                return true;
            }

            @Override
            public boolean swipeRight(Book itemData) {
                Log.d("Book_swipeRight___", itemData.toString());
               // displaySnackbar(itemData.getTitle() + " loved", null, null);
                return true;
            }

            @Override
            public void onClick(Book itemData) {

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

        populate();

        return v;
    }

    private void populate() {
        NewsDaoImpl news = new NewsDaoImpl(getContext());

        List<Book> bookList = news.getItems();

        this.books.clear();
        this.books.addAll(bookList);
    }

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

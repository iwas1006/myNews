package com.han.mynews.adapter;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.han.mynews.R;
import com.han.mynews.dto.Book;
import com.han.mynews.task.BookImageTask;
import com.han.mynews.task.ImageTask;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import co.dift.ui.SwipeToAction;


public class BooksAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Book> items;
    private static Map<String, String> imageUrlMap = new HashMap<String, String>();

    /** References to the views for each data item **/
    public class BookViewHolder extends SwipeToAction.ViewHolder<Book> {
        public TextView titleView;
        public TextView authorView;
        public SimpleDraweeView imageView;

        public BookViewHolder(View v) {
            super(v);

            titleView = (TextView) v.findViewById(R.id.title);
            authorView = (TextView) v.findViewById(R.id.author);
            imageView = (SimpleDraweeView) v.findViewById(R.id.image);
        }
    }

    /** Constructor **/
    public BooksAdapter(List<Book> items) {
        this.items = items;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item_view, parent, false);

        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Book item = items.get(position);

        BookViewHolder vh = (BookViewHolder) holder;

        vh.titleView.setText(item.getTitle());
        vh.authorView.setText(item.getContent());

        if(imageUrlMap.containsKey(item.getUrl())) {
            vh.imageView.setImageURI(Uri.parse(imageUrlMap.get(item.getUrl())));
        } else {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("view", vh.imageView);
            map.put("map", imageUrlMap);

            //new BookImageTask(holder.itemView.getContext(), map).execute(item.getUrl());
        }



       // vh.imageView.setImageURI(Uri.parse(item.getImageUrl()));
        vh.data = item;
    }
}
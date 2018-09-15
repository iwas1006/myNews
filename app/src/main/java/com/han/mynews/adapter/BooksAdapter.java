package com.han.mynews.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.han.mynews.R;
import com.han.mynews.dto.Book;
import com.han.mynews.task.BookImageTask;

import java.util.List;

import co.dift.ui.SwipeToAction;


public class BooksAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Book> items;
//    private static Map<String, String> imageUrlMap = new HashMap<String, String>();

    /** References to the views for each data item **/
    public class BookViewHolder extends SwipeToAction.ViewHolder<Book> {
        public TextView titleView;
        public TextView authorView;
        public SimpleDraweeView imageView;
        public Context context;

        public BookViewHolder(View v) {
            super(v);
            context =  v.getContext();
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
        holder.setIsRecyclable(false);
        Book item = items.get(position);

        BookViewHolder vh = (BookViewHolder) holder;

        vh.titleView.setText(item.getTitle());
        vh.authorView.setText(item.getContent());

        Log.d("onBindViewHolder item.getImageUrl()_________", item.toString());
        if(item.getImageUrl() != null && item.getImageUrl().trim().length() > 0) {
            //Picasso.get().load(item.getImageUrl()). .into(vh.imageView);
            //((BookViewHolder) holder).context).load(item.getImageUrl()).into(vh.imageView);
            //FileCache fc = new FileCache(((BookViewHolder) holder).context);
            /*File f = fc.readFile(fc.makeFileName(item.getImageUrl()));
            if (f == null) {
                Log.d("aa", "f is null__"+item);
                new BookImageTask(holder.itemView.getContext(), vh.imageView).execute(item);
            } else {
                Log.d("aa", "f is not null__"+f.getName());
                vh.imageView.setImageURI(Uri.fromFile(f));
            }*/
            //ImageView.setImageURI(Uri.fromFile(new File("/sdcard/cats.jpg")));
            vh.imageView.setImageURI(Uri.parse(item.getImageUrl()));

        } else {

          //  final DBHelperForIconList dbHelper = new DBHelperForIconList(getApplicationContext(), "newsbookIcon.db", null, 1);
            /*for(Book b : BookListFragment.books) {
                Log.d("___populate____", b.toString());
                String imageUrl = dbHelper.getImageUrl(b.getUrl());

                if(imageUrl != null && imageUrl.length() > 0) b.setImageUrl(imageUrl);
                Log.d("___populate2____", b.toString());
                if(b.getImageUrl() == null || b.getImageUrl().length() == 0) {

                }
            }
*/
            Log.d("aa", "f else__"+item);
             //new BookImageTask(holder.itemView.getContext(), null).execute(item);
            //new BookImageTask(holder.itemView.getContext(), vh.imageView).execute(item);
        }
            vh.data = item;
    }
}
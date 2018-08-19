package com.han.mynews.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.etsy.android.grid.StaggeredGridView;
import com.han.mynews.R;
import com.han.mynews.dto.NewsItem;
import com.han.mynews.view.NewsItemView;

import java.util.ArrayList;

public class NewsList2Fragment extends Fragment {

    NewsAdapter adapter;
    ListView listView;

    public NewsList2Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_newslist, container, false);

        listView = (ListView) v.findViewById(R.id.mainList);

        adapter = new NewsAdapter();


        ListAdapter la = new ListAdapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(context).inflate(R.layout.recycler_item,parent,false);
                return new ViewHolder(view);

                return null;
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                SingModel singModel = singModels.get(position);
                holder.song.setText(singModel.getSong());
                holder.singer.setText(singModel.getSinger());

            }
        }
        addList(adapter);

        StaggeredGridView gridView = (StaggeredGridView) v.findViewById(R.id.grid_view);
        gridView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                NewsItem item = (NewsItem) adapter.getItem(position);
                Toast.makeText(getActivity(), "선택 : " + item.getTitle(), Toast.LENGTH_LONG).show();


                Fragment fragment = new WebViewFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();

                Bundle bundle = new Bundle(1); // 파라미터는 전달할 데이터 개수
                bundle.putString("url", item.getUrl()); // key , value
                fragment.setArguments(bundle);

                ft.replace(R.id.content_fragment_layout, fragment, fragment.getClass().getName());
                ft.commit();



               // vf.setDisplayedChild(1);

            }
        });

        adapter.notifyDataSetChanged();

        // Inflate the layout for this fragment
        return v;
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


    class NewsListAdapter extends ListAdapter {

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        }

        public NewsListAdapter(@NonNull DiffUtil.ItemCallback diffCallback) {
            super(diffCallback);
        }
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
            NewsItemView view = new NewsItemView(getActivity());

            NewsItem item = items.get(position);
            view.setTitle(item.getTitle());
            view.setContent(item.getContent());
            view.setImage(item.getIcon());

            return view;
        }
    }
}

package com.han.mynews.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;

import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.etsy.android.grid.StaggeredGridView;
import com.han.mynews.R;
import com.han.mynews.adapter.SampleAdapter;
import com.han.mynews.dto.NewsItem;

import java.util.ArrayList;

public class NewsList2Fragment extends Fragment implements AbsListView.OnScrollListener, AbsListView.OnItemClickListener {

    private static final String TAG = "StaggeredGridActivityFragment";

   // NewsAdapter adapter;
    ListView listView;

    private StaggeredGridView mGridView;
    private boolean mHasRequestedMore;
    private SampleAdapter mAdapter;

    private ArrayList<NewsItem> mData;

    public NewsList2Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_sgv, container, false);

        mGridView = (StaggeredGridView) v.findViewById(R.id.grid_view);

        if (savedInstanceState == null) {
            final LayoutInflater layoutInflater = getActivity().getLayoutInflater();

            View header = layoutInflater.inflate(R.layout.list_item_header_footer, null);
            View footer = layoutInflater.inflate(R.layout.list_item_header_footer, null);
            TextView txtHeaderTitle = (TextView) header.findViewById(R.id.txt_title);
            TextView txtFooterTitle = (TextView) footer.findViewById(R.id.txt_title);
            txtHeaderTitle.setText("THE HEADER!");
            txtFooterTitle.setText("THE FOOTER!");

            mGridView.addHeaderView(header);
            mGridView.addFooterView(footer);
        }

        if (mAdapter == null) {
            mAdapter = new SampleAdapter(getActivity(), R.id.txt_line1);
        }

        if (mData == null) {
            mData = addList();
        }

        for (NewsItem data : mData) {
            mAdapter.add(data);
        }

        mGridView.setAdapter(mAdapter);
        mGridView.setOnScrollListener(this);
        mGridView.setOnItemClickListener(this);

        // Inflate the layout for this fragment
        return v;
    }

    private ArrayList<NewsItem> addList() {
        ArrayList<NewsItem> result = new ArrayList<NewsItem>();

        result.add(new NewsItem(0, "네이버", "네이버신문", "https://m.news.naver.com/read.nhn?sid1=100&oid=277&aid=0004298119&mode=LSD", 0));
        result.add(new NewsItem(3, "네이트", "네이트뉴스", "https://m.news.naver.com/read.nhn?sid1=100&oid=055&aid=0000668013&mode=LSD", 0));
        result.add(new NewsItem(4, "전자신문", "전자신문", "https://m.news.naver.com/read.nhn?aid=0002734244&oid=030&sid1=105", 0));
        result.add(new NewsItem(1, "다음", "다음신문", "http://m.media.daum.net", 0));
        result.add(new NewsItem(2, "구글", "구글신문", "https://news.google.com", 0));
        result.add(new NewsItem(0, "네이버", "네이버신문", "https://m.news.naver.com", 0));
        result.add(new NewsItem(1, "다음", "다음신문", "http://m.media.daum.net", 0));
        result.add(new NewsItem(2, "구글", "구글신문", "https://news.google.com", 0));
        result.add(new NewsItem(3, "네이트", "네이트뉴스", "http://m.news.nate.com/?", 0));
        result.add(new NewsItem(4, "국민일보", "국민일보", "http://m.kmib.co.kr/", 0));
        result.add(new NewsItem(0, "네이버", "네이버신문", "https://m.news.naver.com", 0));
        result.add(new NewsItem(1, "다음", "다음신문", "http://m.media.daum.net", 0));
        result.add(new NewsItem(2, "구글", "구글신문", "https://news.google.com", 0));
        result.add(new NewsItem(3, "네이트", "네이트뉴스", "http://m.news.nate.com/?", 0));
        result.add(new NewsItem(4, "국민일보", "국민일보", "http://m.kmib.co.kr/", 0));

        return result;
    }

/*
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
    }*/



    @Override
    public void onScrollStateChanged(final AbsListView view, final int scrollState) {
        Log.d(TAG, "onScrollStateChanged:" + scrollState);
    }

    @Override
    public void onScroll(final AbsListView view, final int firstVisibleItem, final int visibleItemCount, final int totalItemCount) {
        Log.d(TAG, "onScroll firstVisibleItem:" + firstVisibleItem +
                " visibleItemCount:" + visibleItemCount +
                " totalItemCount:" + totalItemCount);
        // our handling
        if (!mHasRequestedMore) {
            int lastInScreen = firstVisibleItem + visibleItemCount;
            if (lastInScreen >= totalItemCount) {
                Log.d(TAG, "onScroll lastInScreen - so load more");
                mHasRequestedMore = true;
               // onLoadMoreItems();
            }
        }
    }
/*

    private void onLoadMoreItems() {
        final ArrayList<NewsItem> sampleData = addList();
        for (NewsItem data : sampleData) {
            mAdapter.add(data);
        }
        // stash all the data in our backing store
        mData.addAll(sampleData);
        // notify the adapter that we can update now
        mAdapter.notifyDataSetChanged();
        mHasRequestedMore = false;
    }
*/

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

        NewsItem item = (NewsItem)adapterView.getAdapter().getItem(position);

        Log.d("__________item toString", item.toString());

        Fragment fragment = new WebViewFragment();
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();

        Bundle bundle = new Bundle(1); // 파라미터는 전달할 데이터 개수
        bundle.putString("url", item.getUrl()); // key , value
        fragment.setArguments(bundle);

        ft.replace(R.id.content_fragment_layout, fragment, fragment.getClass().getName());
        ft.commit();

        Toast.makeText(getActivity(), item.getTitle(), Toast.LENGTH_SHORT).show();
    }
}

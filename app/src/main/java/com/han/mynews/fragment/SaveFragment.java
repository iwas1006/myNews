package com.han.mynews.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.han.mynews.R;
import com.han.mynews.db.DBHelper;
import com.han.mynews.dto.NewsItem;
import com.han.mynews.view.NewsItemView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SaveFragment extends Fragment {

    NewsAdapter adapter;
    ListView listView;

    public SaveFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_db, container, false);

        final DBHelper dbHelper = new DBHelper(getActivity(), "newsbook.db", null, 1);
        final TextView result = (TextView) v.findViewById(R.id.result);
        final EditText etTitle = (EditText) v.findViewById(R.id.title);
        final EditText etContent = (EditText) v.findViewById(R.id.content);
        final EditText etUrl = (EditText) v.findViewById(R.id.url);
        long now = System.currentTimeMillis();
        final Date date = new Date(now);
        // 출력될 포맷 설정
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        //etDate.setText(simpleDateFormat.format(date));

        // DB에 데이터 추가
        Button insert = (Button) v.findViewById(R.id.insert);
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = etTitle.getText().toString();
                String content = etContent.getText().toString();
                String url = etUrl.getText().toString();

                dbHelper.insert(simpleDateFormat.format(date), title, content, url);
                result.setText(dbHelper.getResult());
            }
        });

       /* // DB에 있는 데이터 수정
        Button update = (Button) findViewById(R.id.update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item = etItem.getText().toString();
                int price = Integer.parseInt(etPrice.getText().toString());

                dbHelper.update(item, price);
                result.setText(dbHelper.getResult());
            }
        });*/

        // DB에 있는 데이터 삭제
      /*  Button delete = (Button) findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item = etItem.getText().toString();

                dbHelper.delete(item);
                result.setText(dbHelper.getResult());
            }
        });*/

        // DB에 있는 데이터 조회
        Button select = (Button) v.findViewById(R.id.select);
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result.setText(dbHelper.getResult());
            }
        });


        // Inflate the layout for this fragment
        return v;
    }

    private void addSavedList(NewsAdapter adapter) {
        final DBHelper dbHelper = new DBHelper(getActivity(), "newsbook.db", null, 1);
        List<NewsItem> itemList = dbHelper.getItems();
        for(NewsItem item : itemList) {
            adapter.addItem(item);
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
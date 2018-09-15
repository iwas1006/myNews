package com.han.mynews.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;


import com.han.mynews.R;
import com.han.mynews.dao.NewsDaoImpl;
import com.han.mynews.db.DBHelper;


import java.text.SimpleDateFormat;

import java.util.Date;


public class SaveFragment extends Fragment {


    public SaveFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_db, container, false);

        final NewsDaoImpl news = new NewsDaoImpl(getContext());
        final TextView result = (TextView) v.findViewById(R.id.result);

        long now = System.currentTimeMillis();
        final Date date = new Date(now);
        // 출력될 포맷 설정
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        //etDate.setText(simpleDateFormat.format(date));

   /*     // DB에 데이터 추가
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
*/
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
        Button delete = (Button) v.findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String item = etItem.getText().toString();

                news.deleteAll();
                result.setText(news.getResult());
            }
        });

        // DB에 있는 데이터 조회
        Button select = (Button) v.findViewById(R.id.select);
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result.setText(news.getResult());
            }
        });


        // Inflate the layout for this fragment
        return v;
    }
}
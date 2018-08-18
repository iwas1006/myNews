package com.han.mynews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NewsItemView extends LinearLayout {
    TextView textView;
    TextView textView2;
    ImageView imageView;

    public NewsItemView(Context context) {
        super(context);
        init(context);
    }

    public NewsItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.news_item, this, true);

        textView = (TextView) findViewById(R.id.textView);
        textView2 = (TextView) findViewById(R.id.textView2);
        imageView = (ImageView) findViewById(R.id.imageView);
    }

    public void setTitle(String title) {
        textView.setText(title);
    }

    public void setContent(String content) {
        textView2.setText(content);
    }

    public void setImage(int resId) {
        imageView.setImageResource(resId);
    }
}

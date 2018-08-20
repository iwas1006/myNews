package com.han.mynews.task;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.han.mynews.dto.Book;
import com.han.mynews.dto.OGTag;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class BookImageTask extends AsyncTask<String, Void, String> {

    private Exception e;
    private Context c;
    private ImageView imageView;
    private  Map<String, String> imageUrlMap;

    public BookImageTask(Context c, Map<String, Object> obj) {
        this.c = c;
        this.imageView = (ImageView)obj.get("view");
        this.imageUrlMap = (Map<String, String>)obj.get("map");

    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            if(imageUrlMap.containsKey(strings[0])) {
                return imageUrlMap.get(strings[0]);
            } else {
                OGTag og = getOGTag(strings[0]);
                if(og != null && og.getImage() != null && og.getImage().length() > 0) {
                    imageUrlMap.put(strings[0], og.getImage());
                    //return og.getImage();
                    this.imageView.setImageURI(Uri.parse(og.getImage()));
                }
            }

        } catch (Exception e) {
            this.e = e;
            return null;
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        this.imageView.setImageURI(Uri.parse(s));
    }

    private OGTag getOGTag(String url) {
        OGTag result = new OGTag();

        try {
            Connection con = Jsoup.connect(url);
            Document doc = con.get();
            Elements ogTags = doc.select("meta[property^=og:]");
            if (ogTags.size() <= 0) {
                return null;
            }

            int size = ogTags.size();

            for (int i = 0 ; i < size ; i++) {
                Element tag = ogTags.get(i);
                String text = tag.attr("property");
                if ("og:url".equals(text)) {
                    result.setUrl(tag.attr("content"));
                } else if ("og:image".equals(text)) {
                    result.setImage(tag.attr("content"));
                } else if ("og:description".equals(text)) {
                    result.setDescription(tag.attr("content"));
                } else if ("og:title".equals(text)) {
                    result.setTitle(tag.attr("content"));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return result;
    }
}
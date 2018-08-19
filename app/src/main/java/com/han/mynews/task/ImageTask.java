package com.han.mynews.task;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.han.mynews.dto.OGTag;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ImageTask extends AsyncTask<String, Void, Drawable> {
    private static Map<String, Bitmap> imgMap;
    private Exception e;
    private Context c;
    private View v;

    public ImageTask(Context c, View v) {
        this.c = c;
        this.v = v;

        imgMap = new HashMap<String, Bitmap>();
    }

    @Override
    protected Drawable doInBackground(String... strings) {
        try {

            OGTag og = getOGTag(strings[0]);

            if(og != null && og.getImage() != null && og.getImage().length() > 0) {
                Bitmap b = null;
                if(imgMap.containsKey(og.getImage())) {
                    b = imgMap.get(og.getImage());
                } else {
                    URL u = new URL(og.getImage());
                    HttpURLConnection conn = (HttpURLConnection) u.openConnection();
                    conn.setDoInput(true);
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    b = BitmapFactory.decodeStream(is);
                    if(!imgMap.containsKey(og.getImage())) {
                        imgMap.put(og.getImage(), b);
                    }

                    conn.disconnect();
                }

                Drawable b2 = new BitmapDrawable(this.c.getResources(), b);

                return b2;
            }
        } catch (Exception e) {
            this.e = e;
            return null;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Drawable drawable) {
        v.setBackground(drawable);
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
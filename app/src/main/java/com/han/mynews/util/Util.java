package com.han.mynews.util;

import com.han.mynews.dto.OGTag;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Util {
    public OGTag getOGTag(String url) {
        OGTag result = new OGTag();

        try {
            Connection.Response response = Jsoup.connect(url).method(Connection.Method.GET).execute();
            Document doc = response.parse();

//            Connection con = Jsoup.connect(url);
//            Document doc = con.get();
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

    public static String encodeUrl(String str) {
        try {
            return URLEncoder.encode(str, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static String decodeUrl(String str) {
        try {
            return URLEncoder.encode(str, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return "";
    }
}

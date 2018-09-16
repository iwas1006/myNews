package com.han.mynews.dto;

import android.graphics.Bitmap;

public class Book {
    private String id;
    private String title;
    private String content;
    private String url;
    private String imageUrl = "";
    private Bitmap imageBitmap;

    public Book() {

    }

    public Book(String id, String title, String content, String url, String imageUrl) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.url = url;
        this.imageUrl = imageUrl;
    }

    public Book(String title, String content, String url) {
        this.title = title;
        this.content = content;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageUrl() {
      return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Bitmap getImageBitmap() {
        return imageBitmap;
    }

    public void setImageBitmap(Bitmap b) {
        this.imageBitmap = b;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", url='" + url + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}

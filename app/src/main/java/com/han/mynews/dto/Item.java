package com.han.mynews.dto;

public class Item {
    private String title;
    private String content;
    private String url;
    private String icon;


    public Item(String title, String content, String url, String icon) {
        this.title      = title;
        this.content    = content;
        this.url        = url;
        this.icon       = icon;
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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public String toString() {
        return "Item{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", url='" + url + '\'' +
                ", icon='" + icon + '\'' +
                '}';
    }
}

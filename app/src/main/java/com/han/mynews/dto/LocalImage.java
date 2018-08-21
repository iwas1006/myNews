package com.han.mynews.dto;

public class LocalImage {
    private String id;
    private String url;
    private String filePath;

    public LocalImage(String id, String url, String filePath) {
        this.id = id;
        this.url = url;
        this.filePath = filePath;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String toString() {
        return "LocalImage{" +
                "id='" + id + '\'' +
                ", url='" + url + '\'' +
                ", filePath='" + filePath + '\'' +
                '}';
    }
}

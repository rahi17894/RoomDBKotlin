package com.example.diagnalprogrammingtest.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Content {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("poster-image")
    @Expose
    private String posterImage;

    @SerializedName("total-content-items")
    @Expose
    private String total_content_items;
    @SerializedName("page-num")
    @Expose
    private String page_num;

    @SerializedName("page-size")
    @Expose
    private String page_size;

    @SerializedName("title")
    @Expose
    private String title;

    public Content(String name, String posterImage, String total_content_items, String page_num, String page_size, String title) {
        this.name = name;
        this.posterImage = posterImage;
        this.total_content_items = total_content_items;
        this.page_num = page_num;
        this.page_size = page_size;
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosterImage() {
        return posterImage;
    }

    public void setPosterImage(String posterImage) {
        this.posterImage = posterImage;
    }

    public String getTotal_content_items() {
        return total_content_items;
    }

    public void setTotal_content_items(String total_content_items) {
        this.total_content_items = total_content_items;
    }

    public String getPage_num() {
        return page_num;
    }

    public void setPage_num(String page_num) {
        this.page_num = page_num;
    }

    public String getPage_size() {
        return page_size;
    }

    public void setPage_size(String page_size) {
        this.page_size = page_size;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

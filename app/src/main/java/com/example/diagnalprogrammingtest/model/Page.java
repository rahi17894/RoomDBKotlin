package com.example.diagnalprogrammingtest.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Page {
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("total-content-items")
    @Expose
    private String totalContentItems;
    @SerializedName("page-num")
    @Expose
    private String pageNum;
    @SerializedName("page-size")
    @Expose
    private String pageSize;
    @SerializedName("content-items")
    @Expose
    private ContentItems contentItems;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTotalContentItems() {
        return totalContentItems;
    }

    public void setTotalContentItems(String totalContentItems) {
        this.totalContentItems = totalContentItems;
    }

    public String getPageNum() {
        return pageNum;
    }

    public void setPageNum(String pageNum) {
        this.pageNum = pageNum;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public ContentItems getContentItems() {
        return contentItems;
    }

    public void setContentItems(ContentItems contentItems) {
        this.contentItems = contentItems;
    }
}

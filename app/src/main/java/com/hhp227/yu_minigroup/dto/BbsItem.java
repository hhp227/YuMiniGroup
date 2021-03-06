package com.hhp227.yu_minigroup.dto;

public class BbsItem {
    private String id, title, writer, date;

    public BbsItem() {
    }

    public BbsItem(String id, String title, String writer, String date) {
        this.id = id;
        this.title = title;
        this.writer = writer;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

package com.alkan.news.dto;

import java.io.Serializable;

public class NewsDto implements Serializable {

    int id;
    String title;
    String content;
    String link;
    String category;
    String date;
    String author;
    boolean isFavorite = false;
    public NewsDto() {
    }
}

package com.alkan.news.response;

import java.util.ArrayList;
import java.util.List;

public class PythonApiResponse {

    private List<String> recommended_news;

    public List<String> getRecommendedNews() {
        return recommended_news;
    }

    public void setRecommendedNews(List<String> recommendedNews) {
        this.recommended_news = recommendedNews;
    }

    public List<String> getHeadline(List<String> recommendedNews) {
        List<String> headlines = new ArrayList<>();
        for (String news : recommendedNews) {
            headlines.add(news);
        }
        return headlines;
    }
}

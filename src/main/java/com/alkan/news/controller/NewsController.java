package com.alkan.news.controller;

import com.alkan.news.request.GetRecommendedNews;
import com.alkan.news.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class NewsController {


    private RestTemplate restTemplate;
    private NewsService newsService;

    public NewsController(RestTemplate restTemplate, NewsService newsService) {
        this.restTemplate = restTemplate;
        this.newsService = newsService;
    }

    @PostMapping("/getRecommendedNews")
    @ResponseBody
    public ResponseEntity<String> getRecommendedNews(@RequestBody GetRecommendedNews getRecommendedNews) throws Exception {
        return newsService.getRecommendations(getRecommendedNews.getHeadline());
    }
}
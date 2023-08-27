package com.novacodestudios.news.viewmodel.news_feed

import com.novacodestudios.news.model.News

data class NewsFeedState(
    val newsList:List<News> = emptyList(),
    val search:String="",
)

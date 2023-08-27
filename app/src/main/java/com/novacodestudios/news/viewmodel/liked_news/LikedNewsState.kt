package com.novacodestudios.news.viewmodel.liked_news

import com.novacodestudios.news.model.News

data class LikedNewsState(
    val search:String="",
    val favoriteNewsList:List<News> = emptyList()
)

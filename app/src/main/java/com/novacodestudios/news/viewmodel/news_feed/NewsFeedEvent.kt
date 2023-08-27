package com.novacodestudios.news.viewmodel.news_feed

sealed class NewsFeedEvent{
    data class OnNewsClick(val link:String):NewsFeedEvent()
    data class OnSearchChange(val search:String):NewsFeedEvent()
    data class OnAddFavorite(val newsId:Int):NewsFeedEvent()
    data class OnRemoveFavorite(val newsId:Int):NewsFeedEvent()
}

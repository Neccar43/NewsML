package com.novacodestudios.news.viewmodel.liked_news


sealed class LikedNewsEvent{
    data class OnNewsClick(val link:String): LikedNewsEvent()
    data class OnSearchChange(val search:String): LikedNewsEvent()
    data class OnAddFavorite(val newsId:Int): LikedNewsEvent()
    data class OnRemoveFavorite(val newsId:Int): LikedNewsEvent()
}

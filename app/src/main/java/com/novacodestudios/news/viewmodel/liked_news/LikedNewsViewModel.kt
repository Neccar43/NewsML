package com.novacodestudios.news.viewmodel.liked_news

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.novacodestudios.news.service.NewsAPI
import com.novacodestudios.news.util.Const
import com.novacodestudios.news.viewmodel.news_feed.NewsFeedViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LikedNewsViewModel @Inject constructor(
private val api: NewsAPI
) : ViewModel() {

    var state by mutableStateOf(LikedNewsState())

   private var job: Job?=null
    init {
        job?.cancel()
        job=viewModelScope.launch {
            getFavoriteNews()
        }
    }

    private fun getFavoriteNews() {
        // TODO: uid yi vermeyi unutma
        viewModelScope.launch {
            val response=api.getFavoriteNews(0)
            if (response.isSuccessful){
                state=state.copy(favoriteNewsList = response.body()!!)
            }
            state=state.copy()
        }

    }

    fun onEvent(event: LikedNewsEvent){
        when (event) {
            is LikedNewsEvent.OnNewsClick -> {}
            is LikedNewsEvent.OnSearchChange -> state=state.copy(search = event.search)
            is LikedNewsEvent.OnAddFavorite -> addFavorite(event.newsId)
            is LikedNewsEvent.OnRemoveFavorite -> removeFavorite(event.newsId)
        }
    }

    private fun addFavorite(newsId: Int) {
        viewModelScope.launch {

            val response= api.addNewsToFavorite(newsId, Const.UID)
            if (response.isSuccessful){
                NewsFeedViewModel.UIEvent.ShowSnackbar("Favorilere eklendi")
            }else{
                NewsFeedViewModel.UIEvent.ShowSnackbar("Favorilere eklenemedi")
            }
        }

    }

    private fun removeFavorite(newsId: Int) {

        viewModelScope.launch {

            val response= api.removeNewsFromFavorite(newsId, Const.UID)
            if (response.isSuccessful){
                NewsFeedViewModel.UIEvent.ShowSnackbar("Favorilerden çıkarıldı")
            }else{
                NewsFeedViewModel.UIEvent.ShowSnackbar("Favorilerden çıkarılamadı")
            }
        }
    }
}
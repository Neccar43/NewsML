package com.novacodestudios.news.viewmodel.news_feed

import android.annotation.SuppressLint
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.novacodestudios.news.service.NewsAPI
import com.novacodestudios.news.util.Const.UID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@SuppressLint("SuspiciousIndentation")
@HiltViewModel
class NewsFeedViewModel @Inject constructor(
private val api: NewsAPI
) : ViewModel() {
    var state by mutableStateOf(NewsFeedState())

    private var job: Job?=null
    init {
        job?.cancel()

        job=viewModelScope.launch {
          val response=  api.getFeedNews()

            if (response.isSuccessful){
                state=state.copy(newsList = response.body()!!)
            }
        }
    }

    private fun addFavorite(newsId: Int) {
        viewModelScope.launch {

         val response= api.addNewsToFavorite(newsId,UID)
            if (response.isSuccessful){
                UIEvent.ShowSnackbar("Favorilere eklendi")
            }else{
                UIEvent.ShowSnackbar("Favorilere eklenemedi")
            }
        }

    }

    private fun removeFavorite(newsId: Int) {

        viewModelScope.launch {

            val response= api.removeNewsFromFavorite(newsId,UID)
            if (response.isSuccessful){
                UIEvent.ShowSnackbar("Favorilerden çıkarıldı")
            }else{
                UIEvent.ShowSnackbar("Favorilerden çıkarılamadı")
            }
        }
    }

    fun onEvent(event: NewsFeedEvent){
        when (event) {
            is NewsFeedEvent.OnNewsClick -> {}
            is NewsFeedEvent.OnSearchChange -> state=state.copy(search = event.search)
            is NewsFeedEvent.OnAddFavorite -> addFavorite(event.newsId)
            is NewsFeedEvent.OnRemoveFavorite -> removeFavorite(event.newsId)
        }
    }

    sealed class UIEvent {
        data class ShowSnackbar(val message: String) : UIEvent()
    }
}
package com.novacodestudios.news.viewmodel.browser

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.novacodestudios.news.service.NewsAPI
import com.novacodestudios.news.util.Const.NEWS_URL
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BrowserViewModel @Inject constructor(
private val api:NewsAPI,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    var state by mutableStateOf(BrowserState())

    init {
        savedStateHandle.get<String>(NEWS_URL)?.let {url->
            println(url)
            openNewsUrl(url)
        }
    }

    private fun openNewsUrl(url:String) {
        state=state.copy(url=url)
        // TODO: Burda apiye bildir
    }
}
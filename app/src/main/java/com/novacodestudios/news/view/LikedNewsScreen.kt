package com.novacodestudios.news.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.novacodestudios.news.viewmodel.liked_news.LikedNewsEvent
import com.novacodestudios.news.viewmodel.liked_news.LikedNewsViewModel
import com.novacodestudios.news.viewmodel.news_feed.NewsFeedEvent
import com.novacodestudios.news.viewmodel.settings.SettingsViewModel
import com.novacodestudios.recall.presentation.util.StandardSearchBar

@Composable
fun LikedNewsScreen(navController: NavController, viewModel: LikedNewsViewModel = hiltViewModel()) {
    val state=viewModel.state
    Column(Modifier.fillMaxSize()) {
        StandardSearchBar(
            onSearch = { viewModel.onEvent(LikedNewsEvent.OnSearchChange(it)) },
            text = state.search,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
            hint = "Favorilerde arayın"
        )
        NewsLazyColumn(newsList = state.favoriteNewsList,navController=navController)
    }

}
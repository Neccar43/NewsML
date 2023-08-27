package com.novacodestudios.news.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ListItem
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.novacodestudios.news.model.News
import com.novacodestudios.news.util.Screen
import com.novacodestudios.news.viewmodel.news_feed.NewsFeedEvent
import com.novacodestudios.news.viewmodel.news_feed.NewsFeedViewModel
import com.novacodestudios.news.viewmodel.settings.SettingsViewModel
import com.novacodestudios.recall.presentation.util.StandardSearchBar


@Composable
fun NewsFeedScreen(navController: NavController, viewModel: NewsFeedViewModel = hiltViewModel()) {
    val state = viewModel.state
    Column {
        StandardSearchBar(
            onSearch = { viewModel.onEvent(NewsFeedEvent.OnSearchChange(it)) },
            text = state.search,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            hint = "Haberlerde arayın"
        )
        NewsLazyColumn(newsList = state.newsList,navController=navController)
    }
}

@Composable
fun NewsLazyColumn(newsList: List<News>, navController: NavController) {
    LazyColumn {
        items(newsList) { news ->
            NewsItem(
                news = news,
                onClick = { navController.navigate(Screen.BrowserScreen.route + "/{${news.link}}") })
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NewsItem(news: News, onClick: () -> Unit) {
    var isFavorite by remember { mutableStateOf(false) }
    ListItem(modifier = Modifier.clickable { onClick() },
        text = { Text(text = news.title) },
        secondaryText = { Text(text = news.content) },
        singleLineSecondaryText = true,
        trailing = {
            IconButton(
                onClick = { isFavorite = !isFavorite },
            ) {
                Icon(
                    imageVector =  if (isFavorite)Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = "Favorilere ekle",
                )
            }
        }
    )
    Divider(Modifier.fillMaxWidth())
}

@Composable
fun NewsItem2(news: News, onClick: () -> Unit) {

}
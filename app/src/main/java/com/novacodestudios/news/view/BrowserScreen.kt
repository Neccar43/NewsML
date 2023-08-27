package com.novacodestudios.news.view

import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.novacodestudios.news.viewmodel.browser.BrowserViewModel

@Composable
fun BrowserScreen(navController: NavController, viewModel: BrowserViewModel = hiltViewModel()) {
    val state=viewModel.state
    AndroidView(factory = {
        WebView(it).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            webViewClient = WebViewClient()
            loadUrl(state.url)
        }
    }, update = {
        it.loadUrl(state.url)
    })
}
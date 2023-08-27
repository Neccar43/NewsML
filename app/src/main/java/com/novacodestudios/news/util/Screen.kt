package com.novacodestudios.news.util

sealed class Screen(val route:String) {
    data object SignUpScreen: Screen("sign_up_screen")
    data object SignInScreen: Screen("sign_in_screen")
    data object SettingsScreen: Screen("settings_screen")
    data object NewsFeedScreen:Screen("news_feed_screen")
    data object LikedNewsScreen:Screen("liked_news_screen")
    data object BrowserScreen:Screen("browser_screen")
}

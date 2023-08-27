package com.novacodestudios.news

import android.app.Application
import android.content.Context
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NewsApp : Application() {
    override fun onCreate() {
        super.onCreate()

        val sharedPreferences = this.getSharedPreferences(
            "com.novacodestudios.news",
            Context.MODE_PRIVATE
        )
    }
}
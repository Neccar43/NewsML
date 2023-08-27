package com.novacodestudios.news.di

import com.novacodestudios.news.service.NewsAPI
import com.novacodestudios.news.util.Const.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun injectNewsApi(): NewsAPI = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()
        .create(NewsAPI::class.java)

    /*@Singleton
        @Provides
        fun injectFakeApi():NewsAPI=FakeNewsAPI()*/


}
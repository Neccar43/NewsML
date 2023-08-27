package com.novacodestudios.news.service

import com.novacodestudios.news.model.News
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface NewsAPI {

    @GET("")
    suspend fun getUserId(email: String): Response<Int>

    @POST("")
    suspend fun signUpUser(email: String, password: String): Response<String>

    @FormUrlEncoded
    @POST("")
    suspend fun signInUser(
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<String>

    @GET("")
    suspend fun getFeedNews():Response<List<News>>

    @GET("")
    suspend fun getFavoriteNews(uid: Int):Response<List<News>>

    @POST("")
   suspend fun signOutUser(uid:Int):Response<String>

    @POST()
    suspend fun addNewsToFavorite(newsId:Int,userId:Int):Response<Boolean>

    @DELETE("")
    suspend fun removeNewsFromFavorite(newsId:Int,userId:Int):Response<Boolean>





}
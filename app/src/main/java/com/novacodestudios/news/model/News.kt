package com.novacodestudios.news.model

data class News(
    val id:Int,
    val title:String,
    val content:String,
    val link:String,
    val category:String,
    val date:String,
    val author:String,
)

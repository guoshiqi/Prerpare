package com.example.prerpare.data.network

import com.example.prerpare.data.model.Article
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("article/{article}")
    suspend fun getArticles(@Path("article") index: String): List<Article>
}
package com.example.prerpare.data

import com.example.prerpare.data.model.Article

class MainListRepository {
    suspend fun getArticles(): List<Article> {
        return listOf(
            Article("Android 基础", "这是第一条假数据"),
            Article("Kotlin 入门", "这是第二条假数据"),
            Article("RecyclerView 练习", "这是第三条假数据"),
            Article("面试复习", "这是第四条假数据"),
            Article("Android 基础", "这是第一条假数据"),
            Article("Kotlin 入门", "这是第二条假数据"),
            Article("RecyclerView 练习", "这是第三条假数据"),
            Article("面试复习", "这是第四条假数据"),
            Article("Android 基础", "这是第一条假数据"),
            Article("Kotlin 入门", "这是第二条假数据"),
            Article("RecyclerView 练习", "这是第三条假数据"),
            Article("面试复习", "这是第四条假数据")
        )
    }
}
package com.example.prerpare.data

import com.example.prerpare.data.db.dao.ArticleDAO
import com.example.prerpare.data.model.Article

class LocalDataSource(private val dao: ArticleDAO) : DataSource {
    //本地数据
    override suspend fun getData(): Result<List<Article>> {
        return try {
            val articles = dao.getAllArticles()
            Result.success(articles)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun saveData(data: List<Article>) {
        dao.insertArticles(data)
    }
}
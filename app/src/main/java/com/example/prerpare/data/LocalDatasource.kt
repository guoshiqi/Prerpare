package com.example.prerpare.data

import com.example.prerpare.data.db.dao.ArticleDAO
import com.example.prerpare.data.model.Article

class LocalDataSource(private val dao: ArticleDAO) : DataSource {
    override suspend fun getData(): Result<List<Article>> {
        return try {
            val articles = dao.getAllArticles()
            if (articles.isNotEmpty()) {
                Result.success(articles)
            } else {
                Result.failure(Exception("No data found in local database"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun saveData(data: List<Article>) {
        dao.insertArticles(data)
    }
}
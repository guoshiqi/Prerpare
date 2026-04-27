package com.example.prerpare.data

import com.example.prerpare.data.model.Article
import com.example.prerpare.data.network.ApiService

class RemoteDataSource(private val apiService: ApiService) : DataSource{
    override suspend fun getData(): Result<List<Article>> {
        return try {
            val response=apiService.getArticles("s")
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Failed to fetch data from network"))
            }
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}
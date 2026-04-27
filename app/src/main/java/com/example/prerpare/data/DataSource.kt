package com.example.prerpare.data

import com.example.prerpare.data.model.Article

interface DataSource {
    suspend fun getData(): Result<List<Article>>
}
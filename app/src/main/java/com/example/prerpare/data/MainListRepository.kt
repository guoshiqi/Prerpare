package com.example.prerpare.data

import com.example.prerpare.data.model.Article
import kotlinx.coroutines.delay

class MainListRepository( private val localDataSource: LocalDataSource,
                          private val remoteDataSource: RemoteDataSource) {
    suspend fun getArticles(): Result<List<Article>> {

        val local=localDataSource.getData()
        return if (local.isSuccess){
            local
        }else{
            delay(3000)
            val remote=remoteDataSource.getData()
            if (remote.isSuccess){
                remote.getOrNull()?.let { data->
                    localDataSource.saveData(data)
                }
            }
            remote
        }
    }
}
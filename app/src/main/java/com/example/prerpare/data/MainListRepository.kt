package com.example.prerpare.data

import com.example.prerpare.data.model.Article
import kotlinx.coroutines.delay

class MainListRepository( private val localDataSource: LocalDataSource,
                          private val remoteDataSource: RemoteDataSource) {
    suspend fun getArticles(network: Boolean): Result<List<Article>> {
        return if (network){
            delay(3000)
            val remote=remoteDataSource.getData()
            if (remote.isSuccess){
                remote.getOrNull()?.let { data->
                    localDataSource.saveData(data)
                }
            }
            remote
        }else{
            val local=localDataSource.getData()
            if (local.isSuccess){
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
}
package com.example.prerpare

import android.app.Application
import com.example.prerpare.data.network.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PrepareApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())  // 添加Gson转换器
            .build()

        val apiService = retrofit.create(ApiService::class.java)
    }
}
package com.example.prerpare

import android.app.Application
import com.example.prerpare.data.network.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PrepareApplication : Application() {
    lateinit var apiService: ApiService
    private lateinit var mockWebServer: MockWebServer
    var baseUrl = "https://api.github.com/".toHttpUrl()
    private val applicationScope = CoroutineScope(
        SupervisorJob() + Dispatchers.Default
    )

    override fun onCreate() {
        super.onCreate()
        applicationScope.launch {
            initMockServer()
            val okHttpClient = OkHttpClient()
            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())  // 添加Gson转换器
                .build()

            apiService = retrofit.create(ApiService::class.java)
            mockResponse()
        }
    }

    fun initMockServer() {
        // 创建 MockWebServer 实例
        mockWebServer = MockWebServer()

        // 启动 MockWebServer
        mockWebServer.start()
        baseUrl = mockWebServer.url("/")
    }

    fun mockResponse() {
        // 读取模拟的 JSON 响应
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(loadMockResponse())  // 加载配置文件中的响应
        mockWebServer.enqueue(mockResponse)

    }

    fun loadMockResponse(): String {
        val inputStream = assets.open("mock_response.json")
        val json = inputStream.bufferedReader().use { it.readText() }
        // 加载本地的 mock_response.json 文件
        return json
    }

}
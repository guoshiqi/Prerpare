package com.example.prerpare

import android.util.Log
import com.example.prerpare.data.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28]) // 设置模拟的 SDK 版本
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun first_day_action() {
        val a = 1
        var b = 2
        var c: String? = "s"
        c?.length
        c = c ?: "sdf"
        c = "asd,$c"
        println(c)
        val result = if (c == c) {
            "yes"
        } else {
            "false"
        }
        println(result)
        when (result) {
            "yes" -> println("成")
        }
        println(add(1, 2))
        println(add(1))
        val numbers = listOf(1, 2, 3, 4, 5)
        val even = numbers.filter { it % 2 == 1 }.map { it * 2 }
        println(even)
    }

    fun add(a: Int, b: Int = 100) = a + b

    private lateinit var mockWebServer: MockWebServer
    private lateinit var client: OkHttpClient

    @Before
    fun setUp() {
        // 创建 MockWebServer 实例
        mockWebServer = MockWebServer()

        // 启动 MockWebServer
        mockWebServer.start()

        // 设置 OkHttpClient
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.NONE // 禁用日志

        // 创建 OkHttpClient 并添加日志拦截器
        client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }


    @Test
    fun testGetUser() {

        // 获取 MockWebServer 的 URL
        val baseUrl = mockWebServer.url("/")

        // 创建 Retrofit 实例并将 MockWebServer 的 URL 作为 baseUrl
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        val apiService = retrofit.create(ApiService::class.java)

        // 读取模拟的 JSON 响应
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(loadMockResponse())  // 加载配置文件中的响应
        mockWebServer.enqueue(mockResponse)

        // 调用 Retrofit 的 API 方法
        val articles = runBlocking {
            apiService.getArticles("octocat")
        }

        // 验证结果
        println(articles.toString())

    }

    fun loadMockResponse(): String {
        val inputStream = javaClass.classLoader?.getResourceAsStream("mock_response.json")
        val json = inputStream?.bufferedReader().use { it?.readText() }
        // 加载本地的 mock_response.json 文件
        return json.toString()
    }

    @After
    fun tearDown() {
        // 停止 MockWebServer
        mockWebServer.shutdown()
    }

    @Test
    fun dispatcherDemo() {
        runBlocking {
            launch {
                delay(1000)
                println("launch")
                withContext(Dispatchers.IO) {
                    delay(3000)
                    println("io")
                }
                println("launchFinish")
            }
            val deferred = async {
                delay(500)
                "async"
            }
            val result = deferred.await()
            println(result)
        }
    }
}
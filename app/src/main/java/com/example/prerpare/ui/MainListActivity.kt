package com.example.prerpare.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.prerpare.ArticleAdapter
import com.example.prerpare.model.Article
import com.example.prerpare.databinding.ActivityListLayoutBinding

class MainListActivity : ComponentActivity() {
    private lateinit var binding: ActivityListLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mockList = listOf(
            Article("Android 基础", "这是第一条假数据"),
            Article("Kotlin 入门", "这是第二条假数据"),
            Article("RecyclerView 练习", "这是第三条假数据"),
            Article("面试复习", "这是第四条假数据")
        )

        binding.rvList.layoutManager = LinearLayoutManager(this)
        binding.rvList.adapter = ArticleAdapter(mockList) { article, position ->
            run {
                val intent = Intent(this, ListDetailActivity::class.java)
                intent.putExtra("data", article)
                startActivity(intent)
            }
        }
    }
}
package com.example.prerpare.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.prerpare.ArticleAdapter
import com.example.prerpare.databinding.ActivityListLayoutBinding

class MainListActivity : ComponentActivity() {
    private lateinit var binding: ActivityListLayoutBinding
    private lateinit var adapter: ArticleAdapter
    private val viewModel: MainListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.articles.observe(this) { articles ->
            adapter.setData(articles)
        }
        binding.rvList.layoutManager = LinearLayoutManager(this)
        adapter = ArticleAdapter { article, position ->
            run {
                val intent = Intent(this, ListDetailActivity::class.java)
                intent.putExtra("data", article)
                startActivity(intent)
            }
        }
        binding.rvList.adapter = adapter
        viewModel.loadArticles()
    }
}
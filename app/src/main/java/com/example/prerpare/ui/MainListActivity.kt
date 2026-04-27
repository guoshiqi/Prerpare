package com.example.prerpare.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.prerpare.ArticleAdapter
import com.example.prerpare.DatabaseHelper
import com.example.prerpare.PrepareApplication
import com.example.prerpare.data.LocalDataSource
import com.example.prerpare.data.MainListRepository
import com.example.prerpare.data.RemoteDataSource
import com.example.prerpare.data.db.AppDatabase
import com.example.prerpare.data.db.dao.ArticleDAO
import com.example.prerpare.databinding.ActivityListLayoutBinding

class MainListActivity : ComponentActivity() {
    private lateinit var binding: ActivityListLayoutBinding
    private lateinit var adapter: ArticleAdapter
    private lateinit var viewModel: MainListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // 创建 Repository 和 ViewModelFactory
        val localDataSource =
            LocalDataSource(DatabaseHelper.getDatabase(applicationContext).articleDao())
        val remoteDataSource =
            RemoteDataSource((applicationContext as PrepareApplication).apiService)
        val articleRepository = MainListRepository(localDataSource, remoteDataSource)

        // 创建 ViewModel 实例
        val factory = ArticleViewModelFactory(articleRepository)
        viewModel = ViewModelProvider(this, factory).get(MainListViewModel::class.java)

        viewModel.articles.observe(this) { articles ->
            articles.getOrNull()?.let { data ->
                adapter.setData(data)
            }
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
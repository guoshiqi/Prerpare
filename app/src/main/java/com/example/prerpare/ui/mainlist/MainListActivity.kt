package com.example.prerpare.ui.mainlist

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.prerpare.PrepareApplication
import com.example.prerpare.data.LocalDataSource
import com.example.prerpare.data.MainListRepository
import com.example.prerpare.data.RemoteDataSource
import com.example.prerpare.data.db.DatabaseHelper
import com.example.prerpare.data.model.Article
import com.example.prerpare.databinding.ActivityListLayoutBinding
import com.example.prerpare.ArticleViewModelFactory
import com.example.prerpare.ui.ListDetailActivity
import kotlinx.coroutines.launch

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

        lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                when (state) {
                    is ListUiState.Loading -> {
                        binding.swipeRefreshLayout.isRefreshing = true
                        showLoading()
                    }

                    is ListUiState.Success -> {
                        binding.swipeRefreshLayout.isRefreshing = false
                        showData(state.data)
                    }

                    is ListUiState.Error -> {
                        binding.swipeRefreshLayout.isRefreshing = false
                        showError(state.message)
                    }

                    is ListUiState.Empty -> {
                        binding.swipeRefreshLayout.isRefreshing = false
                        showEmpty()
                    }
                }
            }
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.loadArticles(true)
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
        viewModel.loadArticles(false)
    }


    private fun showError(message: String) {
        binding.emptyView.visibility = View.GONE
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showEmpty() {
        binding.emptyView.visibility = View.VISIBLE
    }

    private fun showLoading() {


    }

    private fun showData(articles: List<Article>) {
        adapter.setData(articles)
        binding.emptyView.visibility = View.GONE
    }
}

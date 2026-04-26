package com.example.prerpare.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prerpare.data.MainListRepository
import com.example.prerpare.data.model.Article
import kotlinx.coroutines.launch


class MainListViewModel : ViewModel() {

    private val repositor: MainListRepository = MainListRepository()
    private val _articles = MutableLiveData<List<Article>>()
    val articles: LiveData<List<Article>> = _articles

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    fun loadArticles() {
        viewModelScope.launch {
            _loading.value = true
            _articles.value = repositor.getArticles()
            _loading.value = false
        }
    }
}
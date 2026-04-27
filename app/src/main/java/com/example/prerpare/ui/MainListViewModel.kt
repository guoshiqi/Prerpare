package com.example.prerpare.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prerpare.data.MainListRepository
import com.example.prerpare.data.model.Article
import kotlinx.coroutines.launch


class MainListViewModel(private val repository: MainListRepository) : ViewModel() {

    private val _articles = MutableLiveData<Result<List<Article>>>()
    val articles: LiveData<Result<List<Article>>> = _articles

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    fun loadArticles() {
        viewModelScope.launch {
            _loading.value = true
            val result = repository.getArticles()
            _articles.postValue(result)
            _loading.value = false
        }
    }
}